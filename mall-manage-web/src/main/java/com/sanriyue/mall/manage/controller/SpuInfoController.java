package com.sanriyue.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sanriyue.mall.bean.BaseSaleAttr;
import com.sanriyue.mall.bean.SpuImage;
import com.sanriyue.mall.bean.SpuInfo;
import com.sanriyue.mall.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SpuInfoController {

    @Reference
    private ManageService manageService;

    // http://localhost:8082/baseSaleAttrList
    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> getBaseSaleList(){

        return manageService.getBaseSaleAttrList();
    }
    // http://localhost:8082/saveSpuInfo
    @RequestMapping("saveSpuInfo")
    public void saveSpuInfo(@RequestBody SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
    }


}
