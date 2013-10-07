package com.pierr.rockyouth;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierr on 13-9-14.
 *
 * POD data for Album
 */
public class Album implements Parcelable {


    public String title;
    public String author;
    public String uri;
    public int rating;
    public String bigCoverUri;
    public List<Song> songs = new ArrayList<Song>();


    private void init(String author, String title, String uri,String bigCoverUri,
                      int rating,
                      List<Song> songs) {
        this.title = title;
        this.author = author;
        this.uri = uri;
        this.bigCoverUri = bigCoverUri;
        this.rating = rating;
        if (songs != null) {
            this.songs = new ArrayList<Song>(songs);
        }
    }

    private Album(String author, String title, String uri, String imageBig,int rating) {
       init(author,title,uri, imageBig,rating, null);
    }


    public void setSongs(List<Song>songs){

        this.songs = new ArrayList<Song>(songs);
    }

    /**
     * {
     "post": {
     "content": "来自VC的礼物

     http://music.douban.com/subject/1420186/",
     "created_at": "2012-07-26T03:42:14Z",
     "happen_at": "2005-01-01T00:00:00Z",
     "html_content": "<p>来自VC的礼物</p>

     <p>http://music.douban.com/subject/1420186/</p>
     ",
     "id": 891,
     "image_big": "http://img5.douban.com/lpic/s1432989.jpg",
     "image_mid": "http://img5.douban.com/lpic/s1432989.jpg",
     "image_small": "http://img5.douban.com/spic/s1432989.jpg",
     "link_mobile": "http://m.douban.com/music/subject/1420186/",
     "name": "happy_robot",
     "singer": "果味VC",
     "title": "来自VC的礼物",
     "updated_at": "2012-07-26T03:42:14Z",
     "user_id": null,
     "vote_points": 0
     }
     },

     *
     */

    public static Album createFromJson(JSONObject jo)  {
        Album album = null;

        try {
            String imageUri = jo.getString("image_small");
            String imageBig = jo.getString("image_big");
            String title = jo.getString("title");
            String author = jo.getString("singer");
            int rating = jo.getInt("vote_points");

            // TODO:fixme

            album =  new Album(author,title,imageUri,imageBig,rating);

        } catch (JSONException ex) {
            Log.w(MainActivity.TAG, "malformed JSON album object");

        }

        return album;

    }

    // implement parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(title);
        out.writeString(author);
        out.writeString(uri);
        out.writeString(bigCoverUri);
        out.writeInt(rating);
        out.writeTypedList(songs);

    }

    public static final Parcelable.Creator<Album> CREATOR
            = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    private Album(Parcel in) {
        title = in.readString();
        author = in.readString();
        uri = in.readString();
        bigCoverUri = in.readString();
        rating = in.readInt();
        in.readTypedList(songs, Song.CREATOR);
    }

    public static class Song implements Parcelable{
        public String title;
        public String uri;
        public String lyrics;


        public Song(String title, String uri, String lyrics){
            this.title = title;
            // FIXME:
            String fixUri = "https://s3-us-west-2.amazonaws.com/pierrchen/music/lizhi/卡夫卡.mp3";
            try {
                this.uri = URLEncoder.encode(fixUri,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            this.lyrics = lyrics;

        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(title);
            parcel.writeString(uri);
            parcel.writeString(lyrics);

        }

        public static final Parcelable.Creator<Song> CREATOR
                = new Parcelable.Creator<Song>() {
            public Song createFromParcel(Parcel in) {
                return new Song(in);
            }

            public Song[] newArray(int size) {
                return new Song[size];
            }
        };

        private Song(Parcel in) {
            title = in.readString();
            uri = in.readString();
            lyrics = in.readString();

        }
    }
}
