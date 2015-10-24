package com.syrine.ws.model;

public class Artist {
    public int id;
    public String name;
    public String picture;
    public String picture_small;
    public String picture_medium;
    public String picture_big;

    public Artist(int id, String name, String picture, String picture_small, String picture_medium, String picture_big) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.picture_small = picture_small;
        this.picture_medium = picture_medium;
        this.picture_big = picture_big;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getPictureSmall() {
        return picture_small;
    }

    public String getPictureMedium() {
        return picture_medium;
    }

    public String getPictureBig() {
        return picture_big;
    }
}
