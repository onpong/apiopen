package com.onpong.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onpong.apiopencommon.model.entity.InterfaceInfo;

/**
* @author 10025
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-05-19 19:47:45
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
