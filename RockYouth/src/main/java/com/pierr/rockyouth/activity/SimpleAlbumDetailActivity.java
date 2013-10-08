package com.pierr.rockyouth.activity;

import android.animation.TimeInterpolator;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierr.rockyouth.ImageLoader;
import com.pierr.rockyouth.MusicPlayService;
import com.pierr.rockyouth.R;
import com.pierr.rockyouth.activity.MainActivity;
import com.pierr.rockyouth.model.Album;
import com.pierr.rockyouth.model.PlayList;

import java.util.List;

public class SimpleAlbumDetailActivity extends Activity {

    private Album mAlbum = null;
    private View mCoverView;


    private static final  String TAG = "SimpleDetailActivity";

    //for animation
    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;
    private TextView mSongListView;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_album_detail);

        getActionBar().setHomeButtonEnabled(true);


        Bundle bundle = getIntent().getExtras();
        mAlbum = bundle.getParcelable("albumDetail");

        final int thumbnailTop = bundle.getInt(".top");
        final int thumbnailLeft = bundle.getInt(".left");
        final int thumbnailWidth = bundle.getInt(".width");
        final int thumbnailHeight = bundle.getInt(".height");



        //get the container for animation
        mCoverView = findViewById(R.id.album_cover_big);

        //show cover with title
        ImageView imageView = (ImageView) findViewById(R.id.frag_listen_album_cover_big);
        TextView  albumTitle = (TextView) findViewById(R.id.frag_listen_album_title);
        ImageLoader.getInstance().displayImage(mAlbum.bigCoverUri, imageView);
        albumTitle.setText(mAlbum.title);

        //show song list
        mSongListView = (TextView) findViewById(R.id.song_list);
        StringBuilder sb = new StringBuilder();
        List<Album.Song> songs = mAlbum.songs;
        for(Album.Song s : songs){
            sb.append(s.title).append("\n");
        }
        mSongListView.setText(sb.toString());



        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mCoverView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mCoverView.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    mCoverView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / mCoverView.getWidth();
                    mHeightScale = (float) thumbnailHeight / mCoverView.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }


        // FIXME:


        //init the playList
        PlayList.getInstance().addSongs(mAlbum.songs);

    }


    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location, colorizing it in parallel. In parallel, the background of the
     * activity is fading in. When the pictue is in place, the text description
     * drops down.
     */
    public void runEnterAnimation() {
        final long duration = (long) 1000;

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        // we want scale from center - default
        //mCoverView.setPivotX(0);
        //mCoverView.setPivotY(0);
        mCoverView.setScaleX(0.3f);
        mCoverView.setScaleY(0.3f);
        //mCoverView.setTranslationX(mLeftDelta);
        //mCoverView.setTranslationY(mTopDelta);

        // We'll fade the song list in later
        mSongListView.setAlpha(0);

        // Animate scale and translation to go from thumbnail to full size
        mCoverView.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator).
                withEndAction(new Runnable() {
                    public void run() {
                        // Animate the description in after the image animation
                        // is done. Slide and fade the text in from underneath
                        // the picture.
                        mSongListView.setTranslationY(-mSongListView.getHeight());
                        mSongListView.animate().setDuration(duration/2).
                                translationY(0).alpha(1).
                                setInterpolator(sDecelerator);
                    }
                });

        // Fade in the black background
//        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
//        bgAnim.setDuration(duration);
//        bgAnim.start();

        // Animate a color filter to take the image from grayscale to full color.
        // This happens in parallel with the image scaling and moving into place.
//        ObjectAnimator colorizer = ObjectAnimator.ofFloat(PictureDetailsActivity.this,
//                "saturation", 0, 1);
//        colorizer.setDuration(duration);
//        colorizer.start();

//        // Animate a drop-shadow of the image
//        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(mShadowLayout, "shadowDepth", 0, 1);
//        shadowAnim.setDuration(duration);
//        shadowAnim.start();
    }



    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();

    }


    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_album_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                back();
                break;
            default:
                break;
        }
        return true;

    }

    private void back() {
        finish();
    }
}
