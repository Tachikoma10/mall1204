package com.sanriyue.mall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.sanriyue.mall.bean.SkuInfo;
import com.sanriyue.mall.bean.SkuSaleAttrValue;
import com.sanriyue.mall.bean.SpuSaleAttr;
import com.sanriyue.mall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {
    @Reference
    private ManageService manageService;

    @RequestMapping("{skuId}.html")
    public String skuInfoPage(@PathVariable String skuId, HttpServletRequest request){
        //由100009177376.html(skuId)获得skuInfo基本信息
        SkuInfo skuInfo = manageService.getSkuInfoPage(skuId);
        //需要回显spu的销售属性和值，sql是重点
        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrListCheckBySku(skuInfo);

        //根据前端选定的销售属性值确定sku,并切换页面
        //后台要生成"129|130":"37"这样的json串
        String spuId = skuInfo.getSpuId();
        /*List<SkuSaleAttrValue> skuSaleAttrValueList= manageService.getSkuSaleAttrValueList(spuId);
        //对数据格式做处理使其与前端页面相匹配
        //首先要将数据放入Java的map中，然后转为对应的json串，map的key为"129|130"格式(sale_attr_value_id)
        //value为"37"(skuId)
        String key = "";
        Map<String,String> map = new HashMap<>();
        //遍历上面的集合将结果修改后放入map
        for (int i = 0; i < skuSaleAttrValueList.size(); i++) {
            //将saleAttrId值改为|的格式，
            SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueList.get(i);
            String saleAttrValueId = skuSaleAttrValue.getSaleAttrValueId();
            if (key.length()!=0){
                key+="|";
            }
            key+=saleAttrValueId; //"129"
            //注意每次拼接的结束位置,放入map并要清空每次的key，开始下次拼接
            if (i+1==skuSaleAttrValueList.size()||!skuSaleAttrValue.getSkuId().equals(skuSaleAttrValueList.get(i+1).getSkuId())){
                map.put(key,skuSaleAttrValue.getSkuId());
                key="";
            }
        }*/
        //第二种实现
        Map map = manageService.getSkuValueIdsMap(skuInfo.getSpuId());
        //最后要将map变为json
        String valuesSkuJson = JSON.toJSONString(map);

        request.setAttribute("skuInfo",skuInfo);
        request.setAttribute("spuSaleAttrList",spuSaleAttrList);
        request.setAttribute("valuesSkuJson",valuesSkuJson);
        return "item";
    }
}
