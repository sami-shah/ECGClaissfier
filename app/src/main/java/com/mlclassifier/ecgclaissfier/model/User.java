package com.mlclassifier.ecgclaissfier.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String full_name;
    private String email;
    private String password;
    private String phone;
    boolean login;

    public User() {
    }

    public User(String id, String full_name, String email, String password, String phone) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    private String success;

    private String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
