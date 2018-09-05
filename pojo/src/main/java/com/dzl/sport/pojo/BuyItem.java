package com.dzl.sport.pojo;

import java.io.Serializable;
import java.util.Objects;

public class BuyItem implements Serializable{
    private BbsSku sku;
    //是否有货.
    private Boolean isHave;
    //购买数量
    private Integer count;
    private BbsProductWithBLOBs product;

    public BbsProductWithBLOBs getProduct() {
        return product;
    }

    public void setProduct(BbsProductWithBLOBs product) {
        this.product = product;
    }

    public BbsSku getSku() {
        return sku;
    }

    public void setSku(BbsSku sku) {
        this.sku = sku;
    }

    public Boolean getHave() {
        return isHave;
    }

    public void setHave(Boolean have) {
        isHave = have;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
