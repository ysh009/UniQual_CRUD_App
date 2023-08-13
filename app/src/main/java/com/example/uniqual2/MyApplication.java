package com.example.uniqual2;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {
    ArrayList<ItemBean> arrItems = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ArrayList<ItemBean> getDataList(){
        return arrItems;
    }

    public void addData(ItemBean itemBean){
        arrItems.add(itemBean);
    }

    public MyApplication getInstance(){
        return this;
    }
}
