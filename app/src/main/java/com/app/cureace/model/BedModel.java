package com.app.cureace.model;

import java.io.Serializable;

public class BedModel implements Serializable {
    private String id,email,hospital_address,mobile_no,hospital_name,bed_cost;
    int available_beds;

    public String getBed_cost() {
        return bed_cost;
    }

    public void setBed_cost(String bed_cost) {
        this.bed_cost = bed_cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
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

    public int getAvailable_beds() {
        return available_beds;
    }

    public void setAvailable_beds(int available_beds) {
        this.available_beds = available_beds;
    }
}
