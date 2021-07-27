package com.example.path_02;

public class Model_Image {

    String image_url, caption;

    public Model_Image(){}

    public Model_Image(String image_url, String caption) {
        this.image_url = image_url;
        this.caption = caption;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
