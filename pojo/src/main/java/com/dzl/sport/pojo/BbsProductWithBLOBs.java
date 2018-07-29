package com.dzl.sport.pojo;

import java.io.Serializable;

public class BbsProductWithBLOBs extends BbsProduct implements Serializable{
    private String imgUrl;

   public String[] imgUrls;

    private String description;

    private String packageList;

    public String[] getImgUrls() {
        return imgUrls;
    }

    public  void setImgUrls(String[] imgUrls) {
        this.imgUrls = imgUrls;
    }
   /* private  String[] getMyImgUrls(){
        String[] imgs = imgUrl.split(",");
        return imgs;
    }*/

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList == null ? null : packageList.trim();
    }
}