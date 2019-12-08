package com.sanriyue.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class SpuInfo implements Serializable{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //数据库中字段类型为bigInt
    private String id;

    @Column
    private String spuName;
    @Column
    private String description;
    @Column
    private String catalog3Id;

    @Transient
    private List<SpuImage> spuImageList;
    @Transient
    private List<SpuSaleAttr> spuSaleAttrList;
   //TODO 品牌id
}
