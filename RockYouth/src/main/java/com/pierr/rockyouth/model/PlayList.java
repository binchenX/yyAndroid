package com.pierr.rockyouth.model;

import android.util.Log;

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

    // make use of the fact that PlayList is singlton. Used by PlayControl
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

    public  void saveToDisk(){

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
        // TODO: find duplication
        Log.d(TAG,"add " + songs.size() + " to song list");
        mList.addAll(songs);
        mSize = mList.size();
    }


    public void replace(List<Album.Song> songs) {
        mList.clear();
        addSongs(songs);
    }



    public Album.Song getCurrentSong(){
        assert mCurrentIndex < mList.size() - 1;

        if(mCurrentIndex > mList.size() - 1) {
            return null;
        }

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


    public void setIsPlaying(boolean isPlaying) {
        Log.d(TAG,"setIsPlaying " + isPlaying);

        mIsPlaying = isPlaying;
    }

    public Boolean isPlaying() {
        Log.d(TAG, "getIsPlaying " + mIsPlaying);
        return mIsPlaying;
    }
}
