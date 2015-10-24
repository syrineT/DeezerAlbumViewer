package com.syrine.ws.model;

public class Album {
    private int id;
    private String title;
    private String cover;
    private String cover_small;
    private String cover_medium;
    private String cover_big;
    private Artist artist;

    public Album(int id, String title, String cover, String cover_small, String cover_medium, String cover_big, Artist artist) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.cover_small = cover_small;
        this.cover_medium = cover_medium;
        this.cover_big = cover_big;
        this.artist = artist;
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
