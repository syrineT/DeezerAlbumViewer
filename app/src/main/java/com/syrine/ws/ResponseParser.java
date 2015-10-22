package com.syrine.ws;

import android.util.JsonReader;
import android.util.Log;

import com.syrine.ws.model.Album;
import com.syrine.ws.model.Artist;
import com.syrine.ws.response.AlbumsResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResponseParser {

    private static String TAG = ResponseParser.class.getSimpleName();

    public static AlbumsResponse readAlbumsResponse(InputStream inputStream) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            List<Album> albums = null;
            String next = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("data")) {
                    albums = readAlbums(reader);
                } else if (name.equals("next")) {
                    next = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            return new AlbumsResponse(albums, next);

        } catch (IOException e) {
            Log.e(TAG,"Exception has occured "+ e.getMessage());
        }
        return null;
    }

    private static List<Album> readAlbums(JsonReader reader) throws IOException {
        List<Album> albums = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            albums.add(readAlbum(reader));
        }
        reader.endArray();
        return albums;
    }

    private static Album readAlbum(JsonReader reader) throws IOException {
        int id = -1;
        String title = null;
        String cover = null;
        String coverSmall = null;
        String coverMedium = null;
        String coverBig = null;
        Artist artist = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("cover")) {
                cover = reader.nextString();

            } else if (name.equals("cover_small")) {
                coverSmall = reader.nextString();

            } else if (name.equals("cover_medium")) {
                coverMedium = reader.nextString();

            } else if (name.equals("cover_big")) {
                coverBig = reader.nextString();
            } else if (name.equals("artist")) {
                artist = readArtist(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Album(id, title, cover, coverSmall, coverMedium, coverBig, artist);
    }

    private static Artist readArtist(JsonReader reader) throws IOException {
        int id = -1;
        String title = null;
        String picture = null;
        String pictureSmall = null;
        String pictureMedium = null;
        String pictureBig = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("name")) {
                title = reader.nextString();
            } else if (name.equals("picture")) {
                picture = reader.nextString();

            } else if (name.equals("picture_small")) {
                pictureSmall = reader.nextString();

            } else if (name.equals("picture_medium")) {
                pictureMedium = reader.nextString();

            } else if (name.equals("picture_big")) {
                pictureBig = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Artist(id, title, picture, pictureSmall, pictureMedium, pictureBig);
    }
}
