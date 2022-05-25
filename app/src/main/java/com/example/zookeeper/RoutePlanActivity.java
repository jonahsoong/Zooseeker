package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class RoutePlanActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private Button directionsButton;
    private RouteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        this.directionsButton = this.findViewById(R.id.get_directions_btn);
        viewModel = viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);
        ArrayList<String> input = new ArrayList<>(viewModel.getIds());
        PathGenerator gen = new PathGenerator(this);
        RoutePlanAdapter adapter = new RoutePlanAdapter();

        gen.generatePlan(input);
        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ArrayList<RouteExhibitItem> route = gen.getRoute();
        ArrayList<String> vectors = gen.getNodes();
        ArrayList<String> edge = gen.getEdge();
        Map<String, Pair<Double, Double>> location = gen.getLocation();
        for (String s : vectors) {
            Log.d("BEEEE", s);
        }
        for (String b : edge) {
            Log.d("ROOOO", b);
        }

        for (RouteExhibitItem item : route) {
            for (String i : item.directions) {
                Log.d("ABDDSS", i);
            }
        }
        adapter.setRouteExhibitItems(route);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://stackoverflow.com/questions/5374546/passing-arraylist-through-intent
                //allows transferring data through Extras
                Bundle b = new Bundle();
                b.putSerializable("route_exhibits", (Serializable) route);
                Intent intent = new Intent(RoutePlanActivity.this, SpecificDirection.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }
}
