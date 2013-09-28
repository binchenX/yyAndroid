package com.pierr.rockyouth;

/**
 * Created by Pierr on 13-9-14.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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

    private AlbumAdapter mCurrentAlbumAdapter = null;
    public AlbumListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album_list, container, false);

        int sortType = getArguments().getInt(ARG_SECTION_NUMBER);

        //start loading the data and setup the adapter
        AlbumDataBase.queryDataAsync(new AlbumDataBase.DataAvailableListener() {
            @Override
            public void onDataAvailable(List<Album> data) {
                //we got the data, set the list adapter
                onQueryDataAvailable(data);
            }
        });

        return rootView;
    }

    /**
     * our query returned with data! put the data in the adapter and set it to the ListFragment.
     *
     * @param albums
     */

    private void onQueryDataAvailable(List<Album> albums){

        AlbumAdapter adapter = new AlbumAdapter(getActivity(),albums);
        mCurrentAlbumAdapter = adapter;
        setListAdapter(mCurrentAlbumAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Don't delete! This is a hack!
        //http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa/10261438#10261438

        //super.onSaveInstanceState(outState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //get  the album from the adapter
        Album album = (Album)mCurrentAlbumAdapter.getItem(position);
        if (album == null) {
            Toast.makeText(getActivity(),"Can't find the album",Toast.LENGTH_LONG).show();
            Log.e(MainActivity.TAG,"can't find the album detail");
            return;
        }
        showDetail(album);

    }

    private void showDetail(Album album) {
        //Intent intent = new Intent(getActivity(),AlbumDetailActivity.class);
        Intent intent = new Intent(getActivity(),SimpleAlbumDetailActivity.class);
        intent.putExtra("albumDetail",album);
        Toast.makeText(getActivity(), "show " + album.title, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    class AlbumAdapter extends BaseAdapter{
        private Context context;
        private List<Album> albums = null;

        public AlbumAdapter(Context context, List<Album> albums) {
            this.context = context;
            this.albums = albums;
        }

        @Override
        public int getCount() {
            if (albums == null) return 0;
            return albums.size();
        }

        @Override
        public Object getItem(int i) {

            if (i < albums.size()) {
                return albums.get(i);
            }

            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            Album album = albums.get(position);
            View row = view;

            ViewHolder viewHolder = null;
            if (row == null) {
                // trick: should the last parameters to inflate MUST be false
                 row = LayoutInflater.from(context).inflate(R.layout.album_item, viewGroup, false);

                viewHolder = new ViewHolder();

                viewHolder.albumImage = (ImageView) row.findViewById(R.id.album_image);
                viewHolder.albumTitle = (TextView) row.findViewById(R.id.album_title);
                viewHolder.albumAuthor = (TextView) row.findViewById(R.id.album_author);

                row.setTag(viewHolder);

            } else {

                viewHolder = (ViewHolder)row.getTag();
            }

            viewHolder.albumAuthor.setText(album.author);
            viewHolder.albumTitle.setText(album.title);

            ImageLoader.getInstance().displayImage(album.uri, viewHolder.albumImage);

            return row;

        }
    }


    static class ViewHolder {

        ImageView albumImage;
        TextView albumTitle;
        TextView albumAuthor;
        TextView albumRating;
    }
}



