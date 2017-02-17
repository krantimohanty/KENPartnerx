package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/4/2016.
 */

public class Address_Info_Individual {

    public long id;
    public String Apl_AddressType, Apl_Address_Street1, Apl_Address_Street2,Apl_Address_Landmark, Apl_Address_City, Apl_Address_State, Apl_Address_Country, Apl_Address_ZIP ;
    public Address_Info_Individual()
    {

    }
    public Address_Info_Individual(String Apl_AddressType, String Apl_Address_Street1, String Apl_Address_Street2, String Apl_Address_Landmark, String Apl_Address_City, String Apl_Address_State, String Apl_Address_Country, String Apl_Address_ZIP)
    {
        this.Apl_AddressType=Apl_AddressType;
        this.Apl_Address_Street1=Apl_Address_Street1;
        this.Apl_Address_Street2=Apl_Address_Street2;
        this.Apl_Address_City=Apl_Address_City;
        this.Apl_Address_State=Apl_Address_State;
        this.Apl_Address_Country=Apl_Address_Country;
        this.Apl_Address_ZIP=Apl_Address_ZIP;
        this.Apl_Address_Landmark=Apl_Address_Landmark;
    }

    public String getLandmark() {
        return Apl_Address_Landmark;
    }

    public void setLandmark(String apl_Address_Landmark) {
        Apl_Address_Landmark = apl_Address_Landmark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress1() {
        return Apl_Address_Street1;
    }

    public String getAddressType() {
        return Apl_AddressType;
    }

    public void setAddressType(String headline) {
        this.Apl_AddressType = headline;
    }

    public void setAddress1(String address1) {
        this.Apl_Address_Street1 = address1;
    }


    public String getPin() {
        return Apl_Address_ZIP;
    }

    public void setPin(String pin) {
        this.Apl_Address_ZIP = pin;
    }

    public String getCountry() {
        return Apl_Address_Country;
    }

    public void setCountry(String country) {
        this.Apl_Address_Country = country;
    }

    public String getCity() {
        return Apl_Address_City;
    }

    public void setCity(String city) {
        this.Apl_Address_City = city;
    }

    public String getAddress2() {
        return Apl_Address_Street2;
    }

    public void setAddress2(String address2) {
        this.Apl_Address_Street2 = address2;
    }

    public String getState() {
        return Apl_Address_State;
    }

    public void setState(String state) {
        this.Apl_Address_State = state;
    }
}
