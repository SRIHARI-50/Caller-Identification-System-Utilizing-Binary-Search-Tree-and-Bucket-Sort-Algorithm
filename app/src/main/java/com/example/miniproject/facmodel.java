package com.example.miniproject;

public class facmodel {

    private final String id,name,dept,phone,intercom,altno;

    public facmodel(String id, String name,String dept, String phone, String intercom, String altno) {
        this.id = id;
        this.name = name;
        this.dept = dept;
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

}

