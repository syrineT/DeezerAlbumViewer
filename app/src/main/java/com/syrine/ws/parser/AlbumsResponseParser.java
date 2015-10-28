package com.syrine.ws.parser;

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

public class AlbumsResponseParser {

    private static final String TAG = AlbumsResponseParser.class.getSimpleName();

    public static AlbumsResponse readAlbumsResponse(InputStream inputStream) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            List<Album> albums = null;
            String next = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "data":
                        albums = readAlbums(reader);
                        break;
                    case "next":
                        next = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            return new AlbumsResponse(albums, next);

        } catch (IOException e) {
            Log.e(TAG,"Exception has occurred "+ e.getMessage());
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
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "title":
                    title = reader.nextString();
                    break;
                case "cover":
                    cover = reader.nextString();
                    break;
                case "cover_small":
                    coverSmall = reader.nextString();
                    break;
                case "cover_medium":
                    coverMedium = reader.nextString();
                    break;
                case "cover_big":
                    coverBig = reader.nextString();
                    break;
                case "artist":
                    artist = readArtist(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
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
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "name":
                    title = reader.nextString();
                    break;
                case "picture":
                    picture = reader.nextString();
                    break;
                case "picture_small":
                    pictureSmall = reader.nextString();
                    break;
                case "picture_medium":
                    pictureMedium = reader.nextString();
                    break;
                case "picture_big":
                    pictureBig = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Artist(id, title, picture, pictureSmall, pictureMedium, pictureBig);
    }
}
