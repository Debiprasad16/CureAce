package com.app.cureace.model;

import java.io.Serializable;

public class MedicineCartModel implements Serializable {

    private String id,
           m_id,m_booking_id,m_image,m_title,m_description,m_mf_date,m_exp_date,date_and_time;

    String m_price,payment_id,m_quantity,m_sub_total;

    public String getM_sub_total() {
        return m_sub_total;
    }

    public void setM_sub_total(String m_sub_total) {
        this.m_sub_total = m_sub_total;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_price() {
        return m_price;
    }

    public void setM_price(String m_price) {
        this.m_price = m_price;
    }

    public String getM_booking_id() {
        return m_booking_id;
    }

    public void setM_booking_id(String m_booking_id) {
        this.m_booking_id = m_booking_id;
    }

    public String getM_image() {
        return m_image;
    }

    public void setM_image(String m_image) {
        this.m_image = m_image;
    }

    public String getM_description() {
        return m_description;
    }

    public void setM_description(String m_description) {
        this.m_description = m_description;
    }

    public String getM_mf_date() {
        return m_mf_date;
    }

    public void setM_mf_date(String m_mf_date) {
        this.m_mf_date = m_mf_date;
    }

    public String getM_exp_date() {
        return m_exp_date;
    }

    public void setM_exp_date(String m_exp_date) {
        this.m_exp_date = m_exp_date;
    }





    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getM_quantity() {
        return m_quantity;
    }

    public void setM_quantity(String m_quantity) {
        this.m_quantity = m_quantity;
    }
}
