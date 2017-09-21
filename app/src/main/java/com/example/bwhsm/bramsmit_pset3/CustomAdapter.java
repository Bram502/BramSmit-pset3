package com.example.bwhsm.bramsmit_pset3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bwhsm on 20-9-2017.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    private Drawable albumImg;

    public CustomAdapter(@NonNull Context context, ArrayList<String> trackArray) {
        super(context, R.layout.custom_row,trackArray);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);


//        for (int i=0; i < trackData.size();i++) {
//            String[] track = trackData.get(i).split("-");
//            trackArray.add(track[0]);
//        }
//        System.out.println(trackData);
//        System.out.println(trackArray);

        String trackData = getItem(position);
        String[] split = trackData.split(" - ");
        String track = split[0];
        String artist = split[1];
        String imgUrl = split[2];

        new ImageAsyncTask((ImageView) customView.findViewById(R.id.albumImg)).execute(imgUrl);

        TextView trackView = (TextView) customView.findViewById(R.id.trackName);
        TextView artistView = (TextView) customView.findViewById(R.id.artistName);
//        ImageView albumView = (ImageView) customView.findViewById(R.id.albumImg);

        trackView.setText(track);
        artistView.setText(artist);
//        albumView.setImageDrawable(albumImg);
        return customView;
    }

    public void setAlbumImg(Drawable d){
        albumImg = d;
    }


}
