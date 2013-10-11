package com.pierr.rockyouth.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierr.rockyouth.ImageLoader;
import com.pierr.rockyouth.MusicPlayService;
import com.pierr.rockyouth.R;
import com.pierr.rockyouth.model.Album;
import com.pierr.rockyouth.model.PlayList;

/**
 *
 *
 * Minimized player control pannel.
 *
 * show current playing songs, as well as play control
 *
 * It is stateless. The playing status is stored in PlayList
 * which is a singleton.
 *
 * Work with PlayList to control the play/pause, next,previous.
 */
public class PlayerController extends Fragment {

    @SuppressWarnings("FieldCanBeLocal")
    private Context mContext ;

    private static  final  String TAG="RockYouth:PlayerCtrl";

    private MusicPlayService mPlayerService;
    private boolean mServiceBound = false;
    private final ServiceConnection mCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "AudioPlayer serviced connected");
            mPlayerService = ((MusicPlayService.LocalBinder)iBinder).getService();
            mServiceBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "AudioPlayer serviced disconnected");
            mServiceBound = false;

        }
    };

    private View mRootView;
    private boolean mCurrentPlaying = PlayList.getInstance().isPlaying();
    private ImageButton mPlayOrPauseBtn;
    private BitmapDrawable mPauseIcon;
    private BitmapDrawable mPlayIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startAndBindPlayService();

        mRootView = inflater.inflate(R.layout.player_controller, container, false);
        assert mRootView != null;

        setupView();
        return mRootView;
    }

    private void startAndBindPlayService() {
        mContext = getActivity();

        // start it
        Intent playerService = new Intent(mContext, MusicPlayService.class);
        mContext.startService(playerService);

        //let's bind the service,so as to get the control interface
        mContext.bindService(playerService, mCon, Activity.BIND_AUTO_CREATE);

    }

    private void setupView() {
        //load the pause and play drawable so as to swith quickly
        Bitmap pauseImage = BitmapFactory.decodeResource(getResources(),R.drawable.ic_pause);
        mPauseIcon = new BitmapDrawable(getResources(),pauseImage);
        Bitmap playImage = BitmapFactory.decodeResource(getResources(),R.drawable.ic_play);
        mPlayIcon = new BitmapDrawable(getResources(),playImage);

        // locate view objects
        ImageButton previous = (ImageButton) mRootView.findViewById(R.id.play_control_previous);
        mPlayOrPauseBtn = (ImageButton) mRootView.findViewById(R.id.play_control_play);
        //set current image
        Boolean isPlaying = PlayList.getInstance().isPlaying();
        mPlayOrPauseBtn.setImageDrawable(isPlaying ? mPauseIcon : mPlayIcon);

        ImageButton next = (ImageButton) mRootView.findViewById(R.id.play_control_next);


        //set up button's action
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerService.playNext();
                updateCurrentSongInfo();
            }
        });

        mPlayOrPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerService.pauseOrResume();

                mCurrentPlaying = !mCurrentPlaying;
                PlayList.getInstance().setIsPlaying(mCurrentPlaying);
                Log.d(TAG, "current status: " + (mCurrentPlaying?"playing":"pause"));
                updatePlayOrPauseButton();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerService.playNext();
                updateCurrentSongInfo();
            }
        });


        //set up current song infor
        updateCurrentSongInfo();


    }

    private void updatePlayOrPauseButton() {

        if (mCurrentPlaying) {
            //set it to pause
            mPlayOrPauseBtn.setImageDrawable(mPauseIcon);
        } else {
            mPlayOrPauseBtn.setImageDrawable(mPlayIcon);
        }

    }

    private void updateCurrentSongInfo() {
        ImageView albumIcon = (ImageView) mRootView.findViewById(R.id.play_control_album_image);
        TextView currentSongTextView = (TextView)mRootView.findViewById(R.id.play_control_current_song);
        TextView author  = (TextView)mRootView.findViewById(R.id.play_control_current_song_singer);

        // FixMe: fragment may create before you have a chance to add song to song list in onCreate
        // Need persistent on  playlist

        Album.Song currentSong = PlayList.getInstance().getCurrentSong();

        if (currentSong != null ) {
            // FIXME: album uri
            ImageLoader.getInstance().displayImage("http://img1.douban.com/spic/s3383651.jpg",albumIcon);
            currentSongTextView.setText(currentSong.title);
            author.setText("张楚/［一颗不肯媚俗的心］");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mServiceBound) {
            Log.d(TAG, "unbind audio service");
            Activity activity = getActivity();
            assert activity != null;
            activity.unbindService(mCon);

        }
    }
}
