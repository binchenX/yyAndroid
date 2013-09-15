package com.pierr.rockyouth;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
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
     * @param urlString
     * @return string of the Json file
     */

    public static String download(String urlString)  {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        URL url = null;
        try {
            url = new URL(urlString);
            URLConnection conn = url.openConnection();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
