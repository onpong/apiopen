package com.onpong.apiopeninterface.controller;

import cn.hutool.http.HttpUtil;
import com.onpong.apiopensdk.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @PostMapping("/avatarUrl")
    public String getAvatarUrlByPost(HttpServletRequest request) {
        //https://restapi.amap.com/v3/weather/weatherInfo?
        String avatarUrl = "https://api.asilu.com/bg/";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("type", "json");
//        paramMap.put("form", avatarParams.getForm());
        String result = HttpUtil.get(avatarUrl);
        return result;
    }
}
