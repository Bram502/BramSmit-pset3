package com.example.bwhsm.bramsmit_pset3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.searchButton).setOnClickListener(new ClickHandler());
        etTrack = (EditText) findViewById(R.id.etTrack);
        assert etTrack != null;
    }

    public void trackSearch(View view) {
        String trackSearch = etTrack.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        asyncTask.execute(trackSearch);
        etTrack.getText().clear();
    }

    public void trackStartIntent(ArrayList<String> trackData) {
        Intent dataIntent = new Intent(this, DataActivity.class);
        dataIntent.putExtra("data", trackData);
        this.startActivity(dataIntent);
    }

    private void createSharedPrefs() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("test - test - test");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String jsonList = gson.toJson(testArray);

        editor.putString("tracks", jsonList);
        editor.commit();
    }

    private class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            trackSearch(view);
        }
    }
}
