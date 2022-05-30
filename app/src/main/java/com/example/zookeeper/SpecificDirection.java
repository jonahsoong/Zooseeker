package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    public RecyclerView recyclerView;

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

        // bug with first set of directions being empty
        // this fixes reliably but don't know why that happens


        DirectionAdapter adapter = new DirectionAdapter();
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setDirectionItems(gen.getNext().directionsDetailed);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!gen.isFinished()){
                    adapter.setDirectionItems(gen.getNext().directionsDetailed);
                } else{
                    nextButton.setEnabled(false);
                }
            }
        });



    }
}