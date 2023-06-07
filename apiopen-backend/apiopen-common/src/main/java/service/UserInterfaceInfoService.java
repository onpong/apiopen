package service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onpong.apiopencommon.model.entity.InterfaceInfo;
import com.onpong.apiopencommon.model.entity.User;
import com.onpong.apiopencommon.model.entity.UserInterfaceInfo;

/**
* @author 10025
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-05-27 09:06:19
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean add);

    /**
     * 数据库中查询是否已分配给用户密钥(accessKey) ->boolean
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

    /**
     * 数据库中查询是否接口是否存在(请求路径 请求方法 请求参数) -> boolean
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

    /**
     * 调用次数 + 1
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
