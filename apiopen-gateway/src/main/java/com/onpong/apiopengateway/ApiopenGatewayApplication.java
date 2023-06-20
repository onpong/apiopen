package com.onpong.apiopengateway;

import com.onpong.project.register.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@EnableDubbo
@Service
public class ApiopenGatewayApplication {
    @DubboReference
    private DemoService demoService;
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiopenGatewayApplication.class, args);
        ApiopenGatewayApplication bean = context.getBean(ApiopenGatewayApplication.class);
        String onpong = bean.demoService.sayHello("onpong");
        System.out.println("result: " + onpong);

    }

//    @Component
//    public class UriKeyResolver implements KeyResolver {
//        @Override
//        public Mono<String> resolve(ServerWebExchange exchange) {
//            final String path = exchange.getRequest().getURI().getPath();
//            System.out.println(path);
//            return Mono.just(path);
//        }
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("path_route", r -> r.path("/baidu")
//                        .uri("http://www.baidu.com"))
//                .route("host_route", r -> r.path("/onpong")
//                        .uri("http://httpbin.org"))
//                .build();
//    }
}
