package com.dzl.sport.pojo;

import java.io.Serializable;
import java.util.List;

public class BbsCart implements Serializable{
  private List<BuyItem>  items ;

    public List<BuyItem> getItems() {
        return items;
    }

    public void setItems(List<BuyItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "BbsCart{" +
                "items=" + items +
                '}';
    }
}
