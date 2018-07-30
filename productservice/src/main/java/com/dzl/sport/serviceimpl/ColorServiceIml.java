package com.dzl.sport.serviceimpl;

import com.dzl.sport.color.ColorService;
import com.dzl.sport.mapper.BbsColorMapper;
import com.dzl.sport.pojo.BbsColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("colorService")
public class ColorServiceIml implements ColorService{
    @Autowired
    private BbsColorMapper bbsColorMapper;
     public List<BbsColor> getColors(){
        return bbsColorMapper.selectAll();
    }
}
