package com.onpong.apiopeninterface.controller;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onpong.apiopeninterface.domain.TuweiQinghua;
import com.onpong.apiopeninterface.mapper.TuweiQinghuaMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joke")
public class JokeController {
    @Resource
    TuweiQinghuaMapper tuweiQinghuaMapper;
    @PostMapping("/jokeUrl")
    public String getJokeUrlByPost(HttpServletRequest request) {
        // 创建 QueryWrapper 对象
        QueryWrapper<TuweiQinghua> queryWrapper = new QueryWrapper<>();
        // 使用 SQL 的随机函数
        //queryWrapper.orderByAsc("RAND()").last("LIMIT 1");
        queryWrapper.apply("id >= (SELECT CEIL(MAX(id)*RAND()) AS ID FROM tuwei_qinghua)");

        queryWrapper.last("LIMIT 1");
// 执行查询
        TuweiQinghua random = tuweiQinghuaMapper.selectOne(queryWrapper);
        String result = random.getContent();
        result = "{\"content\":\"" + result + "\"}";
        return result;
    }
}
