package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsProduct;
import com.dzl.sport.pojo.BbsProductWithBLOBs;

import java.util.List;

public interface BbsProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BbsProductWithBLOBs record);
    void  updateProduct0(List<Long> lids);
    void  updateProduct1(List<Long> lids);
    int insertSelective(BbsProductWithBLOBs record);
    List<BbsProductWithBLOBs> selectLimit(BbsProduct record);
    BbsProductWithBLOBs selectByPrimaryKey(Long id);
    void deleteProducts(List<Long> longList);
    int updateByPrimaryKeySelective(BbsProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BbsProductWithBLOBs record);

    int updateByPrimaryKey(BbsProduct record);
}