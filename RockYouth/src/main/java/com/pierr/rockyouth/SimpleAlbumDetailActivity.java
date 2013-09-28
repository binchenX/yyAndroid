package com.pierr.rockyouth;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SimpleAlbumDetailActivity extends Activity {

    private Album mAlbum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_album_detail);

        mAlbum = getIntent().getExtras().getParcelable("albumDetail");

    }


    @Override
    protected void onResume() {

        super.onResume();
        Log.d(MainActivity.TAG, "onResume");
        //show cover with title
        ImageView bigCover = (ImageView) findViewById(R.id.frag_listen_album_cover_big);
        TextView  albumTitle = (TextView) findViewById(R.id.frag_listen_album_title);
        ImageLoader.getInstance().displayImage(mAlbum.bigCoverUri,bigCover);
        albumTitle.setText(mAlbum.title);

        //show song list
        TextView songListView = (TextView) findViewById(R.id.song_list);
        StringBuilder sb = new StringBuilder();
        List<Album.Song> songs = mAlbum.songs;
        for(Album.Song s : songs){
            sb.append(s.title).append("\n");
        }
        songListView.setText(sb.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_album_detail, menu);
        return true;
    }
    
}
