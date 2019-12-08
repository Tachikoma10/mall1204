package com.sanriyue.mall.user.controller;

import com.sanriyue.mall.bean.UserInfo;
import com.sanriyue.mall.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 功能描述: <返回用户数据的部分>
     * @Author: 三日月
     * @Date: 2019/12/3 19:43
     */
    @RequestMapping("getAll")
    public List<UserInfo> getAll(){
        return userInfoService.getAll();
    }

    //springMVC对象传值
    //localhost:8080/getByUserInfo?id=
    @RequestMapping("getByUserInfo")
    public List<UserInfo> getByUserInfo(UserInfo userInfo){
        return userInfoService.getByUserInfo(userInfo);
    }

    //模糊查询
    //http://localhost:8080/getByNickName?nickName=d
    @RequestMapping("getByNickName")
    public List<UserInfo> getByNickName(String nickName){
        return userInfoService.getByNickName(nickName);
    }

    @RequestMapping("getOne")
    public UserInfo getOne(UserInfo userInfo){
        return userInfoService.getOne(userInfo);
    }
    /**
     * 功能描述: <添加用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 19:43
     */
    @RequestMapping("addUser")
    public void addUser(UserInfo userInfo){
        userInfoService.addUser(userInfo);
    }
    /**
     * 功能描述: <修改用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 19:46
     */
    @RequestMapping("updateUser")
    public void updateUser(UserInfo userInfo){
        userInfoService.updateUser(userInfo);
    }

    /**
     * 功能描述: <删除用户数据>
     * @Author: 三日月
     * @Date: 2019/12/3 19:58
     */
    @RequestMapping("deleteUser")
    public void deleteUser(UserInfo userInfo){
        userInfoService.deleteUser(userInfo);
    }
}