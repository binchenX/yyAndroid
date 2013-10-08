package com.pierr.rockyouth;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.pierr.rockyouth.activity.MainActivity;
import com.pierr.rockyouth.model.Album;
import com.pierr.rockyouth.model.PlayList;

import java.io.IOException;


/**
 * Created by Pierr on 13-10-7.
 *
 * This service is both started and binded service. We want it be started, because we
 * did not want the service be associated with certain activity; we want it be binded because
 * we have more "advanced" interface such as pause/resume.
 *
 *
 * play a song list, pause/resume current song, automatically play next song.
 *
 * MusicPlayService is responsible for maintain a global play list
 */
public class MusicPlayService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener ,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnBufferingUpdateListener{

    //init in playSongList
    private PlayList mPlayList = PlayList.getInstance();
    private MediaPlayer mAudioPlayer;
    private Context mContext ;
    private LocalBinder mBinder = new LocalBinder();
    private  volatile  PlayerHandler mPlayerHandler ;
    private Looper mServiceLoop;

    private  static final  String TAG = "RockYouth:MPS";
    // audioPlayer is BEYOND prepared (could be in started, paused).
    // set to true in  prepare callback, set to false after reset

    private boolean mPrepared = false;


    private static final int  PAUSE_OR_RESUME = 0 ;
    private static final int  NEXT = 3 ;
    private static final int  PREVIOUS = 4 ;


    // implement AudioPlayer callback listener

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // play next automatically
        playNext();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        // TODO: pass back to UI thread , use LocalBroadcastManager
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
         mPrepared = true;
    }


    private  final class PlayerHandler extends Handler {

        private PlayerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PAUSE_OR_RESUME:
                    onPauseOrResume();
                    break;
                case NEXT:
                    onNext();
                    break;
                case PREVIOUS:
                    onPrevious();
                    break;
                default:
                    Toast.makeText(mContext,"unknown message",Toast.LENGTH_LONG).show();
                    break;
            }
        }

        private void onPrevious() {
            Log.d(TAG, "onPrevious");
            play(mPlayList.getPrevSong());
        }

        private void onNext() {
            Log.d(TAG, "onNext");
            play(mPlayList.getNextSong());
        }


        private void onPauseOrResume(){
            Log.d(TAG, "onPauseOrResume");
            // pause
            if (mAudioPlayer.isPlaying()) {
                Log.d(MainActivity.TAG, "Pause");
                mAudioPlayer.pause();
            } else {
                // resume
                if (mPrepared) {
                    Log.d(MainActivity.TAG, "Resume");
                    mAudioPlayer.getCurrentPosition();
                    mAudioPlayer.start();
                } else {
                    // play a fresh song
                    Log.d(MainActivity.TAG, "Play current song");
                    play(PlayList.getInstance().getCurrentSong());
                }
            }

        }

        // jump to a new song from start
        private void play(Album.Song song) {
            Log.d(TAG, "play song" + song.title);
            try {
                mAudioPlayer.reset();
                mAudioPlayer.setDataSource(mContext, Uri.parse(song.uri));
                mAudioPlayer.prepare();
                mAudioPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"Wrong song URI",Toast.LENGTH_LONG).show();
            }

        }

    }


    public class LocalBinder extends Binder {
        public MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
        mContext = getApplicationContext();
        mAudioPlayer = new MediaPlayer();
        mAudioPlayer.setOnCompletionListener(this);
        mAudioPlayer.setOnPreparedListener(this);
        mAudioPlayer.setOnErrorListener(this);
        mAudioPlayer.setOnBufferingUpdateListener(this);

        // create a thread that has a loop which can pass to Handler
        HandlerThread handleThread = new HandlerThread("PlayerService");
        handleThread.start();

        mServiceLoop = handleThread.getLooper();
        mPlayerHandler = new PlayerHandler(mServiceLoop);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        // do nothing. just make the service sticky
        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        mAudioPlayer.release();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

//song player interface

    public void pauseOrResume(){
        Message msg =  mPlayerHandler.obtainMessage(PAUSE_OR_RESUME);
        mPlayerHandler.sendMessage(msg);
    }

    public void playNext(){
        Message msg =  mPlayerHandler.obtainMessage(NEXT);
        mPlayerHandler.sendMessage(msg);
    }
    public void playPrevious(){
        Message msg =  mPlayerHandler.obtainMessage(PREVIOUS);
        mPlayerHandler.sendMessage(msg);
    }



}
