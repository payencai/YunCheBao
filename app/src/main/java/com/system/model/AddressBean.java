package com.system.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/2/22 18:22
 * 邮箱：771548229@qq..com
 */
public class AddressBean implements Serializable{
    @Override
    public String toString() {
        return "AddressBean{" +
                "module='" + module + '\'' +
                ", latlng=" + latlng +
                ", poiaddress='" + poiaddress + '\'' +
                ", poiname='" + poiname + '\'' +
                ", cityname='" + cityname + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                '}';
    }

    /**
     * module : locationPicker
     * latlng : {"lat":22.937086,"lng":113.388167}
     * poiaddress : 广东省广州市番禺区亚运大道1号
     * poiname : 永旺梦乐城(番禺广场店)
     * cityname : 广州市
     */

    private String module;
    private LatlngBean latlng;
    private String poiaddress;
    private String poiname;
    private String cityname;
    private String province;
    private String district;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public LatlngBean getLatlng() {
        return latlng;
    }

    public void setLatlng(LatlngBean latlng) {
        this.latlng = latlng;
    }

    public String getPoiaddress() {
        return poiaddress;
    }

    public void setPoiaddress(String poiaddress) {
        this.poiaddress = poiaddress;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public static class LatlngBean implements Serializable{
        /**
         * lat : 22.937086
         * lng : 113.388167
         */

        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
