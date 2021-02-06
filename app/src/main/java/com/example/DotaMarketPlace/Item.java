package com.example.DotaMarketPlace;

import java.io.Serializable;

public class Item implements Serializable {
    private String Iname;
    private String Iprice;
    private String Istock;
    private String lat;
    private String lng;
    public Item(){}

    public Item(String iname, String iprice, String istock, String Lat, String Lng) {
        Iname = iname;
        Iprice = iprice;
        Istock = istock;
        lat = Lat;
        lng = Lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIname() {
        return Iname;
    }

    public void setIname(String iname) {
        Iname = iname;
    }

    public String getIprice() {
        return Iprice;
    }

    public void setIprice(String iprice) {
        Iprice = iprice;
    }

    public String getIstock() {
        return Istock;
    }

    public void setIstock(String istock) {
        Istock = istock;
    }
}
