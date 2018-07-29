package com.dzl.sport.serviceimpl;

import com.dzl.sport.mapper.BbsProductMapper;
import com.dzl.sport.pojo.BbsProduct;
import com.dzl.sport.pojo.BbsProductWithBLOBs;
import com.dzl.sport.product.ProductService;
import com.dzl.sport.util.ProductUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    @Autowired
    private BbsProductMapper bbsProductMapper;


    @Override
    public PageInfo<BbsProduct> selectProducts(String name, Integer page, Integer isShow,Long brandId) {
        BbsProduct bbsProduct = new BbsProductWithBLOBs();
        if(name!=null&&!"".equals(name)){
            bbsProduct.setName(name);
        }
        if(isShow!=null){
            if (isShow==1){
                bbsProduct.setIsShow(true);
            }else{
                bbsProduct.setIsShow(false);
            }
        }


        if(brandId!=null&&!"".equals(brandId)&&0!=brandId){
            bbsProduct.setBrandId(brandId);
        }


        PageHelper.startPage(page,4);
        List<BbsProductWithBLOBs> plist =   bbsProductMapper.selectLimit(bbsProduct);
        for(BbsProductWithBLOBs bbsProductWithBLOBs:plist){
            String imgUrl =  bbsProductWithBLOBs.getImgUrl();
            if(imgUrl!=null){
                bbsProductWithBLOBs.setImgUrls(imgUrl.split(","));
            }
            String colors = bbsProductWithBLOBs.getColors();
            if(colors!=null){

                String[] colorss = colors.split(",");

                bbsProductWithBLOBs.setColorss(colorss);
            }
            String sizes = bbsProductWithBLOBs.getSizes();
            if(sizes!=null){
                bbsProductWithBLOBs.setSizess(sizes.split(","));
            }
        }
        PageInfo<BbsProduct> pageInfo = new PageInfo(plist);
        return pageInfo;
    }

    @Override
    public void deleteProducts(Long[] ids) {
       List<Long> longList = new ArrayList<Long>();
       for (Long id:ids){
           longList.add(id);
       }
       bbsProductMapper.deleteProducts(longList);
    }

    @Override
    public BbsProductWithBLOBs selectById(Long id) {
        if(id==null||"".equals(String.valueOf(id))){
            return  null;
        }
       BbsProductWithBLOBs bbsProductWithBLOBs =   bbsProductMapper.selectByPrimaryKey(id);
       String imgUrl =  bbsProductWithBLOBs.getImgUrl();
       if(imgUrl!=null){
           bbsProductWithBLOBs.setImgUrls(imgUrl.split(","));
       }

       String colors = bbsProductWithBLOBs.getColors();
        if(colors!=null){
            bbsProductWithBLOBs.setColorss(colors.split(","));
        }
       String sizes = bbsProductWithBLOBs.getSizes();
       if(sizes!=null){
           bbsProductWithBLOBs.setSizess(sizes.split(","));
       }
       return bbsProductWithBLOBs;
    }


    @Override
    public void updateProduct(BbsProductWithBLOBs bbsProductWithBLOBs,String[] imgUrlsl,String[] colorss,String[] sizess) {
           //ProductUtil为自己封装的工具类
         ProductUtil<String> productUtil = new ProductUtil<>();
         String imgUrl = productUtil.getSplitObject(imgUrlsl);
         bbsProductWithBLOBs.setImgUrl(imgUrl);

         String colors = productUtil.getSplitObject(colorss);
         bbsProductWithBLOBs.setColors(colors);

         String sizes = productUtil.getSplitObject(sizess);
         bbsProductWithBLOBs.setSizes(sizes);

         bbsProductMapper.updateByPrimaryKeySelective(bbsProductWithBLOBs);
    }

    @Override
    public void updateProducts1(Long[] ids) {
        //处理上架的service
       List<Long> longList = new ArrayList<>();
       for(Long id:ids){
           longList.add(id);
       }
         bbsProductMapper.updateProduct1(longList);
    }

    @Override
    public void updateProducts0(Long[] ids) {
       //处理上架的service
        List<Long> longList = new ArrayList<>();
        for(Long id:ids){
            longList.add(id);
        }
        bbsProductMapper.updateProduct0(longList);
    }
}
