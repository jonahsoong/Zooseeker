package com.example.zookeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

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
    private Button confirmMocking;
    public RecyclerView recyclerView;
    public boolean briefOrDetailed = true;
    private Switch briefDetailedSwitch;
    private PathGenerator gen;
    private LatLng current;
    private DirectionAdapter adapter;

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
        input.add(0,"entrance_exit_gate");

        gen = new PathGenerator(this);
        ((CurrentLocation) this.getApplication()).setPathGenerator(gen);
        current = ((CurrentLocation) this.getApplication()).getCurrentLocation();
        adapter = new DirectionAdapter();
        gen.generatePlan(input);
        ArrayList<RouteExhibitItem> route = gen.getRoute();
        Stack<RouteExhibitItem> visited = new Stack<>();
        this.nextButton = this.findViewById(R.id.Nextbutton);
        this.prevButton = this.findViewById(R.id.prevButton);
        this.skipButton = this.findViewById(R.id.skipButton);
        this.briefDetailedSwitch = findViewById(R.id.bodSwitch);
        this.confirmMocking = findViewById(R.id.mockConfirm);

        // bug with first set of directions being empty
        // this fixes reliably but don't know why that happens



        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if (briefOrDetailed)
            adapter.setDirectionItems(gen.getCurrent().directionsDetailed);
        else
            adapter.setDirectionItems(gen.getCurrent().directionsBrief);

//        btn onclick bod = false;
//        if (bod == true) detailed
//        else brief
        briefDetailedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               briefOrDetailed = b;
               if(briefOrDetailed){
                   adapter.setDirectionItems(gen.getCurrent().directionsDetailed);
                   briefDetailedSwitch.setText("change to brief directions");
               }
               else{
                   adapter.setDirectionItems(gen.getCurrent().directionsBrief);
                   briefDetailedSwitch.setText("change to detailed directions");
               }
           }
       });
//                new () {
//            @Override
//            public void onClick(View view) {
//                //dDir.toggle();
//                briefDetailedSwitch.toggle();
//                if(briefDetailedSwitch.isChecked())
//                    briefOrDetailed = true;
//                else
//                    briefOrDetailed = false;
//            }
//        });
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevButton.setEnabled(true);
                        if (!gen.isFinished()) {
                            //visited.add(route.remove(0));
                            if (briefOrDetailed)
                                adapter.setDirectionItems(gen.getNext().directionsDetailed);
                            else
                                adapter.setDirectionItems(gen.getNext().directionsBrief);
                        } else {
                            nextButton.setEnabled(false);
                        }
                    }
                });

        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nextButton.setEnabled(true);
                if(!gen.isEntrance()){
                    if (briefOrDetailed)
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
                if(gen.position < gen.size()-1 && (gen.size() - gen.position) > 2){
                    gen.rerouteSkip();
                    if (briefOrDetailed)
                        adapter.setDirectionItems(gen.getCurrent().directionsDetailed);
                    else
                        adapter.setDirectionItems(gen.getCurrent().directionsBrief);
                } else {
                    skipButton.setEnabled(false);
                }
            }
        });

        confirmMocking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText latitude = findViewById(R.id.LatInput);
                EditText longitude = findViewById(R.id.LngInput);
//        convert to double
                double latInput = Double.MIN_VALUE;
                double lngInput = Double.MIN_VALUE;
                try {
                    latInput = Double.parseDouble(latitude.getText().toString());
                    lngInput = Double.parseDouble(longitude.getText().toString());
                } catch (NumberFormatException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SpecificDirection.this);
                    builder.setMessage("Invalid Input! Please enter a number.");
                    builder.create();
                }
                LocationChecker.updateLocation(new LatLng(latInput, lngInput));
                String closest = LocationChecker.updateRoute(gen.getExhibitString(),gen.getRemainingLocations());
//                double latInput = Double.parseDouble(latitude.getText().toString());
//                double lngInput = Double.parseDouble(longitude.getText().toString());
                //gen = setLocation(latInput,lngInput);

//            update the location with the input
//            we need to decide whether to call replan

                    if (briefOrDetailed)
                        adapter.setDirectionItems(gen.getCurrent().directionsDetailed);
                    else
                        adapter.setDirectionItems(gen.getCurrent().directionsBrief);
                    //latitude.setText("");
                    //longitude.setText("");
            }
        });


    }

    public PathGenerator setLocation(double latInput, double lngInput){
        ((CurrentLocation) this.getApplication()).setCurrentLocation(new LatLng(latInput,lngInput));
        return ((CurrentLocation) this.getApplication()).getPathGenerator();
    }


}