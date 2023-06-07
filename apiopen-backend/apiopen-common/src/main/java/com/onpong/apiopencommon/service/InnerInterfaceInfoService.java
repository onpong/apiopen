package com.onpong.apiopencommon.service;

import com.onpong.apiopencommon.model.entity.InterfaceInfo;

/**
* @author 10025
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-05-19 19:47:45
*/
public interface InnerInterfaceInfoService {
    /**
     * 数据库中查询是否接口是否存在(请求路径 请求方法 请求参数) -> boolean
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
