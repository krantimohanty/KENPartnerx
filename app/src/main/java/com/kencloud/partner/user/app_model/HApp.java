package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita.p on 11/3/2016.
 */

public class HApp {

    int image;
    String text;

    public HApp() {
    }
    public HApp(int image, String text
    ) {
        this.image = image;
        this.text = text;

    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
