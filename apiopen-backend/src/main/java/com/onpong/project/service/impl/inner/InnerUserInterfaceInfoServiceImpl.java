package com.onpong.project.service.impl.inner;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onpong.apiopencommon.model.entity.UserInterfaceInfo;
import com.onpong.apiopencommon.service.InnerUserInterfaceInfoService;
import com.onpong.project.common.ErrorCode;
import com.onpong.project.exception.BusinessException;
import com.onpong.project.mapper.UserInterfaceInfoMapper;
import com.onpong.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
* @author 10025
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-05-27 09:06:19
*/
@DubboService
@Service
public class InnerUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements InnerUserInterfaceInfoService {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
       return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }
}




