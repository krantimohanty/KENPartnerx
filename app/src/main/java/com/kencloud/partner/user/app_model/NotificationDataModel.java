package com.kencloud.partner.user.app_model;

/**
 * Created by Kranti on 18/3/2016.
 */
public class NotificationDataModel {
    String announcement_id;
    String date;
    String description;
    String totalRecords;
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }



    public String getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(String announcement_id) {
        this.announcement_id = announcement_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
