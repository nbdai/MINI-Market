package com.dzl.sport.serviceimpl;

import com.dzl.sport.mapper.BbsColorMapper;
import com.dzl.sport.mapper.BbsIdMapper;
import com.dzl.sport.mapper.BbsProductMapper;
import com.dzl.sport.mapper.BbsSkuMapper;
import com.dzl.sport.pojo.BbsColor;
import com.dzl.sport.pojo.BbsProduct;
import com.dzl.sport.pojo.BbsProductWithBLOBs;
import com.dzl.sport.pojo.BbsSku;
import com.dzl.sport.product.ProductService;
import com.dzl.sport.util.ProductUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.sport.staticpage.StaticPageServiceImpl;
import com.sun.jmx.snmp.Timestamp;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    @Autowired
    private BbsProductMapper bbsProductMapper;
    @Autowired
    private BbsIdMapper bbsIdMapper;
    @Autowired
    private HttpSolrServer solrServer;
    @Autowired
    private BbsSkuMapper bbsSkuMapper;
    @Autowired
    private BbsColorMapper bbsColorMapper;
    @Autowired
    private StaticPageServiceImpl staticPageService;
    @Override
    public PageInfo<BbsProduct> selectProducts(String name, Integer page, Integer isShow,Long brandId) {
        BbsProduct bbsProduct = new BbsProductWithBLOBs();
        if(name!=null&&!"".equals(name)){
            bbsProduct.setName(name);
        }
        if(isShow!=null){
            if (isShow==1){
                bbsProduct.setIsShow(true);
            }else if(isShow==0){
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
       for(Long id:ids) {
           longList.add(id);
           //静态化
           List<BbsSku> skus = bbsSkuMapper.selectByPid(id);
           BbsProductWithBLOBs product = bbsProductMapper.selectByPrimaryKey(id);
           product.setPrice(bbsSkuMapper.selectMinPrice(id));
           product.setImgUrls(product.getImgUrl().split(","));
           Set<BbsColor> colors = new HashSet<BbsColor>();
           for(BbsSku sku :skus){
               BbsColor color = bbsColorMapper.selectByPrimaryKey(sku.getColorId());
               colors.add(color);
           }
           Map<String,Object> root = new HashMap<>();
           root.put("skus",skus);
           root.put("colors",colors);
           root.put("product",product);
           staticPageService.productStaticPage(root,String.valueOf(id));
       }
        bbsProductMapper.updateProduct1(longList);
        //向solr中存数据。
         addSolr(ids);

    }
     private void addSolr(Long[] ids){
         for(Long id:ids) {
             SolrInputDocument solrInputDocument = new SolrInputDocument();
             solrInputDocument.addField("id",id);
             BbsProductWithBLOBs bbsProductWithBLOBs = bbsProductMapper.selectByPrimaryKey(id);
             solrInputDocument.addField("brandId",bbsProductWithBLOBs.getBrandId());
             solrInputDocument.addField("name_ik",bbsProductWithBLOBs.getName());
             solrInputDocument.addField("imgUrls",bbsProductWithBLOBs.getImgUrl().split(","));
             solrInputDocument.addField("price",bbsSkuMapper.selectMinPrice(id));
             try {
                 solrServer.add(solrInputDocument);
             } catch (SolrServerException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 solrServer.commit();
             } catch (SolrServerException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
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

    @Override
    public void addProduct(BbsProductWithBLOBs bbsProductWithBLOBs) {

         ProductUtil<String> productUtil = new ProductUtil();
         String imgUrl =  productUtil.getSplitObject(bbsProductWithBLOBs.getImgUrls());
         String color = productUtil.getSplitObject(bbsProductWithBLOBs.getColorss());
         String  size = productUtil.getSplitObject(bbsProductWithBLOBs.getSizess());
         bbsProductWithBLOBs.setImgUrl(imgUrl);
         bbsProductWithBLOBs.setColors(color);
         bbsProductWithBLOBs.setSizes(size);
         bbsProductWithBLOBs.setIsDel(true);
         bbsProductWithBLOBs.setIsShow(false);
         String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
         bbsProductWithBLOBs.setCreateTime(java.sql.Timestamp.valueOf(nowTime));
         //全局Id
        synchronized (bbsIdMapper){
            Long pid = bbsIdMapper.selectAll().getTempPid();
            bbsProductWithBLOBs.setId(pid);
            bbsProductMapper.insertSelective(bbsProductWithBLOBs);
            bbsIdMapper.updatePid();
        }


    }
}
