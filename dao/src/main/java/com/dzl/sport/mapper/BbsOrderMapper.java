package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsOrder;

public interface BbsOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BbsOrder record);

    int insertSelective(BbsOrder record);

    BbsOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsOrder record);

    int updateByPrimaryKey(BbsOrder record);
}