package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/30/2016.
 */

public class KYC_id {
    String identity_type, ref_id;

    public  KYC_id(){

    }
    public KYC_id(String identity_type, String ref_id ){
        this.identity_type=identity_type;
        this.ref_id=ref_id;

    }
    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }


}
