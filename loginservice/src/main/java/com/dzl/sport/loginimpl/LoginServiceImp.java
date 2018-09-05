package com.dzl.sport.loginimpl;

import com.dzl.sport.login.LoginService;
import com.dzl.sport.mapper.BbsBuyerMapper;
import com.dzl.sport.pojo.BbsBuyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service("loginService")
public class LoginServiceImp implements LoginService {
    @Autowired
    private BbsBuyerMapper bbsBuyerMapper;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public BbsBuyer selectByName(String userName) {
        return bbsBuyerMapper.selectByName(userName);
    }
    @Override
    public void addBuyerToRedis(Long sid, Integer count, String username) {
        //登陆存到redis中  //用hash结构存  键 + map(键，值)
        Jedis jedis = jedisPool.getResource();
        //map  : sid ,count
        Map map = jedis.hgetAll(username);
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        //判断有没有商品以及购物车.
        boolean btemp = false;
        while (iterator.hasNext()){
            Long tempSid = Long.valueOf((String)iterator.next());
            if(tempSid==sid){
                //这个时候说明有此商品。
                btemp = true;
                break;
            }
        }
        //有此商品的记录，改变数量即可，
        if(btemp){
            jedis.hincrBy(username,String .valueOf(sid),count);
        }
        //没有商品，就得新建一个.
        else{

            jedis.hset(username,String.valueOf(sid),String.valueOf(count));
        }
        jedis.close();
    }
}
