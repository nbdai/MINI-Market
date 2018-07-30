package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsSku;

import java.util.List;

public interface BbsSkuMapper {
    int deleteByPrimaryKey(Long id);
    List<BbsSku> selectByPid(Long pid);
    int insert(BbsSku record);

    int insertSelective(BbsSku record);

    BbsSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsSku record);

    int updateByPrimaryKey(BbsSku record);
}