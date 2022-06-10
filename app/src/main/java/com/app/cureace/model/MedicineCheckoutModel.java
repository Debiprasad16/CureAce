package com.app.cureace.model;

import java.io.Serializable;
import java.util.List;

public class MedicineCheckoutModel implements Serializable {

    private String id,booking_id,user_name,user_mobile_no,
            user_email,user_id_proof,user_address,user_id, m_total_price,payment_id,date_time;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
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

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    private List<MedicineCartModel> cartModelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_total_price() {
        return m_total_price;
    }

    public void setM_total_price(String m_total_price) {
        this.m_total_price = m_total_price;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public List<MedicineCartModel> getCartModelList() {
        return cartModelList;
    }

    public void setCartModelList(List<MedicineCartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }
}
