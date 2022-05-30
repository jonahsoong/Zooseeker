package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jgrapht.alg.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class SpecificDirection extends AppCompatActivity {
    private Button nextButton;
    private Button skipButton;
    private Button prevButton;
    public RecyclerView recyclerView;
    private TextView nextExhibit;
    private ImageView settings;
    private int directionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int[] currentDirection = {1};
        Log.d("ACTIVITY", "Success");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_direction);
        //https://stackoverflow.com/questions/5374546/passing-arraylist-through-intent
        //passes ArrayList<POJO> through to this activity
        //retrieves data for generating route. convenient to have full PathGenerator object for replanning.
        Bundle b = getIntent().getExtras();
        ArrayList<String> input = (ArrayList<String>) b.getSerializable("route_exhibits");
        PathGenerator gen = new PathGenerator(this);
        gen.generatePlan(input);

        ArrayList<RouteExhibitItem> route = gen.getRoute();
        this.nextButton = this.findViewById(R.id.Nextbutton);
        this.prevButton = this.findViewById(R.id.prevButton);
        this.skipButton = this.findViewById(R.id.skipButton);
        this.settings = this.findViewById(R.id.settings);

        // bug with first set of directions being empty
        // this fixes reliably but don't know why that happens

        directionType = SettingsActivity.status;
        DirectionAdapter adapter = new DirectionAdapter();
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setDirectionItems(gen.getCurrent().directionsDetailed);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                prevButton.setEnabled(true);
                if(!gen.isFinished()){
                    if(directionType == 0)
                        adapter.setDirectionItems(gen.getNext().directionsDetailed);
                    else
                        adapter.setDirectionItems(gen.getNext().directionsBrief);
                } else{
                    nextButton.setEnabled(false);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!gen.isEntrance()){
                    if(directionType == 0)
                        adapter.setDirectionItems(gen.getPrev().directionsDetailed);
                    else
                        adapter.setDirectionItems(gen.getPrev().directionsBrief);
                } else{
                    prevButton.setEnabled(false);
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("CONTROL", gen.size()+ "");
                if(gen.position < gen.size()-1 && gen.size() > 2){
                    gen.skipExhibit();
                    if(directionType == 0)
                        adapter.setDirectionItems(gen.getCurrent().directionsDetailed);
                    else
                        adapter.setDirectionItems(gen.getCurrent().directionsBrief);

                } else {
                    skipButton.setEnabled(false);
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SpecificDirection.this, SettingsActivity.class);
                startActivity(intent);

            }
        });



    }
}