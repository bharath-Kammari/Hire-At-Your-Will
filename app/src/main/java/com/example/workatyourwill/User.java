package com.example.workatyourwill;

import com.google.firebase.firestore.GeoPoint;

public class User {
    String user_id;
    String user_name;

    String Ph_no;
    GeoPoint lat_lng;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public User(String user_id, String user_name, String ph_no, GeoPoint lat_lng) {
        this.user_id=user_id;
        this.user_name = user_name;
        Ph_no = ph_no;
        this.lat_lng = lat_lng;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPh_no() {
        return Ph_no;
    }

    public void setPh_no(String ph_no) {
        Ph_no = ph_no;
    }

    public GeoPoint getLat_lng() {
        return lat_lng;
    }

    public void setLat_lng(GeoPoint lat_lng) {
        this.lat_lng = lat_lng;
    }
}
