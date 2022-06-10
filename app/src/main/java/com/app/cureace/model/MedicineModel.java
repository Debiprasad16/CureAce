package com.app.cureace.model;

import java.io.Serializable;

public class MedicineModel implements Serializable {

   private String m_id,m_title,m_price,m_description,m_mf_date,m_exp_date,m_image;

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public String getM_price() {
        return m_price;
    }

    public void setM_price(String m_price) {
        this.m_price = m_price;
    }

    public String getM_description() {
        return m_description;
    }

    public void setM_description(String m_description) {
        this.m_description = m_description;
    }

    public String getM_exp_date() {
        return m_exp_date;
    }

    public void setM_exp_date(String m_exp_date) {
        this.m_exp_date = m_exp_date;
    }

    public String getM_image() {
        return m_image;
    }

    public void setM_image(String m_image) {
        this.m_image = m_image;
    }

    public String getM_mf_date() {
        return m_mf_date;
    }

    public void setM_mf_date(String m_mf_date) {
        this.m_mf_date = m_mf_date;
    }
}
