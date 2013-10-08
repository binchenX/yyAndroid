package com.pierr.rockyouth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pierr.rockyouth.model.Album;
import com.pierr.rockyouth.R;

/**
 * Created by Pierr on 13-9-21.
 */
public class DiscussItFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_album_detail_discuss, container, false);
        TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        Album album = getArguments().getParcelable("album");

        dummyTextView.setText(album.title);
        return rootView;
    }
}
