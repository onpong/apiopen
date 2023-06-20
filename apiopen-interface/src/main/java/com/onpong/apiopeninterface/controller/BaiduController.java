package com.onpong.apiopeninterface.controller;

import cn.hutool.http.HttpUtil;
import com.onpong.apiopensdk.model.BaiduParams;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ClassName BaiduController
 * @Description TODO
 * @Author lish
 * @Date 2023/5/2 19:57
 */

@RestController
@RequestMapping("/baidu")
public class BaiduController {
    @PostMapping("/baiduInfo")
    public String getBaiduInfoByPost(@RequestBody(required = false) BaiduParams baiduParams, HttpServletRequest request) {
        //https://restapi.amap.com/v3/weather/weatherInfo?
        String baiduUrl = "https://www.coderutil.com/api/resou/v1/baidu";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access-key", "09d809af81dcf4170a3b3830e9d5715c");
        paramMap.put("secret-key", "faa208097d7917844a727862e4e750e0");
//        int size = Integer.parseInt(baiduParams.getSize());
        if (baiduParams == null) {
            paramMap.put("size", 10);
        }else {
            paramMap.put("size", baiduParams.getSize());
        }
        String result = HttpUtil.get(baiduUrl, paramMap);

        return result;
    }

}
