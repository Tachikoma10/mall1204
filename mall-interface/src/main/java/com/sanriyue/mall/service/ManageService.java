package com.sanriyue.mall.service;

import com.sanriyue.mall.bean.*;

import java.util.List;

public interface ManageService {
    List<BaseCatalog1> getCatalog1();

    List<BaseCatalog2> getCatalog2(String catalog1Id);

    List<BaseCatalog3> getCatalog3(String catalog2Id);

    List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueList(String attrId);

    BaseAttrInfo getAttrInfo(String attrId);

    List<SpuInfo> getSpuInfoList(SpuInfo spuInfo);

    List<BaseSaleAttr> getBaseSaleAttrList();

    /**
     * 功能描述: <页面添加spuInfo的接口>
     * @Author: 三日月
     * @Date: 2019/12/7 20:35
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 功能描述: <页面做sku添加时，图片的回显>
     * @MethodName: getSpuImageList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuImage>
     * @Author: 三日月
     * @Date: 2019/12/9 16:34
     */
    List<SpuImage> getSpuImageList(String spuId);

    /**
     * 功能描述: <添加sku时spuSaleAttr的回显>
     *     问题？？前端的下拉框没有显示，数据回显后动态加载进来了
     * @MethodName: getspuSaleAttrList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuSaleAttr>
     * @Author: 三日月
     * @Date: 2019/12/9 19:11
     */
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    /**
     * 功能描述: <进行skuInfo的最终保存>
     * @MethodName: saveSkuInfo
     * @Param: [spuInfo]
     * @Return: void
     * @Author: 三日月
     * @Date: 2019/12/9 19:45
     */
    void saveSkuInfo(SkuInfo skuInfo);
}
