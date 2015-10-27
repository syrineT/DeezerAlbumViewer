package com.syrine.ws.response;

import com.syrine.ws.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumsResponse {

    private List<Album> data;
    private String next;

    public AlbumsResponse(List<Album> data, String next) {
        this.data = data;
        this.next = next;
    }

    public List<Album> getData() {
        return data != null ? data : new ArrayList<Album>();
    }

    public String getNext() {
        return next;
    }
}
