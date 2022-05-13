package com.app.cureace.model;

import java.io.Serializable;

public class BedBookingModel implements Serializable {

    private String id,
            bed_id,bed_booking_id,hospital_mobile_no,hospital_email,hospital_name,hospital_address,date_and_time,user_name,user_mobile_no,
            user_email,user_id_proof,user_address,user_id,no_of_days,start_date;

    String bed_cost,payment_id;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getBed_id() {
        return bed_id;
    }

    public String getHospital_mobile_no() {
        return hospital_mobile_no;
    }

    public void setHospital_mobile_no(String hospital_mobile_no) {
        this.hospital_mobile_no = hospital_mobile_no;
    }

    public String getHospital_email() {
        return hospital_email;
    }

    public void setHospital_email(String hospital_email) {
        this.hospital_email = hospital_email;
    }

    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBed_booking_id() {
        return bed_booking_id;
    }

    public void setBed_booking_id(String bed_booking_id) {
        this.bed_booking_id = bed_booking_id;
    }






    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile_no() {
        return user_mobile_no;
    }

    public void setUser_mobile_no(String user_mobile_no) {
        this.user_mobile_no = user_mobile_no;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_id_proof() {
        return user_id_proof;
    }

    public void setUser_id_proof(String user_id_proof) {
        this.user_id_proof = user_id_proof;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getBed_cost() {
        return bed_cost;
    }

    public void setBed_cost(String bed_cost) {
        this.bed_cost = bed_cost;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
}
