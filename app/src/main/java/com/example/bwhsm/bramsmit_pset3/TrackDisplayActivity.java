package com.example.bwhsm.bramsmit_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TrackDisplayActivity extends AppCompatActivity {

    String trackData;
    SharedPreferences prefs;
    ArrayList<String> trackArray;
    String jsonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_display);

        Bundle extras = getIntent().getExtras();
        trackData = (String) extras.getSerializable("data");
        String[] split = trackData.split(" - ");

        String track = split[0];
        String artist = split[1];
        String imgUrl = split[2];

        new ImageAsyncTask((ImageView) findViewById(R.id.albumDisplay)).execute(imgUrl);

        TextView trackView = (TextView) findViewById(R.id.trackDisplay);
        TextView artistView = (TextView) findViewById(R.id.artistDisplay);

        trackView.setText(track);
        artistView.setText(artist);

        Button addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new ClickHandler());
        loadSharedPrefs();

    }

    private void loadSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        jsonList = prefs.getString("tracks",null);

        if (jsonList==null) {
            trackArray = new ArrayList<String>();
        }
        else {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            trackArray = gson.fromJson(jsonList,type);
        }

    }

    private void addToSharedPrefs() {
        Boolean added = false;
        for (int i=0; i<trackArray.size(); i++) {
            if (trackData.equals(trackArray.get(i))) {
                added = true;
            }
        }
        if (!added) {
            trackArray.add(trackData);
            saveSharedPrefs();
        }
        else {
            Toast.makeText(this, "Track already in your favorites...", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveSharedPrefs(){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        jsonList = gson.toJson(trackArray);
        editor.putString("tracks", jsonList);
        editor.commit();
        goToSavedList();

    }

    private void goToSavedList() {
        Intent savedListIntent  = new Intent(this, SavedListActivity.class);
        this.startActivity(savedListIntent);
        finish();
    }


    private class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            addToSharedPrefs();
        }
    }
}
