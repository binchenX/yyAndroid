package com.pierr.rockyouth;

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
}
