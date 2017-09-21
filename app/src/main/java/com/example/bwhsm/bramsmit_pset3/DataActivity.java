package com.example.bwhsm.bramsmit_pset3;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/** This activity gets all the data from the search query and puts it in an adapter for a listview. */
public class DataActivity extends AppCompatActivity {
    ArrayList<String> trackArray;
    ListView lvItems;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Bundle extras = getIntent().getExtras();
        trackArray = (ArrayList<String>) extras.getSerializable("data");

        ArrayAdapter arrayAdapter = new CustomAdapter(this,trackArray);
        lvItems = (ListView) findViewById(R.id.listViewID);
        lvItems.setAdapter(arrayAdapter);

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String trackData = String.valueOf(parent.getItemAtPosition(position));
                        displayIntent(trackData);
                    }
                }
        );

    }

    public void displayIntent(String trackData) {
        Intent displayIntent = new Intent(this, TrackDisplayActivity.class);
        displayIntent.putExtra("data", trackData);
        this.startActivity(displayIntent);
    }
}
