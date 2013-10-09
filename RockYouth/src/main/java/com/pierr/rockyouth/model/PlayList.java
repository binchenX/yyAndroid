package com.pierr.rockyouth.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierr on 13-10-7.
 *
 * Singleton play list. Set by activty and read by PlayService
 */
public class PlayList {


    private static  final String TAG = "RockYouth:PL";
    private static PlayList mInstance;

    private int mCurrentIndex;
    private List<Album.Song> mList;
    private int mSize;

    public static PlayList getInstance(){

        if (mInstance == null) {
            mInstance = new PlayList();
        }
        return mInstance;
    }


    private PlayList(){

        mList = new ArrayList<Album.Song>();
        mCurrentIndex = 0;
        mSize = 0;
    }

    public void addSongs(List<Album.Song> songs){
        // TODO: find duplication
        mList.addAll(songs);
        mSize = mList.size();
    }


    public Album.Song getCurrentSong(){

        return mList.get(mCurrentIndex);
    }


    public Album.Song getNextSong(){

        int next = mCurrentIndex + 1;
        next = next >= mSize ? 0 : next;
        mCurrentIndex = next;
        return mList.get(mCurrentIndex);
    }

    public Album.Song getPrevSong(){
        int prev = mCurrentIndex - 1;
        prev = prev < 0 ? mSize - 1 : prev;
        mCurrentIndex = prev;
        return mList.get(mCurrentIndex);
    }

}
