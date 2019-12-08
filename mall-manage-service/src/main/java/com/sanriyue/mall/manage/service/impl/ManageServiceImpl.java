package com.sanriyue.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sanriyue.mall.bean.*;
import com.sanriyue.mall.manage.mapper.*;
import com.sanriyue.mall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class ManageServiceImpl implements ManageService{
    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    SpuInfoMapper spuInfoMapper;

    @Autowired
    SpuImageMapper spuImageMapper;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return baseAttrInfoMapper.select(baseAttrInfo);
    }

    /**
     * 功能描述: <页面添加平台属性的功能，要保存属性和属性值列表>
     * @MethodName: saveAttrInfo
     * @Param: [baseAttrInfo]
     * @Return: void
     * @Author: 三日月
     * @Date: 2019/12/6 16:43
     */
    @Override
    @Transactional
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        if (baseAttrInfo.getId()!=null&&baseAttrInfo.getId().length()>0){
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            //保存数据到baseAttrInfo表
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //保存属性值,首先要是清空之前的数据
        //虽然属性值被清空了，但是baseAttrInfo中有更新的数据attrValueList
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        //得到attrValueList集合，遍历添加到baseAttrValue表
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

        if (attrValueList!=null&&attrValueList.size()>0){
            for (BaseAttrValue attrValue : attrValueList) {
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }

    }

    /**
     * 功能描述: <页面点击修改后回显属性值列表>
     * @MethodName: getAttrValueList
     * @Param: [attrId]
     * @Return: java.util.List<com.sanriyue.mall.bean.BaseAttrValue>
     * @Author: 三日月
     * @Date: 2019/12/6 18:26
     */
    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }

    /**
     * 功能描述: <业务角度来实现数据的回显，分两步>
     * @MethodName: getAttrInfo
     * @Param: [attrId]
     * @Return: com.sanriyue.mall.bean.BaseAttrInfo
     * @Author: 三日月
     * @Date: 2019/12/6 19:23
     */
    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(baseAttrValue);

        baseAttrInfo.setAttrValueList(attrValueList);
        return baseAttrInfo;
    }

    /**
     * 功能描述: <获取spu商品列表>
     * @MethodName: getSpuInfoList
     * @Param: [spuInfo]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuInfo>
     * @Author: 三日月
     * @Date: 2019/12/6 20:37
     */
    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        return spuInfoMapper.select(spuInfo);
    }

    /**
     * 功能描述: <实现添加spu时基本销售属性的数据回显>
     * @MethodName: getBaseSaleAttrList
     * @Param: []
     * @Return: java.util.List<com.sanriyue.mall.bean.BaseSaleAttr>
     * @Author: 三日月
     * @Date: 2019/12/7 19:56
     */
    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    /**
     * 功能描述: <页面添加保存spu接口的实现>
     * @MethodName: saveSpuInfo
     * @Param: [spuInfo]
     * @Return: void
     * @Author: 三日月
     * @Date: 2019/12/7 20:37
     */
    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存名称和描述
        spuInfoMapper.insertSelective(spuInfo);
        //保存图片
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (spuImageList!=null&&spuImageList.size()>0){
            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }

        //保存spu销售属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if (spuSaleAttrList!=null && spuSaleAttrList.size()>0){
            for (int i = 0; i < spuSaleAttrList.size(); i++) {
                SpuSaleAttr spuSaleAttr = spuSaleAttrList.get(i);
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);

                //保存spu销售属性值
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if (spuSaleAttrValueList!=null && spuSaleAttrValueList.size()>0){
                    for (int j = 0; j < spuSaleAttrValueList.size(); j++) {
                        SpuSaleAttrValue spuSaleAttrValue = spuSaleAttrValueList.get(j);
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }
            }
        }
    }

    //自定义判断集合是否为空的泛型方法
    //如果参数为ArrayList<T>， 则上述获取的集合类型也要变为ArrayList<T>
    public <T> boolean checkList(List<T> list){
        if (list!=null&&list.size()>0){
            return false;
        }
        return true;
    }
}
