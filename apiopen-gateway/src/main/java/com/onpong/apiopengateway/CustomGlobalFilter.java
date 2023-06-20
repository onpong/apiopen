package com.onpong.apiopengateway;

import com.onpong.apiopencommon.model.entity.InterfaceInfo;
import com.onpong.apiopencommon.model.entity.User;
import com.onpong.apiopencommon.model.entity.UserInterfaceInfo;
import com.onpong.apiopencommon.service.InnerInterfaceInfoService;
import com.onpong.apiopencommon.service.InnerUserInterfaceInfoService;
import com.onpong.apiopencommon.service.InnerUserService;
import com.onpong.apiopensdk.util.SignUtils;
import com.onpong.project.register.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    InnerUserService innerUserService;

    @DubboReference
    InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    private static final String INTERFACE_HOST = "http://localhost:8123";
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
//        1. 用户发送请求到 API 网关
//        2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getPath().value();
        String totalUrl = "";
        totalUrl = INTERFACE_HOST + url;
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + totalUrl);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        ServerHttpResponse response = exchange.getResponse();
//        3. （黑白名单）
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            return handleNoAuth(response);
        }
//        4. 用户鉴权（判断 accessKey, secretKey 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        //todo 实际情况应该是去数据库中查找是否存在
        User invokeUser = null;
        try{
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.error("getInvokerUserError", e);
        }
        if(invokeUser == null){
            return handleNoAuth(response);
        }
        if(Long.parseLong(nonce) > 10000){
            return handleNoAuth(response);
        }
        //todo 时间和当前时间不能超过5分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES){
            return handleNoAuth(response);
        }
        //todo 实际情况应该是去数据库中查找是否存在secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body,secretKey);
        if(sign != null && !sign.equals(serverSign)){
            return handleNoAuth(response);
        }
//        5. 请求的模拟接口是否存在
        //todo 从数据库中查询接口是否存在，请求方法是否正确。
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(url,method);
        }catch(Exception e){
            log.error("getInterfaceInfoError", e);
        }
        if(interfaceInfo == null){
            return handleNoAuth(response);
        }
        //todo 是否还有调用次数
        UserInterfaceInfo userInterfaceInfo = null;
        try {
            userInterfaceInfo = innerUserInterfaceInfoService.getUserInterfaceInfo(interfaceInfo.getId(),invokeUser.getId());
        }catch(Exception e){
            log.error("getUserInterfaceInfoError", e);
        }
        if(userInterfaceInfo == null || userInterfaceInfo.getLeftNum() <= 0){
            return handleNoAuth(response);
        }
//       6. 请求转发，调用模拟接口,响应日志
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());
        //log.info("响应：" + response.getStatusCode());


    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId){
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatusCode statusCode = originalResponse.getStatusCode();
            if(statusCode == HttpStatus.OK){
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        //8. todo 调用成功，次数 + 1
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount Error",e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);//data
                                        sb2.append(data);
                                        //打印日志
                                        log.info("响应结果： " + data);//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }
    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
    @Override
    public int getOrder() {
        return -1;
    }
}