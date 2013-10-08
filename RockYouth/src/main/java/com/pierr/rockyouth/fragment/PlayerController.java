package com.pierr.rockyouth.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierr.rockyouth.MusicPlayService;
import com.pierr.rockyouth.R;

/**
 * Created by Pierr on 13-10-7.
 */
public class PlayerController extends Fragment {

    private Context mContext ;

    private MusicPlayService mPlayerService;
    ServiceConnection mCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mPlayerService = ((MusicPlayService.LocalBinder)iBinder).getService();

            // FIXME
            //start play the music
            //mPlayerService.playNext();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();

        View rootView = inflater.inflate(R.layout.player_controller, container, false);
        ImageButton previous = (ImageButton) rootView.findViewById(R.id.previous);
        ImageButton playOrPause = (ImageButton) rootView.findViewById(R.id.play);

        ImageButton next = (ImageButton) rootView.findViewById(R.id.next);


        Intent playerService = new Intent(mContext, MusicPlayService.class);
        mContext.startService(playerService);

        //let's bind the service,so as to get the control interface
        mContext.bindService(playerService, mCon, Activity.BIND_AUTO_CREATE);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerService.playNext();
            }
        });

        playOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerService.pauseOrResume();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerService.playNext();
            }
        });


        return rootView;
    }
}
