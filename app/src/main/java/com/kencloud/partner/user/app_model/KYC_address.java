package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/30/2016.
 */

public class KYC_address {
    String addresType, ref_address;

    public  KYC_address(){

    }
    public KYC_address(String addresType, String ref_address ){
        this.addresType=addresType;
        this.ref_address=ref_address;
    }

    public String getAddresType() {
        return addresType;
    }

    public void setAddresType(String addresType) {
        this.addresType = addresType;
    }

    public String getRef_address() {
        return ref_address;
    }

    public void setRef_address(String ref_address) {
        this.ref_address = ref_address;
    }
}
