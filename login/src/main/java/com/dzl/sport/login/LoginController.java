package com.dzl.sport.login;

import com.dzl.sport.pojo.BbsBuyer;
import com.dzl.sport.pojo.BbsCart;
import com.dzl.sport.pojo.BuyItem;
import com.dzl.sport.util.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.List;

/**
 * @author DZL
 * @desc 登陆
 */

@Controller
public class LoginController {
    @Resource
    private LoginService loginService;
    @RequestMapping("toLogin.aspx")
    public String toLogin(String ReturnUrl, Model model){
        model.addAttribute("returnUrl",ReturnUrl);
        return "login";
    }
    @RequestMapping("login.aspx")
    public String login(BbsBuyer bbsBuyer, Model model, HttpServletRequest request, HttpServletResponse response){
      /* 登陆要做的是： md5 hex加密， 加盐。
       数据库取出比较，将username 存到cookie,然后将cookie购物车添加到redis中.
       返回源页.*/
        boolean flag = false;//记录是否登陆
        if(bbsBuyer.getPassword()!=null){
            //加密
            String password = bbsBuyer.getPassword();
            String newPassword = "";
            try {
                //MD5
                MessageDigest instance = MessageDigest.getInstance("MD5");
                byte[] digest = instance.digest(password.getBytes());
                //HEX
                char[] encodeHex = Hex.encodeHex(digest);
                newPassword = new String(encodeHex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BbsBuyer buyer =  loginService.selectByName(bbsBuyer.getUsername());
            //比较password
            if(buyer!=null){
                if(newPassword.equals(buyer.getPassword())){
                    flag = true;
                }
            }
        }
        if(!flag){
            //没有登陆成功
            model.addAttribute("error","用户名密码不匹配!!!");
            model.addAttribute("returnUrl",bbsBuyer.getReturnUrl());
            return "login";
        }else{
            //将username 存到cookie,然后将cookie购物车添加到redis中.
            //cookie购物车添加到redis中.
            Cookie[] cookies = request.getCookies();
            ObjectMapper objectMapper = new ObjectMapper();

            BbsCart bbsCart = null;
            for (Cookie cookie:cookies){

              //先看看cookie中有没有购物车
                if("jdbuycart".equals(cookie.getName())){
                    try {
                        bbsCart = objectMapper.readValue(URLDecoder.decode(cookie.getValue()),BbsCart.class);
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(bbsCart!=null){
                //有购物车则存到redis中.
                List<BuyItem> buyItems = bbsCart.getItems();
                for(BuyItem buyItem:buyItems){
                    loginService.addBuyerToRedis(buyItem.getSku().getId(),buyItem.getCount(),bbsBuyer.getUsername());
                }
            }
            //将username 存到cookie
            String csessionid = RequestUtils.getCSESSIONID(request,response);
            Cookie cookie = new Cookie(csessionid,bbsBuyer.getUsername());
            response.addCookie(cookie);
            return "redirect:"+bbsBuyer.getReturnUrl();
        }
    }
}
