package com.syrine.ws.model;

public class Album {
    private int id;
    private String title;
    private String cover;
    private String cover_small;
    private String cover_medium;
    private String cover_big;
    private Artist artist;

    public Album(int _id, String _title, String _cover, String _cover_small, String _cover_medium, String _cover_big, Artist _artist) {
        id = _id;
        title = _title;
        cover = _cover;
        cover_small = _cover_small;
        cover_medium = _cover_medium;
        cover_big = _cover_big;
        artist = _artist;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getCoverSmall() {
        return cover_small;
    }

    public String getCoverMedium() {
        return cover_medium;
    }

    public String getCoverBig() {
        return cover_big;
    }

    public Artist getArtist() {
        return artist;
    }
}
