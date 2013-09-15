package com.pierr.rockyouth;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pierr on 13-9-14.
 *
 * POD data for Album
 */
public class Album {

    public String author;
    public String title;
    public String uri;
    public int rating;

    public Album(String author, String title, String uri, int rating) {

        this.author = author;
        this.title = title;
        this.uri = uri;
        this.rating = rating;

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
            String title = jo.getString("title");
            String author = jo.getString("singer");
            int rating = jo.getInt("vote_points");

            album =  new Album(author,title,imageUri,rating);

        } catch (JSONException ex) {
            Log.w(MainActivity.TAG, "malformed JSON album object");

        }

        return album;






    }
}
