package com.dzl.sport.mapper;

import com.dzl.sport.pojo.BbsBrand;

import java.util.List;

public interface BbsBrandMapper {
    int deleteByPrimaryKey(Long id);
    List<BbsBrand> selectAll();
    List<BbsBrand> selectListLimit(BbsBrand bbsBrand);
    int insert(BbsBrand record);

    int insertSelective(BbsBrand record);
    void  deleteBrands(List<Long> ids);
    BbsBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BbsBrand record);

    int updateByPrimaryKey(BbsBrand record);
}