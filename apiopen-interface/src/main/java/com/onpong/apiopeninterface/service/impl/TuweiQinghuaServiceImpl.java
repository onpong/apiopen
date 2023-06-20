package com.onpong.apiopeninterface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onpong.apiopeninterface.domain.TuweiQinghua;
import com.onpong.apiopeninterface.mapper.TuweiQinghuaMapper;
import com.onpong.apiopeninterface.service.TuweiQinghuaService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

/**
* @author 10025
* @description 针对表【tuwei_qinghua】的数据库操作Service实现
* @createDate 2023-06-18 10:50:00
*/
@Service
//@MapperScan(basePackages = {"com.onpong.apiopeninterface.mapper.TuweiQinghuaMapper"})
public class TuweiQinghuaServiceImpl extends ServiceImpl<TuweiQinghuaMapper, TuweiQinghua>
    implements TuweiQinghuaService {

}




