package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsBuyer;

public interface BbsBuyerMapper {
    int deleteByPrimaryKey(Long id);
    BbsBuyer selectByName(String username);
    int insert(BbsBuyer record);

    int insertSelective(BbsBuyer record);

    BbsBuyer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsBuyer record);

    int updateByPrimaryKey(BbsBuyer record);
}