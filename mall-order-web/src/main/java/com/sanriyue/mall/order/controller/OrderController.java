package com.sanriyue.mall.order.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.sanriyue.mall.bean.UserAddress;
import com.sanriyue.mall.service.UserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("getAddress")
    public List<UserAddress> getAddressByUserInfo(UserAddress userAddress){
        return userInfoService.getAddressByUserInfo(userAddress);
    }
}
