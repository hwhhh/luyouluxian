package com.flag.myapplication.car.bean;

import java.util.List;

public class DataEntity {

    private String title;//一级列表内容
    private List<Jingdian> childrenDataList;


    public DataEntity(String title, List<Jingdian> childrenDataList) {
        this.title = title;
        this.childrenDataList = childrenDataList;
    }

    public List<Jingdian> getChildrenDataList() {
        return childrenDataList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChildrenDataList(List<Jingdian> childrenDataList) {
        this.childrenDataList = childrenDataList;
    }


}
