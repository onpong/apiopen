package com.onpong.apiopensdk;

import com.onpong.apiopensdk.client.ApiOpenClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("apiopen.client")
@Data
@ComponentScan
public class ApiOpenClientConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public ApiOpenClient apiOpenClient(){
        return new ApiOpenClient(accessKey,secretKey);
    }
}
