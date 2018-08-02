package com.rxjy.rxdesign.entity;

import java.util.List;

/**
 * Created by 阿禹 on 2018/8/1.
 */

public class ShangQuanBean {

    private int StatusCode;
    private List<BodyBean> Body;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public List<BodyBean> getBody() {
        return Body;
    }

    public void setBody(List<BodyBean> Body) {
        this.Body = Body;
    }

    public static class BodyBean {
        /**
         * ID : 1
         * City : 1
         * County : 2
         * Name : CBD
         */

        private int ID;
        private int City;
        private int County;
        private String Name;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getCity() {
            return City;
        }

        public void setCity(int City) {
            this.City = City;
        }

        public int getCounty() {
            return County;
        }

        public void setCounty(int County) {
            this.County = County;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
