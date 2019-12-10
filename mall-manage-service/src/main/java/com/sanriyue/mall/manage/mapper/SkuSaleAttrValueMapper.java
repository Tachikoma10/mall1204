package com.sanriyue.mall.manage.mapper;


import com.sanriyue.mall.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {
    /**
     * 功能描述: <根据spuId查询其下所有的SkuSaleAttrValue表信息>
     * @MethodName: selectSkuSaleAttrValueList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SkuSaleAttrValue>
     * @Author: 三日月
     * @Date: 2019/12/10 19:39
     */
    List<SkuSaleAttrValue> selectSkuSaleAttrValueList(String spuId);
    //第二种实现
    List<Map> getSaleAttrValuesBySpu(String spuId);
}


