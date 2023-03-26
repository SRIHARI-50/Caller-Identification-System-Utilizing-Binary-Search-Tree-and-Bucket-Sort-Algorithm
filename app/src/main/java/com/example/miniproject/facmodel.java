package com.example.miniproject;

public class facmodel {

    private final String id,name,dept,email,phone,intercom,altno;

    public facmodel(String id, String name, String dept, String email, String phone, String intercom, String altno) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.email = email;
        this.phone = phone;
        this.intercom = intercom;
        this.altno = altno;
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

    public String getIntercom() {
        return intercom;
    }

    public String getAltno() {
        return altno;
    }

    public String getEmail() {
        return email;
    }

}

