package com.sanriyue.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sanriyue.mall.bean.*;
import com.sanriyue.mall.config.RedisUtil;
import com.sanriyue.mall.manage.constant.ManageConst;
import com.sanriyue.mall.manage.mapper.*;
import com.sanriyue.mall.service.ManageService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.fn.timer.TimeUtils;
import redis.clients.jedis.Jedis;


import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ManageServiceImpl implements ManageService{
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    RedisUtil redisUtil;

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

        return baseAttrInfoMapper.getAttrInfoList(catalog3Id);
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

    @Override
    public List<SpuImage> getSpuImageList(String spuId) {
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    /**
     * 功能描述: <要查询spu销售属性和销售属性值，不能用通用mapper了。自定义sql查询>
     * @MethodName: getSpuSaleAttrList
     * @Param: [spuId]
     * @Return: java.util.List<com.sanriyue.mall.bean.SpuSaleAttr>
     * @Author: 三日月
     * @Date: 2019/12/9 19:15
     */
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {

        return spuSaleAttrMapper.getSpuSaleAttrList(spuId);
    }

    /**
     * 功能描述: <实现添加skuInfo的最后一步保存>
     * @MethodName: saveSkuInfo
     * @Param: [spuInfo]
     * @Return: void
     * @Author: 三日月
     * @Date: 2019/12/9 19:46
     */
    @Override
    @Transactional
    public void saveSkuInfo(SkuInfo skuInfo) {
        //先保存skuInfo中的基本属性
        skuInfoMapper.insertSelective(skuInfo);
        //保存sku平台属性值的时候需要遍历
        //先删除后插入
        /*SkuAttrValue skuAttrValue = new SkuAttrValue();
        skuAttrValue.setSkuId(skuInfo.getId());
        skuAttrValueMapper.delete(skuAttrValue);*/

        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if (checkList(skuAttrValueList)){
            for (SkuAttrValue attrValue : skuAttrValueList) {
                attrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insertSelective(attrValue);
            }
        }

        //保存sku图片，原理同上
        /*SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuInfo.getId());
        skuImageMapper.delete(skuImage);*/

        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (checkList(skuImageList)){
            for (SkuImage image : skuImageList) {
                image.setSkuId(skuInfo.getId());
                skuImageMapper.insertSelective(image);
            }
        }

        //保存sku销售属性
        /*SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuInfo.getId());
        skuSaleAttrValueMapper.delete(skuSaleAttrValue);*/

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if (checkList(skuSaleAttrValueList)){
            for (SkuSaleAttrValue saleAttrValue : skuSaleAttrValueList) {
                saleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insertSelective(saleAttrValue);
            }
        }
    }

    /**
     * 功能描述: <实现商品详情页面数据的展示>
     * @MethodName: getSkuInfoPage
     * @Param: [skuId]
     * @Return: com.sanriyue.mall.bean.SkuInfo
     * @Author: 三日月
     * @Date: 2019/12/10 12:34
     */
    @Override
    public SkuInfo getSkuInfoPage(String skuId){
        //return getSkuInfoJava(skuId);

        return getSkuInfoRedisson(skuId);
    }

    private SkuInfo getSkuInfoJava(String skuId) {
        //商品详情页面高频访问，要整合redis缓存
        SkuInfo skuInfo = null;
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            String skuInfoKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;

            String skuInfoJson = jedis.get(skuInfoKey);
            if (skuInfoJson==null || skuInfoJson.length()==0){
                //缓存中没有数据，要走数据库，加锁，取出后再放入redis缓存，解锁
                //定义锁的key
                String skuLockKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKULOCK_SUFFIX;
                //锁的值
                String token = UUID.randomUUID().toString().replaceAll("-","");
                //执行锁 SET key value PX millisecond 效果等同于 PSETEX key millisecond value
                String lock = jedis.set(skuLockKey, token, "NX", "PX", ManageConst.SKULOCK_EXPIRE_PX);
                if ("OK".equals(lock)){
                    skuInfo = getSkuInfoDB(skuId);
                    String jsonString  = JSON.toJSONString(skuInfo);
                    //SET key value EX second 效果等同于 SETEX key second value
                    jedis.setex(skuInfoKey,ManageConst.SKUKEY_TIMEOUT,jsonString);

                    // 解锁  jedis.del(lockKey); 误删锁！ lua 脚本
                    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    // 执行删除锁！
                    jedis.eval(script, Collections.singletonList(skuLockKey), Collections.singletonList(token));
                    return skuInfo;
                }else{
                    Thread.sleep(1000);

                    return getSkuInfoPage(skuId);
                }
            }else{
                //将Json转换为SkuInfo对象
                skuInfo = JSON.parseObject(skuInfoJson,SkuInfo.class);
                return skuInfo;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                // 关闭
                jedis.close();
            }
        }
        return getSkuInfoRedisson(skuId);
    }

    private SkuInfo getSkuInfoRedisson(String skuId) {
        //使用redission进行缓存设置
        SkuInfo skuInfo = null;
        Jedis jedis = null;
        RLock lock = null;

        try {
            jedis = redisUtil.getJedis();
            String skuInfoKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;
            if (jedis.exists(skuInfoKey)){
                //从缓存中获取
                String skuInfoJson = jedis.get(skuInfoKey);
                //转化为对象返回
                if (!StringUtils.isEmpty(skuInfoJson)){
                    skuInfo = JSON.parseObject(skuInfoJson, skuInfo.getClass());
                    return skuInfo;
                }
            }else{
                Config config = new Config();
                config.useSingleServer().setAddress("redis://192.168.186.129:6379");
                RedissonClient redisson = Redisson.create(config);
                lock = redisson.getLock("my-lock");
                //lock.lock(10, TimeUnit.SECONDS);
                boolean b = lock.tryLock(100, 10, TimeUnit.SECONDS);
                if (b){
                    try {
                        skuInfo = getSkuInfoDB(skuId);
                        //设置过期时间
                        jedis.setex(skuInfoKey,ManageConst.SKUKEY_TIMEOUT,JSON.toJSONString(skuInfo));
                    } finally {
                        lock.unlock();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis!=null){
                jedis.close();
            }
        }
        //从数据库查
        return getSkuInfoDB(skuId);
    }

    private SkuInfo getSkuInfoDB(String skuId) {
        SkuInfo skuInfo;
        skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        //封装sku图片列表数据
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);
        skuInfo.setSkuImageList(skuImageList);
        return skuInfo;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {

        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuInfo.getId(),skuInfo.getSpuId());
    }

    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueList(String spuId) {
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueList(spuId);
    }

    @Override
    public Map getSkuValueIdsMap(String spuId) {
        List<Map> mapList = skuSaleAttrValueMapper.getSaleAttrValuesBySpu(spuId);
        Map<Object, Object> hahMap = new HashMap<>();
        for (Map map : mapList) {
            hahMap.put(map.get("value_ids"),map.get("sku_id"));
        }
        return hahMap;
    }


    //自定义判断集合是否为空的泛型方法
    //如果参数为ArrayList<T>， 则上述获取的集合类型也要变为ArrayList<T>
    public <T> boolean checkList(List<T> list){
        if (list!=null&&list.size()>0){
            return true;
        }
        return false;
    }
}
