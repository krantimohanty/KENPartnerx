package com.kencloud.partner.user.app_model;

/**
 * Created by suchismita on 1/30/2017.
 */

public class NewsModel {
    String Title;
    int Image;
int Text;
    public NewsModel(String title, int image, int text)
    {
        this.Title=title;
        this.Image=image;
        this.Text=text;
    }

    public int getText() {
        return Text;
    }

    public void setText(int text) {
        Text = text;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }



}
