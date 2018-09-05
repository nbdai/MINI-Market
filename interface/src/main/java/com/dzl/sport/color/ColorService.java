package com.dzl.sport.color;

import com.dzl.sport.pojo.BbsColor;

import java.util.List;

public interface ColorService {
    List<BbsColor> getColors();
    BbsColor selectById(Long cid);
}
