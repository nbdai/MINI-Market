package com.dzl.sport.cart;

import com.dzl.sport.pojo.BbsCart;
import com.dzl.sport.pojo.BuyItem;
import com.dzl.sport.util.ResponsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    public void  addCart(Long sid, Integer buyNum, ResponsUtils responsUtils);
    //得到购物车
    public List<BuyItem> toCart(ResponsUtils responsUtils);
}
