package com.dzl.sport.controller;

        import com.dzl.sport.brand.BrandService;
        import com.dzl.sport.color.ColorService;
        import com.dzl.sport.pojo.BbsBrand;
        import com.dzl.sport.pojo.BbsColor;
        import com.dzl.sport.pojo.BbsProduct;
        import com.dzl.sport.pojo.BbsProductWithBLOBs;
        import com.dzl.sport.product.ProductService;
        import com.github.pagehelper.PageInfo;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;
        import javax.annotation.Resource;
        import java.util.List;

/**
 * @author DZL
 * @desc 商品处理类
 */
@Controller
public class ProductController {
    @Resource
    private ProductService productService;
    @Resource
    private BrandService brandService;
    @Resource
    private ColorService colorService;
    @RequestMapping("addProduct")
    public String addProduct(String names, Integer page, Integer isShows , Long brandIds,  BbsProductWithBLOBs bbsProductWithBLOBs ,Model model){
        productService.addProduct(bbsProductWithBLOBs);
        model.addAttribute("name",names);
        model.addAttribute("page",page);
        model.addAttribute("isShows",isShows);
        model.addAttribute("brandIds",brandIds);
        return "product/add";
    }
    @RequestMapping("updateProducts1")
    public String updateProducts1(String name, Integer page, Integer isShow, Long brandId, Long[] ids, RedirectAttributes redirectAttributes){
       //处理上架
       productService.updateProducts1(ids);
       redirectAttributes.addAttribute("name",name);
       redirectAttributes.addAttribute("page",page);
       redirectAttributes.addAttribute("isShow",isShow);
       redirectAttributes.addAttribute("brandId",brandId);
       return  "redirect:productList";
    }
    @RequestMapping("updateProducts0")
    public String updateProducts0(String name,Integer page,Integer isShow,Long brandId,Long[] ids,RedirectAttributes redirectAttributes){
        //处理下架
        productService.updateProducts0(ids);
        redirectAttributes.addAttribute("name",name);
        redirectAttributes.addAttribute("page",page);
        redirectAttributes.addAttribute("isShow",isShow);
        redirectAttributes.addAttribute("brandId",brandId);
        return  "redirect:productList";
    }
    @RequestMapping("productUpdate")
    public String productUpdate(String names,Integer page,Integer isShows,Long brandIds,BbsProductWithBLOBs bbsProductWithBLOBs,String[] imgUrlsl,String[] colorss,String[] sizess,Model model){
        productService.updateProduct(bbsProductWithBLOBs,imgUrlsl,colorss,sizess);
        model.addAttribute("name",names);
        model.addAttribute("page",page);
        model.addAttribute("isShow",isShows);
        model.addAttribute("brandId",brandIds);
        return "product/update";
    }
    @RequestMapping("productView")
    public String productView(Long pid,Integer page,String name,Integer isShow,Long brandId,Model model){
        BbsProductWithBLOBs bbsProduct = productService.selectById(pid);
        List<BbsColor> colorList = colorService.getColors();
        List<BbsBrand> blist = brandService.selectAll();
        model.addAttribute("page",page);
        model.addAttribute("name",name);
        model.addAttribute("isShow",isShow);
        model.addAttribute("brandId",brandId);
        model.addAttribute("bbsProduct",bbsProduct);
        model.addAttribute("colorList",colorList);
        model.addAttribute("blist",blist);
        return  "product/view";
    }
    @RequestMapping("toUpdateProduct")
    public String toUpdateProduct(Long id,Integer page,String name,Integer isShow,Long brandId,Model model){
        BbsProductWithBLOBs bbsProduct = productService.selectById(id);
        List<BbsColor> colorList = colorService.getColors();
        List<BbsBrand> blist = brandService.selectAll();
        model.addAttribute("page",page);
        model.addAttribute("name",name);
        model.addAttribute("isShow",isShow);
        model.addAttribute("brandId",brandId);
        model.addAttribute("bbsProduct",bbsProduct);
        model.addAttribute("colorList",colorList);
        model.addAttribute("blist",blist);
        return  "product/update";
    }
    @RequestMapping("toProductAdd")
    public String toProductAdd(String name, Integer page, Integer isShow ,Long brandId,Model model){
        List<BbsColor> colorList = colorService.getColors();
        List<BbsBrand> brandList = brandService.selectAll();
        model.addAttribute("name",name);
        model.addAttribute("page",page);
        model.addAttribute("isShow",isShow);
        model.addAttribute("brandId",brandId);
        model.addAttribute("colorList",colorList);
        model.addAttribute("brandList",brandList);
        return "product/add";
    }
    @RequestMapping("productList")
    public String productList(String name, @RequestParam(required=true,defaultValue="1") Integer page, Integer isShow ,Long brandId, Model model){
        PageInfo pageInfo =  productService.selectProducts(name,page,isShow,brandId);
        List<BbsBrand> bbsBrandList = brandService.selectAll();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("page",page);
        model.addAttribute("name",name);
        model.addAttribute("isShow",isShow);
        model.addAttribute("brandId",brandId);
        model.addAttribute("blist",bbsBrandList);
        return "/product/list";
    }
    @RequestMapping("deleteProducts")
    public String deleteProducts(Long[] ids,@RequestParam(required=true,defaultValue="1")Integer page,String name,Integer isShow,Long brandId,Model model){
          productService.deleteProducts(ids);
           return  productList(name,page,isShow,brandId,model);
}

}
