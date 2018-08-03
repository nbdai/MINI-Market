package com.dzl.sport.pcontroller;

import com.dzl.sport.brand.BrandService;
import com.dzl.sport.pojo.BbsBrand;
import com.dzl.sport.solr.ProductSolrService;
import com.dzl.sport.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ProductSolrController {
    @Resource
    private ProductSolrService productSolrService;
    @Resource
    private BrandService brandService;
    @RequestMapping("/search")
    public String search(String keyword, Integer pageNo, Integer pageSize, Long brandId,String priceStr,Model model){
        Pagination pagination = productSolrService.selectProductSolr(keyword,pageNo,pageSize,brandId,priceStr);
        List<BbsBrand> blist = brandService.selectAll();
        model.addAttribute("blist",blist);
        model.addAttribute("brandId",brandId);
        model.addAttribute("pagination",pagination);
        model.addAttribute("priceStr",priceStr);
        model.addAttribute("keyword",keyword);
        return "search";
    }
}
