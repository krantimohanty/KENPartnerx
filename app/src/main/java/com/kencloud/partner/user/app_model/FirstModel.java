package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 12/9/2016.
 */

public class FirstModel {

    public String name, dob, natonality, gender;
    public boolean mStatus;


    public FirstModel(){

    }
    public FirstModel(String name, String dob, String natinality, String gender, boolean mStatus){
        this.name=name;
        this.dob=dob;
        this.natonality=natinality;
        this.gender=gender;
        this.mStatus=mStatus;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNatonality() {
        return natonality;
    }

    public void setNatonality(String natonality) {
        this.natonality = natonality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean getmStatus() {
        return mStatus;
    }

    public void setmStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }
}
