package com.sanriyue.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuInfo implements Serializable{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column
    String spuId;
    @Column
    String skuName;
    @Column
    String skuDesc;
    @Column
    //对应数据库中的浮点数类型
    BigDecimal weight;
    @Column
    BigDecimal price;
    @Column
    String catalog3Id;
    @Column
    String skuDefaultImg;

    @Transient
    List<SkuAttrValue> skuAttrValueList;
    @Transient
    List<SkuImage> skuImageList;
    @Transient
    List<SkuSaleAttrValue> skuSaleAttrValueList;

}
