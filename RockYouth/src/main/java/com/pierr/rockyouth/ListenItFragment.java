package com.pierr.rockyouth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pierr on 13-9-21.
 */
public class ListenItFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_album_detail_listen, container, false);

        Album album = getArguments().getParcelable("album");

        ImageView bigCover = (ImageView) rootView.findViewById(R.id.frag_listen_album_cover_big);
        TextView  albumTitle = (TextView) rootView.findViewById(R.id.frag_listen_album_title);
        //albumTitle.setTextAlignment();

        ImageLoader.getInstance().displayImage(album.bigCoverUri,bigCover);
        albumTitle.setText(album.title);


        // TODO:set other stuff

        TextView currSongTextView = (TextView) rootView.findViewById(R.id.frag_listen_current_song);

        String currentSong = album.songs.get(0).title;
        currSongTextView.setText(currentSong);

        return rootView;
    }
}
