package com.sanriyue.mall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
@Data
public class SkuSaleAttrValue implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String skuId;
    @Column
    private String saleAttrId;
    @Column
    String saleAttrValueId;

    @Column
    String saleAttrName;

    @Column
    String saleAttrValueName;

}
