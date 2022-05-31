package com.example.zookeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

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

        // bug with first set of directions being empty
        // this fixes reliably but don't know why that happens


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
                    adapter.setDirectionItems(gen.getNext().directionsDetailed);
                } else{
                    nextButton.setEnabled(false);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nextButton.setEnabled(true);
                if(!gen.isEntrance()){
                    adapter.setDirectionItems(gen.getPrev().directionsDetailed);
                } else{
                    prevButton.setEnabled(false);
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(gen.position < gen.size()-1 && gen.size() > 2){
                    gen.skipExhibit();
                    adapter.setDirectionItems(gen.getCurrent().directionsDetailed);
                } else {
                    skipButton.setEnabled(false);
                }
            }
        });


    }

    public void onConfirmClicked(View view) {
        EditText latitude = findViewById(R.id.LatInput);
        EditText longitude = findViewById(R.id.LngInput);
//        convert to double
        double latInput = Double.parseDouble(latitude.getText().toString());
        double lngInput = Double.parseDouble(longitude.getText().toString());
        var checkLoc = new LocationChecker(this);
        if (latitude != null && longitude != null){
//            update the location with the input
//            we need to decide whether to call replan
            checkLoc.updateRoute(latInput, lngInput);
            LatLngs.current = new LatLng(latInput,lngInput);
            latitude.setText("");
            longitude.setText("");
        }
    }
}