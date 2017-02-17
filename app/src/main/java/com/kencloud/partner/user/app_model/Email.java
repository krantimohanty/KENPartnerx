package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/24/2016.
 */

public class Email {
    String emailType, emailid;

    public  Email(){

    }
    public Email(String emailid, String emailType ){
        this.emailType=emailType;
        this.emailid=emailid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }
}
