package com.pierr.rockyouth.model;

import android.os.AsyncTask;
import android.util.Log;

import com.pierr.rockyouth.Downloader;
import com.pierr.rockyouth.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierr on 13-9-14.
 *
 * This is the Model use to query the data from the server or local cache.
 *
 *
 * TODO: rest API
 * https://developer.stackmob.com/rest-api/api-docs
 *
 * song.album
 * song.singer
 *
 */
public class AlbumDataBase {

    public static final int SORT_BY_TIME = 1;
    public static final int SORT_BY_RATING = 2;

    private static List<Album> albumsData = new ArrayList<Album>();

    /**
     * called on the UI thread , the listener should be running on UI thread as well.
     * This could be garentteed by AsyncTask Implementation.
     *
     *
     * TODO: improve the query interface
     * @param listener callback when data available
     */

    public static void queryDataAsync(DataAvailableListener listener){

        Log.d(MainActivity.TAG,"start query data");

        String dataQueryQui = "https://raw.github.com/pierrchen/datahouse/master/albums.json";
        new FetchDataFromServer(dataQueryQui,listener).execute();

    }

    public  interface  DataAvailableListener {
        void onDataAvailable(List<Album> data);
    }




    private  static class FetchDataFromServer extends AsyncTask<Void, Void, Void> {

        private final String url;
        private final DataAvailableListener listener;
        private final List<Album> result = new ArrayList<Album>();
        private FetchDataFromServer(String url,DataAvailableListener listener) {
            this.listener = listener;
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Download it , parse it

            String jsonData = Downloader.download(url);

            try {
                JSONArray jsonArray = new JSONArray(jsonData);

                for (int i = 0 ; i < jsonArray.length();i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    JSONObject post = jo.getJSONObject("post");
                    if (post != null) {
                        Album album = Album.createFromJson(post);
                        if (album != null) {
                            // TODO:fix me; song should come from Json as well
                            addSongs(album);
                            result.add(album);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(MainActivity.TAG,"error when parse JSON data");
            }

            return null;
        }

        // FIXME: need real song data
        private void addSongs(Album album) {

            String uri = "https://s3-us-west-2.amazonaws.com/pierrchen/music/lizhi/卡夫卡.mp3";
            Album.Song song = new Album.Song("孤独的人是可耻的", uri ,"lyrcis");

            String uri2 = "https://s3-us-west-2.amazonaws.com/pierrchen/music/lizhi/欢愉..mp3";
            Album.Song song2 = new Album.Song("蚂蚁", uri2 ,"lyrcis");

            Album.Song song3 = new Album.Song("光明大道", uri ,"lyrcis");

            Album.Song song4 = new Album.Song("赵小姐", uri2 ,"lyrcis");

            List<Album.Song> songs = new ArrayList<Album.Song>();
            songs.add(song);
            songs.add(song2);
            songs.add(song3);
            songs.add(song4);
            album.setSongs(songs);
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);

            // TODO: fix me. Need more design for data fetch
            // cache it
            for (Album album: result) {
                albumsData.add(album);
            }

            // notify listener
            if (listener != null ) {
                Log.d(MainActivity.TAG,"data is available, " + result.size() + " albums");
                listener.onDataAvailable(result);
            }
        }
    }


}
