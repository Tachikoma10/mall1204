package com.sanriyue.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sanriyue.mall.bean.SkuInfo;
import com.sanriyue.mall.bean.SpuImage;
import com.sanriyue.mall.bean.SpuInfo;
import com.sanriyue.mall.bean.SpuSaleAttr;
import com.sanriyue.mall.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SkuInfoController {
    @Reference
    private ManageService manageService;

    /**
     * 功能描述: <由spuId得到spu销售属性和值的回显数据>
     * @MethodName: getSpuSaleAttrList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuSaleAttr>
     * @Author: 三日月
     * @Date: 2019/12/9 19:43
     */
    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId){
        return manageService.getSpuSaleAttrList(spuId);
    }

    /**
     * 功能描述: <添加sku时spu中图片的回显>
     * @MethodName: getSpuImageList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuImage>
     * @Author: 三日月
     * @Date: 2019/12/9 19:09
     */
    @RequestMapping("spuImageList")
    public List<SpuImage> getSpuImageList(String spuId){

        return manageService.getSpuImageList(spuId);
    }
    /**
     * 功能描述: <页面所有提交数据的保存>
     * @MethodName: saveSkuInfo
     * @Param: [skuInfo]
     * @Return: void
     * @Author: 三日月
     * @Date: 2019/12/9 20:04
     */
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){

        manageService.saveSkuInfo(skuInfo);
    }
}
