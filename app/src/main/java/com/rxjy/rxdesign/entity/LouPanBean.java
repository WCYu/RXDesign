package com.rxjy.rxdesign.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 阿禹 on 2018/8/2.
 */

public class LouPanBean {

    /**
     * buildName : 北京CBD万达广场
     * ID : 10003
     * : 通州万达广场
     */

    private String buildName;
    private int ID;

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
