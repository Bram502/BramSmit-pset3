package com.example.bwhsm.bramsmit_pset3;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bwhsm on 19-9-2017.
 */

public class TrackAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    MainActivity mainAct;

    /** constructor */
    public TrackAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "searching for tracks...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result.length() == 0) {
            Toast.makeText(context, "No results found...", Toast.LENGTH_SHORT).show();
        }
        else {
            ArrayList<String> trackData = new ArrayList<String>();
            try {
                JSONObject trackStreamobj = new JSONObject(result);
                JSONObject resultsObj = trackStreamobj.getJSONObject("results");
                JSONObject trackMatches = resultsObj.getJSONObject("trackmatches");
                JSONArray tracksObj = trackMatches.getJSONArray("track");

                for (int i = 0; i < tracksObj.length(); i++) {
                    JSONObject track = tracksObj.getJSONObject(i);
                    String trackName = track.getString("name");
                    String artistName = track.getString("artist");
                    JSONArray imageObj = track.getJSONArray("image");
                    String imageUrl = imageObj.getJSONObject(3).getString("#text");

                    trackData.add(trackName + " - " + artistName + " - " + imageUrl);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mainAct.trackStartIntent(trackData);
        }
    }
}
