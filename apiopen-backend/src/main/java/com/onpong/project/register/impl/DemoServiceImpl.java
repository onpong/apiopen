package com.onpong.project.register.impl;

import com.onpong.project.register.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {
    private static int flag = 0;

    @Override
    public String sayHello(String name) {
        String message = "你好啊！ " + name + " =========》 " + flag++;
        return message;
    }

}
