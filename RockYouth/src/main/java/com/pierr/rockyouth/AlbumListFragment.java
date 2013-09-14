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




    public AlbumListFragment() {
    }


    BaseAdapter getAlbumsAdapter(int sortType) {
        List<Album> albumData = AlbumDataBase.queryAlbum(sortType);
        return new AlbumAdapter(getActivity(),albumData);
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
        private List<Album> albums;

        public AlbumAdapter(Context context, List<Album> albums) {
            this.context = context;
            this.albums = albums;
        }

        @Override
        public int getCount() {
            return albums.size();
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



