package com.sanriyue.mall.manage.mapper;

import com.sanriyue.mall.bean.BaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo>{
    /**
     * 功能描述: <页面添加sku时对该方法进行修改，要自定义sql语句。因为通用mapper不支持多表查
     * 根据三级分类id，查询平台属性和值>
     * 步骤：写该mapper接口和该方法对应的xml文件，并进行相关配置使其生效。
     * @MethodName: getAttrInfoList
     * @Param: [catalog3Id]
     * @Return: java.util.List<com.sanriyue.mall.bean.BaseAttrInfo>
     * @Author: 三日月
     * @Date: 2019/12/9 18:47
     */
    List<BaseAttrInfo> getAttrInfoList(String catalog3Id);
}
