package com.onpong.apiopeninterface;

import com.onpong.apiopensdk.client.ApiOpenClient;
import com.onpong.apiopensdk.model.User;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
class ApiopenInterfaceApplicationTests {
    @Resource
    private ApiOpenClient apiOpenClient;
    @Test
    void contextLoads() {
        String onpong = apiOpenClient.getNameByGet("onpong");
        User user = new User();
        user.setUsername("onpong123");
        String usernameByPost = apiOpenClient.getUsernameByPost(user);
        System.out.println(onpong);
        System.out.println(usernameByPost);
    }

}
