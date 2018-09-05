package com.dzl.sport.pcontroller;

import com.dzl.sport.color.ColorService;
import com.dzl.sport.login.LoginService;
import com.dzl.sport.pojo.*;
import com.dzl.sport.product.ProductService;
import com.dzl.sport.sku.SkuService;
import com.dzl.sport.util.RequestUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class CartController {
    @Resource
    private SkuService skuService;
    @Resource
    private ProductService productService;
    @Resource
    private ColorService colorService;
    @Autowired
    private JedisPool jedisPool;
    @Resource
    private LoginService loginService;
    @RequestMapping("addCart")
    public String addCart(String sid, String buyNum, String url, HttpServletRequest request, HttpServletResponse response){
        Long sids = Long.parseLong(sid.replaceAll(",",""));
        Integer buyNums = Integer.parseInt(buyNum);
        //json数据 与  对象互相转化用。
        ObjectMapper objectMapper = new ObjectMapper();
        //null不要序列化了。
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Cookie[]  cookies =  request.getCookies();
        //得到唯一的csessionid(有直接用，没有创建一个)判断是否登陆。  //登陆用csessionid做键 ，用username做值
        String csessionid =  RequestUtils.getCSESSIONID(request,response);
        boolean flag = false;//用于记录是否登陆
        String username = null;
        for(Cookie cookie:cookies){
            if(csessionid.equals(cookie.getName())){
                flag = true;
                username = cookie.getValue();
                break;
            }
        }
        //没有登陆存到cookie
        if(!flag){
            boolean bflag = false;//用于记录是否有购物车。
            String  bbs = null;
            for(Cookie cookie:cookies){
                if("jdbuycart".equals(cookie.getName())){
                    //说明有购物车。
                    try {
                        bbs  = URLDecoder.decode(cookie.getValue(),"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    bflag = true;
                    break;
                }
            }
            List<BuyItem> buyItems = null;
            if(bflag){
                //有 取出，并取出购物车信息.
                try {
                    BbsCart bbsCart =  objectMapper.readValue(bbs, BbsCart.class);
                    buyItems = bbsCart.getItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                //没有购物车，新建。
                buyItems = new ArrayList<>();
            }

            BbsSku bbsSku = skuService.selectById(sids);
            boolean bl = false;
            for(BuyItem bi : buyItems){
                //若有此商品就增加数量，不增加对象。
                if(bi.getSku().getId()==sids){
                    bi.setCount(bi.getCount()+buyNums);
                    bi.setHave(bbsSku.getStock()>=bi.getCount());
                    bl = true;
                    break;
                }
            }
            if(!bl){
                //没有此商品：
                BuyItem buyItem = new BuyItem();
                BbsColor color = colorService.selectById(bbsSku.getColorId());
                bbsSku.setColor(color);
                buyItem.setSku(bbsSku);
                buyItem.setHave(bbsSku.getStock()>=buyNums);
                buyItem.setCount(buyNums);
                BbsProductWithBLOBs product = productService.selectById(bbsSku.getProductId());
                product.setImgUrls(product.getImgUrl().split(","));
                buyItem.setProduct(product);
                buyItems.add(buyItem);
            }
            StringWriter sw = new StringWriter();
            BbsCart bbsCart = new BbsCart();
            bbsCart.setItems(buyItems);
            try {
                objectMapper.writeValue(sw,bbsCart);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Cookie cookie = null;
            try {
                cookie = new Cookie("jdbuycart", URLEncoder.encode(sw.toString(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            cookie.setMaxAge(7*24*60*60);
            response.addCookie(cookie);
        }else {
            //登陆存到redis中  //用hash结构存  键 + map(键，值)
            loginService.addBuyerToRedis(sids,buyNums,username);
        }




        return "redirect:"+url;
    }
    @RequestMapping("toCart")
    public String toCart(HttpServletRequest request,HttpServletResponse response,Model model){

        ObjectMapper objectMapper = new ObjectMapper();
        //null不要序列化了。
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //判断是否登陆，登陆去redis查询 ,没有登陆去cookie中查询。
        String csessionid = RequestUtils.getCSESSIONID(request,response);
        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        String username = null;
        for(Cookie cookie : cookies){
            if(csessionid.equals(cookie.getName())){
                //说明已经登陆。
                flag = true;
                username = cookie.getValue();
                break;
            }
        }
        Float totalmoney = 0F;
        List<BuyItem> buyItems = null;


        if(flag){
            //redis查
            Jedis jedis = jedisPool.getResource();
            Map map = jedis.hgetAll(username);
            Set set = map.keySet();
             buyItems = new ArrayList<>();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()){
                Long sid = Long.parseLong((String)iterator.next());
                BbsSku bbsSku = skuService.selectById(sid);
                BuyItem buyItem = new BuyItem();
                BbsColor color = colorService.selectById(bbsSku.getColorId());
                bbsSku.setColor(color);
                buyItem.setSku(bbsSku);
                BbsProductWithBLOBs product = productService.selectById(bbsSku.getProductId());
                product.setImgUrls(product.getImgUrl().split(","));
                buyItem.setProduct(product);
                buyItem.setCount(Integer.parseInt((String )map.get(String.valueOf(sid))));
                if(map.get(String.valueOf(sid))!=null){
                    buyItem.setHave(bbsSku.getStock()>=Integer.parseInt((String )map.get(String.valueOf(sid))));
                    buyItems.add(buyItem);
                    totalmoney = totalmoney+Integer.parseInt((String )map.get(String.valueOf(sid)))*bbsSku.getPrice();
                }
            }

        }else{
            //cookie查.
            for(Cookie cookie:cookies){
                if("jdbuycart".equals(cookie.getName())){
                    try {
                        String bbs   = URLDecoder.decode(cookie.getValue(),"utf-8");
                         BbsCart   bbsCart =  objectMapper.readValue(bbs,BbsCart.class);
                        if(bbsCart!=null){
                             buyItems = bbsCart.getItems();
                             for(BuyItem buyItem :buyItems){
                               totalmoney = totalmoney+buyItem.getSku().getPrice()*buyItem.getCount();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
            model.addAttribute("carts",buyItems);
            model.addAttribute("totalmoney",totalmoney);

             return "cart";
    }
}
