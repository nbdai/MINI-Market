package com.dzl.sport.cartimpl;

import com.dzl.sport.cart.CartService;
import com.dzl.sport.pojo.BbsCart;
import com.dzl.sport.pojo.BbsSku;
import com.dzl.sport.pojo.BuyItem;
import com.dzl.sport.sku.SkuService;
import com.dzl.sport.util.RequestUtils;
import com.dzl.sport.util.ResponsUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

/**
 * @author DZL
 * @desc 购物车业务实现类
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Override
    public void addCart(Long sid, Integer buyNum, ResponsUtils responsUtils) {

    }

    @Override
    public List<BuyItem> toCart(ResponsUtils responsUtils) {

           return null;
    }
}
