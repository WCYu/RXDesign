package com.rxjy.rxdesign.entity;

import java.util.List;

/**
 * Created by 阿禹 on 2018/7/31.
 */

public class BGAddressBean {

    /**
     * StatusCode : 1
     * Body : [{"Id":1,"Name":"北京","ParentId":0},{"Id":19,"Name":"南京","ParentId":0},{"Id":23,"Name":"上海","ParentId":0},{"Id":40,"Name":"天津","ParentId":0},{"Id":67,"Name":"重庆","ParentId":0},{"Id":105,"Name":"成都","ParentId":0},{"Id":131,"Name":"武汉","ParentId":0},{"Id":150,"Name":"杭州","ParentId":0},{"Id":159,"Name":"宁波","ParentId":0},{"Id":160,"Name":"合肥","ParentId":0},{"Id":170,"Name":"郑州","ParentId":0},{"Id":171,"Name":"济南","ParentId":0},{"Id":187,"Name":"西安","ParentId":0},{"Id":198,"Name":"石家庄","ParentId":0},{"Id":265,"Name":"唐山","ParentId":0}]
     */

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
         * Id : 1
         * Name : 北京
         * ParentId : 0
         */

        private int Id;
        private String Name;
        private int ParentId;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }
    }
}
