package com.example.bwhsm.bramsmit_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class SavedListActivity extends AppCompatActivity {

    SharedPreferences prefs;
    ArrayList<String> trackArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);

        BottomNavigationView bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_search:
                        finish();
                        break;
                    case R.id.action_favorites:
                        goToSavedList();
                        break;
                }
                return true;
            }
        });

        loadSharedPrefs();

        ArrayAdapter arrayAdapter = new CustomAdapter(this,trackArray);
        ListView savedList = (ListView) findViewById(R.id.savedList);
        savedList.setAdapter(arrayAdapter);
        savedList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String trackData = String.valueOf(parent.getItemAtPosition(position));
                        displayIntent(trackData);
                    }
                }
        );
        savedList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        String trackData = String.valueOf(parent.getItemAtPosition(position));
                        removeTrack(trackData);
                        return true;
                    }
                }
        );
    }

    private void loadSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String jsonList = prefs.getString("tracks",null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        trackArray = gson.fromJson(jsonList,type);
    }

    private void displayIntent(String trackData) {
        Intent displayIntent = new Intent(this, TrackDisplayActivity.class);
        displayIntent.putExtra("data", trackData);
        this.startActivity(displayIntent);
    }

    public void removeTrack(String trackData) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().commit();

        for (int i=0; i < trackArray.size();i++) {
            if (trackData.equals(trackArray.get(i))) {
                trackArray.remove(i);
            }
        }
        Gson gson = new Gson();
        String jsonList = gson.toJson(trackArray);
        editor.putString("tracks", jsonList);
        editor.commit();
        goToSavedList();
    }

    private void goToSavedList() {
        Intent savedListIntent  = new Intent(this, SavedListActivity.class);
        this.startActivity(savedListIntent);
        finish();
    }


}
