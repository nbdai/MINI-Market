package com.dzl.sport.product;

import com.dzl.sport.pojo.BbsProduct;
import com.dzl.sport.pojo.BbsProductWithBLOBs;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    PageInfo<BbsProduct> selectProducts(String name, Integer page,Integer isShow,Long brandId);
    void deleteProducts(Long[] ids);
    BbsProductWithBLOBs selectById(Long id);
    void updateProduct(BbsProductWithBLOBs bbsProductWithBLOBs,String[] imgUrlsl,String[] colorss,String[] sizess);
    //处理上架
    void updateProducts1(Long[] ids);
    //处理下架
    void updateProducts0(Long[] ids);
    //增加商品
    void addProduct(BbsProductWithBLOBs bbsProductWithBLOBs);

}
