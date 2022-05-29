package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SpecificDirection extends AppCompatActivity {
    private Button nextButton;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int[] currentDirection = {1};
        Log.d("ACTIVITY", "Success");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_direction);
        //https://stackoverflow.com/questions/5374546/passing-arraylist-through-intent
        //passes ArrayList<POJO> through to this activity
        Bundle b = getIntent().getExtras();
        ArrayList<RouteExhibitItem> route = (ArrayList<RouteExhibitItem>) b.getSerializable("route_exhibits");
        for(RouteExhibitItem step : route){
            for(String s : step.directionsDetailed)
                Log.d("HELPMEOUTHERE", s);
        }
        this.nextButton = this.findViewById(R.id.Nextbutton);
        Queue<ArrayList<String>> directions = new LinkedList<>();
        for(RouteExhibitItem dr : route){
            directions.add(dr.directionsDetailed);
        }
        DirectionAdapter adapter = new DirectionAdapter();
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setDirectionItems(directions.poll());

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!directions.isEmpty()){
                    adapter.setDirectionItems(directions.poll());
                } else{
                    nextButton.setEnabled(false);
                }
            }
        });

        //  for(String step : route.get(currentDirection[0]).directions){
        //      Log.d("HELPMEOUTHERE", step);
        //  }


    }
}