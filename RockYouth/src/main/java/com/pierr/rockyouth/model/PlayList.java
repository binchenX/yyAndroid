package com.pierr.rockyouth.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Singleton maintaiting play list & current playing status.
 *
 *
 *
 */
public class PlayList {


    private static  final String TAG = "RockYouth:PL";
    private static PlayList mInstance;

    // Data1 : song list
    // write by Activity (main thread)
    // read by PlayService (play thread)
    // synchronize needed
    private int mCurrentIndex;
    private List<Album.Song> mList;
    private int mSize;

    // Data 2 : isPlaying
    // read/write by PlayControl. No sychronize needed.
    private boolean mIsPlaying = false;

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
        loadFromDisk();
    }


    private void loadFromDisk() {
        // FIXME: hack to set up some songs:
        mokeUpsongs();

    }

    private  void saveToDisk(){

    }

    private void mokeUpsongs() {
        String uri = "https://s3-us-west-2.amazonaws.com/pierrchen/music/lizhi/卡夫卡.mp3";
        Album.Song song = new Album.Song("孤独的人是可耻的", uri ,"lyrcis");

        String uri2 = "https://s3-us-west-2.amazonaws.com/pierrchen/music/lizhi/欢愉..mp3";
        Album.Song song2 = new Album.Song("蚂蚁", uri2 ,"lyrcis");

        Album.Song song3 = new Album.Song("光明大道", uri ,"lyrcis");

        Album.Song song4 = new Album.Song("赵小姐", uri2 ,"lyrcis");

        List<Album.Song> songs = new ArrayList<Album.Song>();
        songs.add(song);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        addSongs(songs);
    }

    public void addSongs(List<Album.Song> songs){
        synchronized (this) {
            // TODO: find duplication
            Log.d(TAG,"add " + songs.size() + " to song list");
            mList.addAll(songs);
            mSize = mList.size();
        }
        //FIXME :do it in background
        saveToDisk();

    }


    public void replace(List<Album.Song> songs) {
        synchronized (this) {
            mList.clear();
            addSongs(songs);
        }
    }



    public Album.Song getCurrentSong(){
        synchronized (this) {
            if(mCurrentIndex > mList.size() - 1) {
                return null;
            }
            return mList.get(mCurrentIndex);
        }
    }


    public Album.Song getNextSong(){
        synchronized (this) {
            int next = mCurrentIndex + 1;
            next = next >= mSize ? 0 : next;
            mCurrentIndex = next;
            return mList.get(mCurrentIndex);
        }
    }

    public Album.Song getPrevSong(){
        synchronized (this) {
            int prev = mCurrentIndex - 1;
            prev = prev < 0 ? mSize - 1 : prev;
            mCurrentIndex = prev;
            return mList.get(mCurrentIndex);
        }
    }


    public void setIsPlaying(boolean isPlaying) {
        Log.d(TAG,"setIsPlaying " + isPlaying);
        mIsPlaying = isPlaying;
    }

    public Boolean isPlaying() {
        Log.d(TAG, "getIsPlaying " + mIsPlaying);
        return mIsPlaying;
    }
}
