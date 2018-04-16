package com.example.entity;

import javax.ws.rs.FormParam;

public class UserInfo {

    private Integer id;

    @FormParam("power")
    private String power;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
