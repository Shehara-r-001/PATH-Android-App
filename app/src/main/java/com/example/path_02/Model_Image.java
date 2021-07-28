package com.example.path_02;

public class Model_Image {


    String caption, images;

    public Model_Image(){}

    public Model_Image(String caption, String images) {
        this.caption = caption;
        this.images = images;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
