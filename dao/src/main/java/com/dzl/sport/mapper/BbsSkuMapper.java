package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsSku;

public interface BbsSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BbsSku record);

    int insertSelective(BbsSku record);

    BbsSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsSku record);

    int updateByPrimaryKey(BbsSku record);
}