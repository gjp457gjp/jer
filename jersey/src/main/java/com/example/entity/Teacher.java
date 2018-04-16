package com.example.entity;

import javax.ws.rs.FormParam;

public class Teacher {

    private Integer id;

    @FormParam("tno")
    private Integer tno;

    @FormParam("tname")
    private String tname;

    @FormParam("phone")
    private Long phone;

    @FormParam("qqnum")
    private Long qqnum;

    @FormParam("pids")
    private String pids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTno() {
        return tno;
    }

    public void setTno(Integer tno) {
        this.tno = tno;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getQqnum() {
        return qqnum;
    }

    public void setQqnum(Long qqnum) {
        this.qqnum = qqnum;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }
}
