package com.dzl.sport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CenterController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("top")
    public String top(){
        return "top";
    }
    @RequestMapping("main")
    public String main(){
        return "main";
    }
    @RequestMapping("left")
    public String left(){
        return "left";
    }
    @RequestMapping("right")
    public String right(){
        return "right";
    }
   /* @RequestMapping("order_main")
    public  String order_main(){
        return  "/frame/order_main";
    }*/
   /*@RequestMapping("frame/order_main")
   public  String frame(){
       return "frame/order_main";
   }*/
    @RequestMapping("product_main")
    public String product_main(){
        return "/frame/product_main";
    }

    @RequestMapping("product_left")
    public String product_left(){
        return "/frame/product_left";
    }

  /*  @RequestMapping("brandList")
    public String brandList(){
        return "/brand/list";
    }*/


    /* @RequestMapping("productList")
      public String productList(){
          return "/product/list";
      }*/


}
