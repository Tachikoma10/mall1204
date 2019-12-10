package com.sanriyue.mall.manage.mapper;

import com.sanriyue.mall.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    /**
     * 功能描述: <根据spuId查询spu销售属性和值，去自定义xml>
     * @MethodName: getSpuSaleAttrList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuSaleAttr>
     * @Author: 三日月
     * @Date: 2019/12/9 19:16
     */
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    /**
     * 功能描述: <实现页面详情中销售属性回显>
     * @MethodName: selectSpuSaleAttrListCheckBySku
     * @Param: [id, spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuSaleAttr>
     * @Author: 三日月
     * @Date: 2019/12/10 19:06
     */
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(String id, String spuId);
}
