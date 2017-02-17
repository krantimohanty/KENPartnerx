package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/24/2016.
 */

public class Phone {
    String phoneType, phoneNo;
    public Phone(){

    }
    public Phone(String phoneNo, String phoneType){
        this.phoneType=phoneType;
        this.phoneNo=phoneNo;

    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
