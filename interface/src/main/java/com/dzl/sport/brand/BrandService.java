package com.dzl.sport.brand;

import com.dzl.sport.pojo.BbsBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface BrandService {
    PageInfo<BbsBrand> selectBrands(String name, Integer page,Integer isDisplay);
    void deleteBrand(Long[] ids);
    List<BbsBrand> selectAll();
    BbsBrand selectById(Long id);
    void addBrand(BbsBrand bbsBrand,String[] imgUrls);
    String updateBrand(BbsBrand bbsBrand,String[] imgUrls);
}
