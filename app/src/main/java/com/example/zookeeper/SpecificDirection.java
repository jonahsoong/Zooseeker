package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Bundle b = getIntent().getExtras();
        ArrayList<RouteExhibitItem> route = (ArrayList<RouteExhibitItem>) b.getSerializable("route_exhibits");
        for(String step : route.get(1).directions){
            Log.d("HELPMEOUTHERE", step);
        }

    }
}