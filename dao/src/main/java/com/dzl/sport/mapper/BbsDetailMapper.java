package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsDetail;

public interface BbsDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BbsDetail record);

    int insertSelective(BbsDetail record);

    BbsDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsDetail record);

    int updateByPrimaryKey(BbsDetail record);
}