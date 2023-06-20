package com.onpong.apiopensdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.onpong.apiopensdk.model.User;
import com.onpong.apiopensdk.util.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *调用第三方接口
 *
 * @auther onpong
 */
public class ApiOpenClient {
    private static final String GATEWAY_HOST = "http://localhost:8090";
    private String accessKey;
    private String secretKey;

    public ApiOpenClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    private Map<String,String> getHeaderMap(String body){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        //hashMap.put("secretKey",secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.getSign(body,secretKey));
        return hashMap;
    }
    public String getAvatarUrl(){
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/avatar/avatarUrl")
                .addHeaders(getHeaderMap(""))
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        return result;
    }
    public String getUsernameByPost(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }

    public String onlineInvoke(String parameters,String url) {
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + url)
                .addHeaders(getHeaderMap(parameters))
                .body(parameters)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        return result;
    }


}
