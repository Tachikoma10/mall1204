package com.sanriyue.mall.service;

import com.sanriyue.mall.bean.UserAddress;
import com.sanriyue.mall.bean.UserInfo;

import java.util.List;

//业务逻辑层
public interface UserInfoService {
    /**
     * 功能描述: <查询所有用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 18:55
     */
    List<UserInfo> getAll();

    List<UserInfo> getByUserInfo(UserInfo userInfo);

    List<UserInfo> getByNickName(String nickName);

    UserInfo getOne(UserInfo userInfo);

    /**
     * 功能描述: <添加用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 19:41
     */
    void addUser(UserInfo userInfo);

    /**
     * 功能描述: <修改用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 19:46
     */
    void updateUser(UserInfo userInfo);

    /**
     * 功能描述: <删除用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 19:56
     */
    void deleteUser(UserInfo userInfo);

    /**
     * 功能描述: <用户地址信息查询>
     * @Author: 三日月
     * @Date: 2019/12/4 18:18
     */
    List<UserAddress> getAddressByUserInfo(UserAddress userAddress);
}

