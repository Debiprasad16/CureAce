package com.app.cureace.model;

import java.io.Serializable;

public class AmbulanceBookingModel implements Serializable {

    private String id,
            ambulance_id,ambulance_booking_id,hospital_mobile_no,hospital_name,hospital_address,date_and_time,user_name,user_mobile_no,
            user_email,user_id_proof,user_address,user_id,start_date;

    String ambulance_cost,payment_id,ambulance_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmbulance_id() {
        return ambulance_id;
    }

    public void setAmbulance_id(String ambulance_id) {
        this.ambulance_id = ambulance_id;
    }

    public String getAmbulance_booking_id() {
        return ambulance_booking_id;
    }

    public void setAmbulance_booking_id(String ambulance_booking_id) {
        this.ambulance_booking_id = ambulance_booking_id;
    }

    public String getHospital_mobile_no() {
        return hospital_mobile_no;
    }

    public void setHospital_mobile_no(String hospital_mobile_no) {
        this.hospital_mobile_no = hospital_mobile_no;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getAmbulance_cost() {
        return ambulance_cost;
    }

    public void setAmbulance_cost(String ambulance_cost) {
        this.ambulance_cost = ambulance_cost;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getAmbulance_number() {
        return ambulance_number;
    }

    public void setAmbulance_number(String ambulance_number) {
        this.ambulance_number = ambulance_number;
    }
}
