package com.dzl.sport.solrserviceimpl;

import com.dzl.sport.color.ColorService;
import com.dzl.sport.pojo.BbsColor;
import com.dzl.sport.pojo.BbsSku;
import com.dzl.sport.sku.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.List;

/**
 * 处理库存的controller.
 */
@Controller
public class SkuController {
    @Resource
    private SkuService skuService;
    @Resource
    private ColorService colorService;
    @RequestMapping("skuList")
    public String skuLists(Long pid,String name,Integer page,Integer isShow, Long brandId,Model model){
        model.addAttribute("pid",pid);
        List<BbsSku> skuList = skuService.skuList(pid);
        List<BbsColor> bbsColorList = colorService.getColors();
        model.addAttribute("skuList",skuList);
        model.addAttribute("name",name);
        model.addAttribute("page",page);
        model.addAttribute("isShow",isShow);
        model.addAttribute("brandId",brandId);
        model.addAttribute("colorList",bbsColorList);
        return "sku/list";
    }
}
