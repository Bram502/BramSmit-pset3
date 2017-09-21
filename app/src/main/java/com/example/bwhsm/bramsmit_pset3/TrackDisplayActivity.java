package com.example.bwhsm.bramsmit_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackDisplayActivity extends AppCompatActivity {

    String trackArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_display);

        Bundle extras = getIntent().getExtras();
        trackArray = (String) extras.getSerializable("data");
        String[] split = trackArray.split(" - ");

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

    }

    public void goToSavedList() {
        Intent savedListIntent  = new Intent(this, SavedListActivity.class);
        savedListIntent.putExtra("data", trackArray);
        this.startActivity(savedListIntent);
    }

    private class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            goToSavedList();
        }
    }
}
