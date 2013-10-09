package com.pierr.rockyouth;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Pierr on 13-9-20.
 *
 *
 * Adapter class, will use UniversalImageLoader internally
 *
 */
public class ImageLoader {


    private static ImageLoader imageloader = null;

    private DisplayImageOptions options = null;


    public static ImageLoader getInstance() {
        if (imageloader == null) {
            imageloader = new ImageLoader();
        }

        return  imageloader;
    }

    //initialize it with config

    public void init(Context context)
    {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);


        //int radius = (int) context.getResources().getDimension(R.dimen.album_row_radius) - 2;
        final int radius = 2;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(radius))
                .build();

    }

    public void displayImage(String uri, ImageView imageView)
    {

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(uri, imageView, options, null);

    }


}
