package com.dzl.sport.login;

import com.dzl.sport.pojo.BbsBuyer;

public interface LoginService {
    public BbsBuyer selectByName(String userName);
    public void addBuyerToRedis(Long sid,Integer count,String username);


}
