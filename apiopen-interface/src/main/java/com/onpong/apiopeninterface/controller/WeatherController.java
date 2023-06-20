package com.onpong.apiopeninterface.controller;

import cn.hutool.http.HttpUtil;
import com.onpong.apiopensdk.model.BaiduParams;
import com.onpong.apiopensdk.model.WeatherParams;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @PostMapping("/weatherInfo")
    public String getBaiduInfoByPost(@RequestBody(required = false) WeatherParams weatherParams, HttpServletRequest request) {
        //https://restapi.amap.com/v3/weather/weatherInfo?
        String weatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo?parameters";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", "e2fdd7b85bc5b1af343d0c508110ec42");
//        int size = Integer.parseInt(baiduParams.getSize());
        paramMap.put("city",weatherParams.getCity());
        String result = HttpUtil.get(weatherUrl, paramMap);
        return result;
    }

}
