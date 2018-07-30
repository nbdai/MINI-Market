package com.dzl.sport.sku;

import com.dzl.sport.pojo.BbsSku;

import java.util.List;

public interface SkuService {
   List<BbsSku> skuList(Long pid);
   void  updateSku(String[] strings);
}
