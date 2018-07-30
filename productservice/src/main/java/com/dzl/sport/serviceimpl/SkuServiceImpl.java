package com.dzl.sport.serviceimpl;

import com.dzl.sport.mapper.BbsSkuMapper;
import com.dzl.sport.pojo.BbsSku;
import com.dzl.sport.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("skuService")
public class SkuServiceImpl implements SkuService {
    @Autowired
    private BbsSkuMapper bbsSkuMapper;
    @Override
    public List<BbsSku> skuList(Long pid) {
        List<BbsSku> skuList = bbsSkuMapper.selectByPid(pid);

        return skuList;
    }

    @Override
    public void updateSku(String[] strings) {
        if(strings==null){
            return;
        }
        BbsSku bbsSku = new BbsSku();
        bbsSku.setMarketPrice(Float.parseFloat(strings[0]));
        bbsSku.setPrice(Float.parseFloat(strings[1]));
        bbsSku.setStock(Integer.parseInt(strings[2]));
        bbsSku.setUpperLimit(Integer.parseInt(strings[3]));
        bbsSku.setDeliveFee(Float.parseFloat(strings[4]));
        bbsSku.setId(Long.parseLong(strings[5]));
        bbsSkuMapper.updateByPrimaryKeySelective(bbsSku);
    }
}
