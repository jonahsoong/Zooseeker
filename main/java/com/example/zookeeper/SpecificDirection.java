package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SpecificDirection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_direction);
        //https://stackoverflow.com/questions/5374546/passing-arraylist-through-intent
        //passes ArrayList<POJO> through to this activity
        Bundle b = getIntent().getExtras();
        ArrayList<RouteExhibitItem> route = (ArrayList<RouteExhibitItem>) b.getSerializable("route_exhibits");
        for(String step : route.get(1).directions){
            Log.d("HELPMEOUTHERE", step);
        }

    }
}