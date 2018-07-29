package com.dzl.sport.serviceimpl;

import com.dzl.sport.brand.BrandService;
import com.dzl.sport.mapper.BbsBrandMapper;
import com.dzl.sport.mapper.BbsIdMapper;
import com.dzl.sport.pojo.BbsBrand;
import com.dzl.sport.util.ProductUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service("brandService")
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BbsBrandMapper  bbsBrandMapper;
    @Autowired
    private BbsIdMapper bbsIdMapper;


    public PageInfo<BbsBrand> selectBrands(String name,Integer page,Integer isDisplay) {
      /*  if(null==name||"".equals(name)||"null".equals(name)){
            PageHelper.startPage(page,4);
            List<BbsBrand> blist =  bbsBrandMapper.selectAll();
            PageInfo<BbsBrand> pageInfo = new PageInfo(blist);

            return pageInfo;
        }*/
         BbsBrand bbsBrand = new BbsBrand();
         if(name!=null&&!"".equals(name)){
             bbsBrand.setName(name);
         }
         if(isDisplay!=null){
             if(isDisplay==1){
                 bbsBrand.setIsDisplay(true);
             }else{
                 bbsBrand.setIsDisplay(false);
             }
         }
         PageHelper.startPage(page,4);
         List<BbsBrand> blist =  bbsBrandMapper.selectListLimit(bbsBrand);
         PageInfo<BbsBrand> pageInfo = new PageInfo(blist);
         return pageInfo;
    }

    @Override
    public void deleteBrand(Long[] ids) {
        List<Long> idlist = new ArrayList<Long>();
        for(Long id:ids){
            idlist.add(id);
        }
        bbsBrandMapper.deleteBrands(idlist);
    }

    @Override
    public List<BbsBrand> selectAll() {
        return bbsBrandMapper.selectAll();
    }

    @Override
    public BbsBrand selectById(Long id) {
        return bbsBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addBrand(BbsBrand bbsBrand,String[] imgUrls) {
           ProductUtil<String> productUtil = new ProductUtil<>();
           String imgUrl = productUtil.getSplitObject(imgUrls);
           Long ids = bbsIdMapper.selectId();
           bbsBrand.setId(ids);
           bbsBrand.setId(ids);
           bbsBrand.setImgUrl(imgUrl);
           bbsIdMapper.updateId(ids+1);
           bbsBrandMapper.insertSelective(bbsBrand);
    }


    @Override
    public String updateBrand(BbsBrand bbsBrand,String [] imgUrls) {
        ProductUtil<String> productUtil = new ProductUtil<>();
        String imgUrl = productUtil.getSplitObject(imgUrls);
        bbsBrand.setImgUrl(imgUrl);
        int i = bbsBrandMapper.updateByPrimaryKeySelective(bbsBrand);
        if(i>0){
            return "更新成功!!!";
        }else {
            return "更新失败!!!";
        }
    }


}
