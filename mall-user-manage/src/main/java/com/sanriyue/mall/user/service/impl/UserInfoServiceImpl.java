package com.sanriyue.mall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sanriyue.mall.bean.UserAddress;
import com.sanriyue.mall.bean.UserInfo;
import com.sanriyue.mall.service.UserInfoService;
import com.sanriyue.mall.user.mapper.UserAddressMapper;
import com.sanriyue.mall.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;
    //返回用户数据
    @Override
    public List<UserInfo> getAll() {

        return userInfoMapper.selectAll();
    }

    @Override
    public List<UserInfo> getByUserInfo(UserInfo userInfo) {

        return userInfoMapper.select(userInfo);
    }

    @Override
    public List<UserInfo> getByNickName(String nickName) {
        //模糊查询sql:select * from user_info where nickName like "%a%";
        Example example = new Example(UserInfo.class);
        example.createCriteria().andLike("nickName","%"+nickName+"%");

        return userInfoMapper.selectByExample(example);
    }

    @Override
    public UserInfo getOne(UserInfo userInfo) {
        UserInfo user = userInfoMapper.selectOne(userInfo);
        return user;
    }
    //添加用户数据
    @Override
    public void addUser(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);
    }

    //更新用户数据
    @Override
    public void updateUser(UserInfo userInfo) {
        //例如：按照用户nickName修改loginName
        //sql:update user_info set loginName = ? where nickName = ?;
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("nickName",userInfo.getNickName());
        userInfoMapper.updateByExampleSelective(userInfo,example);
    }

    //删除用户数据
    @Override
    public void deleteUser(UserInfo userInfo) {
        userInfoMapper.delete(userInfo);
    }

    //由用户ID等信息查询地址
    @Override
    public List<UserAddress> getAddressByUserInfo(UserAddress userAddress) {
        return userAddressMapper.select(userAddress);
    }

}