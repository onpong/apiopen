package com.onpong.apiopeninterface.controller;

import com.onpong.apiopensdk.model.User;
import com.onpong.apiopensdk.util.SignUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name){

        return "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(String name){
        return "Post 你的名字是" + name;
    }


    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request){
//        String accessKey = request.getHeader("accessKey");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//        String body = request.getHeader("body");
//        //todo 实际情况应该是去数据库中查找是否存在
//        if(!accessKey.equals("onpong")){
//            throw new RuntimeException("无权限");
//        }
//        if(Long.parseLong(nonce) > 10000){
//            throw new RuntimeException("无权限");
//        }
//        //todo 时间和当前时间不能超过5分钟
//
//        //todo 实际情况应该是去数据库中查找是否存在secretKey
//        String serverSign = SignUtils.getSign(body,"abcdefgh");
//        if(!sign.equals(serverSign)){
//            throw new RuntimeException("无权限");x
//        }
        String result = "{\"name\":\"" + user.getUsername() + "\"}";
        return result;
    }
}
