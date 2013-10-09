package com.pierr.rockyouth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierr.rockyouth.model.Album;
import com.pierr.rockyouth.ImageLoader;
import com.pierr.rockyouth.R;

/**
 * Created by Pierr on 13-9-21.
 */
public class ListenItFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_album_detail_listen, container, false);


        assert rootView != null;
        ImageView bigCover = (ImageView) rootView.findViewById(R.id.frag_listen_album_cover_big);
        TextView  albumTitle = (TextView) rootView.findViewById(R.id.frag_listen_album_title);
        //albumTitle.setTextAlignment();

        Album album = getArguments().getParcelable("album");
        assert album != null;
        ImageLoader.getInstance().displayImage(album.bigCoverUri,bigCover);
        albumTitle.setText(album.title);


        // TODO:set other stuff

        TextView currSongTextView = (TextView) rootView.findViewById(R.id.frag_listen_current_song);

        String currentSong = album.songs.get(0).title;
        currSongTextView.setText(currentSong);

        return rootView;
    }
}
