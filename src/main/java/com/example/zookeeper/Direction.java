package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class Direction extends AppCompatActivity {

    PathGenerator path = new PathGenerator(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Bundle b = getIntent().getExtras();
        ArrayList<RouteExhibitItem> route = (ArrayList<RouteExhibitItem>) b.getSerializable("route_exhibits");


    }
}