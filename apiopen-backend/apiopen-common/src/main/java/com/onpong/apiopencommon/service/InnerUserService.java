package com.onpong.apiopencommon.service;


import com.onpong.apiopencommon.model.entity.User;


/**
 * 用户服务
 *
 * @author onpong
 */
public interface InnerUserService {
    /**
     * 数据库中查询是否已分配给用户密钥(accessKey) ->boolean
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
