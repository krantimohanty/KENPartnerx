package com.kencloud.partner.user.GPS;

/**
 * Created by mantosh on 11/17/2015.
 */
public class Mapmarker {

    private String mLabel1;
    private String mLabel2;
    private String mLabel3;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;
    private String time;
    private String mpic;

    public Mapmarker(String mLabel1, String mLabel2, String mpic, String mLabel3, String mIcon, Double mLatitude, Double mLongitude, String time) {
        this.mLabel1 = mLabel1;
        this.mLabel2 = mLabel2;
        this.mpic=mpic;
        this.mLabel3 = mLabel3;
        this.mIcon = mIcon;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.time = time;
    }

    public String getMpic() {
        return mpic;
    }

    public void setMpic(String mpic) {
        this.mpic = mpic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getmLabel1() {
        return mLabel1;
    }

    public void setmLabel1(String mLabel1) {
        this.mLabel1 = mLabel1;
    }

    public String getmLabel2() {
        return mLabel2;
    }

    public void setmLabel2(String mLabel2) {
        this.mLabel2 = mLabel2;
    }

    public String getmLabel3() {
        return mLabel3;
    }

    public void setmLabel3(String mLabel3) {
        this.mLabel3 = mLabel3;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public Double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }
}
