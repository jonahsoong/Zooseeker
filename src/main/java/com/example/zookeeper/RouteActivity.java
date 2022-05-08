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

public class RoutePlanActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private Button directionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        this.directionsButton = this.findViewById(R.id.get_directions_btn);

        ArrayList<String> input = new ArrayList<>(Arrays.asList("lions","gorillas", "gorillas", "gators","arctic_foxes","gorillas"));
        PathGenerator gen = new PathGenerator(this);
        RouteAdapter adapter = new RouteAdapter();

        gen.generatePlan(input);
        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ArrayList<RouteExhibitItem> route = gen.getRoute();
        for(String dir: route.get(1).directions){
            Log.d("ABDDSS", dir);
        }
        adapter.setRouteExhibitItems(route);
        directionsButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //https://stackoverflow.com/questions/5374546/passing-arraylist-through-intent
                //allows transferring data through Extras
                Bundle b = new Bundle();
                b.putSerializable("route_exhibits", (Serializable) route);
                Intent intent = new Intent(RoutePlanActivity.this,SpecificDirection.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }


}