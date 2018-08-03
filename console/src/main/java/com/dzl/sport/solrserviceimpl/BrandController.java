package com.dzl.sport.solrserviceimpl;

import com.dzl.sport.brand.BrandService;

import com.dzl.sport.pojo.BbsBrand;
import com.github.pagehelper.PageInfo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;

@Controller
@SessionAttributes("flag")
public class BrandController {
    @Resource
    private BrandService brandService;
    @RequestMapping("toUpdatePage")
    public  String toUpdatePage(Long id , Integer page, String name , Integer isDisplay, Model model){
        BbsBrand bbsBrand = brandService.selectById(id);
        model.addAttribute("bbsBrand",bbsBrand);
        model.addAttribute("page",page);
        model.addAttribute("name",name);
        model.addAttribute("isDisplay",isDisplay);


        return "brand/edit";
    }
    @RequestMapping("updateBrand")
    public  String updateBrand(Integer page,String names,Integer isDisplay,BbsBrand bbsBrand ,String[] imgUrls,Model model){
            String flag =  brandService.updateBrand(bbsBrand,imgUrls);
            model.addAttribute("flag",flag);
        return  "redirect:toUpdatePage?page="+page+"&name="+names+"&isDisplay="+isDisplay;
    }
    @RequestMapping("addBrand")
    public String addBrand(String  names,Integer page,Integer isDisplay,BbsBrand bbsBrand,String[] imgUrls,Model model){

        model.addAttribute("name",names);
        model.addAttribute("page",page);
        model.addAttribute("isDisplay",isDisplay);
        brandService.addBrand(bbsBrand,imgUrls);
        return "brand/add";
    }
    @RequestMapping("toBrandAdd")
    public  String toBrandAdd(String name,Integer page,Integer isDisplay,Model model){
          model.addAttribute("name",name);
          model.addAttribute("page",page);
          model.addAttribute("isDisplay",isDisplay);
          return "brand/add";
    }
    @RequestMapping("brandList")
    public String brandList(String name, @RequestParam(required=true,defaultValue="1") Integer page,Integer isDisplay , Model model){

            PageInfo pageInfo =  brandService.selectBrands(name, page,isDisplay);
            System.out.println();
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("page",page);
            model.addAttribute("name",name);
            model.addAttribute("isDisplay",isDisplay);
            return "/brand/list";

    }
    @RequestMapping("deleteBrands")
    public  String deleteBrands(String name,@RequestParam(required=true,defaultValue="1") Integer page,Long[] ids,Integer isDisplay,Model model){
           brandService.deleteBrand(ids);
           return brandList(name,page,isDisplay,model);
    }
}

