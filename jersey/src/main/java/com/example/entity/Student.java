package com.example.entity;

import javax.ws.rs.FormParam;

public class Student {

    private Integer id;

    @FormParam("sno")
    private Long sno;

    @FormParam("sname")
    private String sname;

    @FormParam("age")
    private Integer age;

    @FormParam("sex")
    private Integer sex;

    @FormParam("major")
    private String major;

    @FormParam("qqnum")
    private Integer qqnum;

    @FormParam("phone")
    private Long phone;

    @FormParam("pid")
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSno() {
        return sno;
    }

    public void setSno(Long sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getQqnum() {
        return qqnum;
    }

    public void setQqnum(Integer qqnum) {
        this.qqnum = qqnum;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
