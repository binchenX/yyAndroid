package com.pierr.rockyouth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pierr on 13-9-21.
 */
public class ReadItFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_album_detail_read, container, false);
        TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        Album album = getArguments().getParcelable("album");

        dummyTextView.setText(album.title);
        return rootView;
    }
}
