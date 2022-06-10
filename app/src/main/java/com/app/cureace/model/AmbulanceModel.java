package com.app.cureace.model;

import java.io.Serializable;

public class AmbulanceModel implements Serializable {
    private String id,address,mobile_no,hospital_name,cost,ambulance_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAmbulance_number() {
        return ambulance_number;
    }

    public void setAmbulance_number(String ambulance_number) {
        this.ambulance_number = ambulance_number;
    }
}
