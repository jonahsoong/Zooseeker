package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.alg.util.Pair;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class RoutePlanActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private Button directionsButton;
    private Button clearButton;
    private RouteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        this.directionsButton = this.findViewById(R.id.get_directions_btn);
        clearButton = this.findViewById(R.id.clearBtn);
        viewModel = viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);
        ArrayList<String> input = new ArrayList<>(viewModel.getIds());
        input.add(0,"entrance_exit_gate");
        for(String i : input){
            Log.i("TEST", i);
        }


        SharedPreferences prefs = getSharedPreferences("sharedpref",MODE_PRIVATE);
        int page  = prefs.getInt("pos", -1);
        if(page >= 0 )
            directionsButtonClick(input);
        else {
            //update last page
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("pos", -3);
            editor.commit();
        }
        Log.d("pers", "hello");
        PathGenerator gen = new PathGenerator(this);
        RoutePlanAdapter adapter = new RoutePlanAdapter();

        gen.generatePlan(input);
        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ArrayList<RouteExhibitItem> route = gen.getRoute();
        Map<String, Pair<Double, Double>> location = gen.getLocation();
        ArrayList<String> vectors = gen.getNodes();
        ArrayList<String> edge = gen.getEdge();

        adapter.setRouteExhibitItems(route);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                directionsButtonClick(input);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                viewModel.deleteAllNoUpdate();
                startActivity(intent);

            }
        });

    }
    public void directionsButtonClick(ArrayList<String> input){
        //https://stackoverflow.com/questions/5374546/passing-arraylist-through-intent
        //allows transferring data through Extras
        Bundle b = new Bundle();
        b.putSerializable("route_exhibits", (Serializable) input);
        Intent intent = new Intent(RoutePlanActivity.this, SpecificDirection.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
