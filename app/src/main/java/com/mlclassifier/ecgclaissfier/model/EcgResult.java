package com.mlclassifier.ecgclaissfier.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EcgResult implements Serializable {
    private String id;
    private String user_id;
    private String ecg_result;
    private String ecg_image;
    private String ecg_image_type;
    private DateObject created_at;
    private DateObject updated_at;
    private String comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEcg_result() {
        return ecg_result;
    }

    public void setEcg_result(String ecg_result) {
        this.ecg_result = ecg_result;
    }

    public String getEcg_image() {
        return ecg_image;
    }

    public void setEcg_image(String ecg_image) {
        this.ecg_image = ecg_image;
    }

    public String getEcg_image_type() {
        return ecg_image_type;
    }

    public void setEcg_image_type(String ecg_image_type) {
        this.ecg_image_type = ecg_image_type;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public DateObject getCreated_at() {
        return created_at;
    }

    public void setCreated_at(DateObject created_at) {
        this.created_at = created_at;
    }

    public DateObject getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(DateObject updated_at) {
        this.updated_at = updated_at;
    }
}
