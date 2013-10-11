package com.pierr.rockyouth;

import android.util.Log;

import com.pierr.rockyouth.activity.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pierr on 13-9-15.
 *
 * Fetch json file from webserver
 */
public class Downloader {

    /**
     *
     * @param urlString url to the json
     * @return string of the Json file
     */

    public static String download(String urlString)  {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        URL url;
        try {
            url = new URL(urlString);
            URLConnection conn = url.openConnection();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(MainActivity.TAG, "malformed url " + urlString);
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "error when download json file " + urlString);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(MainActivity.TAG,"json file downloaded . size " + sb.length());

        return sb.toString();


    }

}
