package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsId;

public interface BbsIdMapper {
    int deleteByPrimaryKey(Long tempBid);
    Long selectId();
    void updateId(Long id);
    int insert(BbsId record);

    int insertSelective(BbsId record);
}