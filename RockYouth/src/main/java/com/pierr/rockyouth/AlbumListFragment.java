package com.pierr.rockyouth;

/**
 * Created by Pierr on 13-9-14.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public  class AlbumListFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

   //private int sortType = SORT_BY_TIME;


    private static final int SORT_BY_TIME = 1;
    private static final int SORT_BY_RATING = 2;

    public AlbumListFragment() {
    }


    ListAdapter getAlbumsAdapter(int sortType) {

        // TODO:get the really data from Model

        ArrayList<String> l = new ArrayList<String>();

        if (sortType == SORT_BY_TIME) {
            l.add("rocks1");
            l.add("rocks3");
            l.add("rocks3");
        } else {
            l.add("rocks12");
            l.add("rocks22");
            l.add("rocks32");
        }

        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, l);


        return ad;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album_list, container, false);

        int sortType = getArguments().getInt(ARG_SECTION_NUMBER);
        setListAdapter(getAlbumsAdapter(sortType));

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //this is a hack for
        //http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa/10261438#10261438

        //super.onSaveInstanceState(outState);
    }
}



