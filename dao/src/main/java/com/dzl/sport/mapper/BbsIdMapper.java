package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsId;

public interface BbsIdMapper {
    int deleteByPrimaryKey(Long tempBid);

    int insert(BbsId record);


     BbsId selectAll();
     void updatePid();
     void updateBid();
    int insertSelective(BbsId record);

    BbsId selectByPrimaryKey(Long tempBid);

    int updateByPrimaryKeySelective(BbsId record);

    int updateByPrimaryKey(BbsId record);
}