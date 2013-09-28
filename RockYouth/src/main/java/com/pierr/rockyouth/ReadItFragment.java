package com.pierr.rockyouth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pierr on 13-9-21.
 */
public class ReadItFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_album_detail_read, container, false);
        TextView songListView = (TextView) rootView.findViewById(R.id.song_list);
        Album album = getArguments().getParcelable("album");

        StringBuilder sb = new StringBuilder();
        List<Album.Song> songs = album.songs;


        for(Album.Song s : songs){
            sb.append(s.title).append("\n");
        }

        songListView.setText(sb.toString());

        return rootView;
    }
}
