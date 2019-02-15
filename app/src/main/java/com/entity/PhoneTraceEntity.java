package com.entity;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class PhoneTraceEntity {

    /**
     * com : string
     * company : string
     * list : [{"datetime":"string","remark":"string","zone":"string"}]
     * no : string
     * status : string
     */

    private String com;
    private String company;
    private String no;
    private String status;
    private List<ListBean> list;

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * datetime : string
         * remark : string
         * zone : string
         */

        private String datetime;
        private String remark;
        private String zone;

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
