package com.example.miniproject;

public class stumodel {

    private final String id;
    private final String name;
    private final String dept;
    private final String phone;
    private final String yr;
    private final String altno;

    private final String email;

    private final String reg;

    public stumodel(String id, String name,String reg, String yr,String dept, String email, String phone, String altno) {
        this.id = id;
        this.name = name;
        this.yr = yr;
        this.dept = dept;
        this.phone = phone;
        this.altno = altno;
        this.reg = reg;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }
    public String getPhone() {
        return phone;
    }

    public String getYr() {
        return yr;
    }

    public String getAltno() {
        return altno;
    }

    public String getReg() {
        return reg;
    }
    public String getEmail() {
        return email;
    }
}

