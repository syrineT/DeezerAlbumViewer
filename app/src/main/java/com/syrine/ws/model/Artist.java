package com.syrine.ws.model;

public class Artist {
    public int id;
    public String name;
    public String picture;
    public String picture_small;
    public String picture_medium;
    public String picture_big;

    public Artist(int _id, String _name, String _picture, String _picture_small, String _picture_medium, String _picture_big) {
        id = _id;
        name = _name;
        picture = _picture;
        picture_small = _picture_small;
        picture_medium = _picture_medium;
        picture_big = _picture_big;
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
