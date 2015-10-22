package com.syrine.ws.response;

import com.syrine.ws.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumsResponse {

    public List<Album> data;
    public String next;

    public AlbumsResponse(List<Album> _data, String _next) {
        data = _data;
        next = _next;
    }

    public List<Album> getData() {
        return data != null ? data : new ArrayList<Album>();
    }

    public String getNext() {
        return next;
    }
}
