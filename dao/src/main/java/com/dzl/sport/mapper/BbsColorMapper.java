package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsColor;

public interface BbsColorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BbsColor record);

    int insertSelective(BbsColor record);

    BbsColor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsColor record);

    int updateByPrimaryKey(BbsColor record);
}