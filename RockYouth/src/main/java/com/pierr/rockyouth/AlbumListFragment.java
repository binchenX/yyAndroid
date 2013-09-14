package com.pierr.rockyouth;

/**
 * Created by Pierr on 13-9-14.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    BaseAdapter getAlbumsAdapter(int sortType) {

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

        //ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, l);

        // TODO:should ask Model for data
        return new AlbumAdapter(getActivity(),null);
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



    static class AlbumAdapter extends BaseAdapter{


        private Context context;

        public AlbumAdapter(Context context, List<Map<String,String>> items) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int i) {

            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            // trick: should the last parameters to inflate MUST be false
            View v = LayoutInflater.from(context).inflate(R.layout.album_item,viewGroup,false);
            ImageView albumImage = (ImageView)v.findViewById(R.id.album_image);
            TextView albumTitle = (TextView)v.findViewById(R.id.album_title);
            albumTitle.setText("text1");
            TextView albumAuthor = (TextView)v.findViewById(R.id.album_author);
            albumAuthor.setText("Who");
            return v;
        }
    }
}



