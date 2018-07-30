package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsColor;

import java.util.List;


public interface BbsColorMapper {
    int deleteByPrimaryKey(Long id);
    int insert(BbsColor record);
    int insertSelective(BbsColor record);
    BbsColor selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(BbsColor record);
    List<BbsColor> selectAll();
    int updateByPrimaryKey(BbsColor record);
}