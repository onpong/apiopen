package com.onpong.apiopencommon.service;


import com.onpong.apiopencommon.model.entity.UserInterfaceInfo;

/**
* @author 10025
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-05-27 09:06:19
*/
public interface InnerUserInterfaceInfoService {


    /**
     * 调用次数 + 1
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
