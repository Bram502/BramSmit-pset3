package com.example.bwhsm.bramsmit_pset3;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bwhsm on 19-9-2017.
 */

public class HttpRequestHelper {

    protected static synchronized String downloadFromServer(String... params) {
        String result = "";
        String chosenTag = params[0];

        URL url = null;
        try {
            url = new URL("http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + chosenTag + "&api_key=8d79569ac3702cdae1469242a5587161&format=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connect;

        if (url != null) {
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Integer responseCode = connect.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;
                    while ((line = bReader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}
