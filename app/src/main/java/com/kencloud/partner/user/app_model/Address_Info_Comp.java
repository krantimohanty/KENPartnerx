package com.kencloud.partner.user.app_model;

public class Address_Info_Comp {
    public long id;
    public String Apl_AddressType, Apl_Address_Street1, Apl_Address_Street2,Apl_Address_Landmark, Apl_Address_City,
            Apl_Address_State, Apl_Address_Country, Apl_Address_ZIP,
            appl_company_name, appl_contact_desg, appl_company_emailid, appl_company_mobile ;
    public Address_Info_Comp()
    {

    }
    public Address_Info_Comp(String Apl_AddressType, String Apl_Address_Street1, String Apl_Address_Street2, String Apl_Address_Landmark,
                             String Apl_Address_City, String Apl_Address_State, String Apl_Address_Country, String Apl_Address_ZIP,
                             String Appl_company_name, String Appl_company_desg,String Appl_company_emailid, String Appl_company_mobile )
    {
        this.Apl_AddressType=Apl_AddressType;
        this.Apl_Address_Street1=Apl_Address_Street1;
        this.Apl_Address_Street2=Apl_Address_Street2;
        this.Apl_Address_City=Apl_Address_City;
        this.Apl_Address_State=Apl_Address_State;
        this.Apl_Address_Country=Apl_Address_Country;
        this.Apl_Address_ZIP=Apl_Address_ZIP;
        this.Apl_Address_Landmark=Apl_Address_Landmark;
        this.appl_company_name=Appl_company_name;
        this.appl_contact_desg=Appl_company_desg;
        this.appl_company_emailid=Appl_company_emailid;
        this.appl_company_mobile=Appl_company_mobile;
    }

//    public String getLandmark() {
//        return Apl_Address_Landmark;
//    }
//
//    public void setLandmark(String apl_Address_Landmark) {
//        Apl_Address_Landmark = apl_Address_Landmark;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getAddress1() {
//        return Apl_Address_Street1;
//    }
//
//    public String getAddressType() {
//        return Apl_AddressType;
//    }
//
//    public void setAddressType(String headline) {
//        this.Apl_AddressType = headline;
//    }
//
//    public void setAddress1(String address1) {
//        this.Apl_Address_Street1 = address1;
//    }
//
//
//    public String getPin() {
//        return Apl_Address_ZIP;
//    }
//
//    public void setPin(String pin) {
//        this.Apl_Address_ZIP = pin;
//    }
//
//    public String getCountry() {
//        return Apl_Address_Country;
//    }
//
//    public void setCountry(String country) {
//        this.Apl_Address_Country = country;
//    }
//
//    public String getCity() {
//        return Apl_Address_City;
//    }
//
//    public void setCity(String city) {
//        this.Apl_Address_City = city;
//    }
//
//    public String getAddress2() {
//        return Apl_Address_Street2;
//    }
//
//    public void setAddress2(String address2) {
//        this.Apl_Address_Street2 = address2;
//    }
//
//    public String getState() {
//        return Apl_Address_State;
//    }
//
//    public void setState(String state) {
//        this.Apl_Address_State = state;
//    }

    public String getAppl_company_name() {
        return appl_company_name;
    }

    public void setAppl_company_name(String appl_company_name) {
        this.appl_company_name = appl_company_name;
    }

    public String getAppl_contact_desg() {
        return appl_contact_desg;
    }

    public void setAppl_contact_desg(String appl_contact_desg) {
        this.appl_contact_desg = appl_contact_desg;
    }

    public String getAppl_company_emailid() {
        return appl_company_emailid;
    }

    public void setAppl_company_emailid(String appl_company_emailid) {
        this.appl_company_emailid = appl_company_emailid;
    }

    public String getAppl_company_mobile() {
        return appl_company_mobile;
    }

    public void setAppl_company_mobile(String appl_company_mobile) {
        this.appl_company_mobile = appl_company_mobile;
    }

    public String getApl_AddressType() {
        return Apl_AddressType;
    }

    public void setApl_AddressType(String apl_AddressType) {
        Apl_AddressType = apl_AddressType;
    }

    public String getApl_Address_ZIP() {
        return Apl_Address_ZIP;
    }

    public void setApl_Address_ZIP(String apl_Address_ZIP) {
        Apl_Address_ZIP = apl_Address_ZIP;
    }

    public String getApl_Address_Country() {
        return Apl_Address_Country;
    }

    public void setApl_Address_Country(String apl_Address_Country) {
        Apl_Address_Country = apl_Address_Country;
    }

    public String getApl_Address_State() {
        return Apl_Address_State;
    }

    public void setApl_Address_State(String apl_Address_State) {
        Apl_Address_State = apl_Address_State;
    }

    public String getApl_Address_City() {
        return Apl_Address_City;
    }

    public void setApl_Address_City(String apl_Address_City) {
        Apl_Address_City = apl_Address_City;
    }

    public String getApl_Address_Landmark() {
        return Apl_Address_Landmark;
    }

    public void setApl_Address_Landmark(String apl_Address_Landmark) {
        Apl_Address_Landmark = apl_Address_Landmark;
    }

    public String getApl_Address_Street1() {
        return Apl_Address_Street1;
    }

    public void setApl_Address_Street1(String apl_Address_Street1) {
        Apl_Address_Street1 = apl_Address_Street1;
    }

    public String getApl_Address_Street2() {
        return Apl_Address_Street2;
    }

    public void setApl_Address_Street2(String apl_Address_Street2) {
        Apl_Address_Street2 = apl_Address_Street2;
    }
}
