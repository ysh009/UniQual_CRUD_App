package com.example.uniqual2;

import com.example.uniqual2.ItemBean;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemResponse implements Serializable {
    ArrayList<ItemBean> itemBeans;

    public ArrayList<ItemBean> getItemBeans() {
        return itemBeans;
    }

    public void setItemBeans(ArrayList<ItemBean> itemBeans) {
        this.itemBeans = itemBeans;
    }
}
