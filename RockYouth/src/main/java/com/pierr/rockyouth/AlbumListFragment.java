package com.pierr.rockyouth;

/**
 * Created by Pierr on 13-9-14.
 */

import android.content.Context;
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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

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
    private DisplayImageOptions options;

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


        //setListAdapter(getAlbumsAdapter(sortType));
        //
        AlbumDataBase.queryDataAsync(new AlbumDataBase.DataAvailableListener() {
            @Override
            public void onDataAvailable(List<Album> data) {
                //we got the data, set the list adapter
                onQueryDataAvailable(data);
            }
        });



        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();


        return rootView;
    }

    /**
     * our query returned with data!
     *
     * @param albums
     */

    private void onQueryDataAvailable(List<Album> albums){

        AlbumAdapter adapter = new AlbumAdapter(getActivity(),albums);
        setListAdapter(adapter);

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
            //Log.d(MainActivity.TAG,"set ImageView uri " + album.uri);
            //String uri = "http://img1.douban.com/spic/s3383651.jpg";
            // TODO:use image download handling all the cache and decode stuff
            //viewHolder.albumImage.setImageURI(Uri.parse(uri));

            ImageLoader.getInstance().displayImage(album.uri, viewHolder.albumImage, options, null);

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



