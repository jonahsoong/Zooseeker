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
            for(String s : step.directions)
            Log.d("HELPMEOUTHERE", s);
        }
        this.nextButton = this.findViewById(R.id.Nextbutton);

        DirectionAdapter adapter = new DirectionAdapter();
        recyclerView = findViewById(R.id.direction_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setDirectionItems(route.get(currentDirection[0]).directions);
        currentDirection[0] += 1;

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentDirection[0] < route.size()-1) {
                    Log.d("ROUTESIZE", route.size()+
                            " ");
                    Log.d("CURRENTDIRECTION", currentDirection[0]+
                    " ");
                    Log.d("SourceAnimal", route.get(currentDirection[0]).name+
                            " ");
                    adapter.setDirectionItems(route.get(currentDirection[0]).directions);
                    currentDirection[0] += 1;
                }
                else {
                    nextButton.setEnabled(false);
                }
                for(String step : route.get(currentDirection[0]).directions){
                    Log.d("HELPMEOUTHEREEEEEEEEEEEEEEEEEEEEEEEEEEEE", step);
                }
            }
        });

      //  for(String step : route.get(currentDirection[0]).directions){
      //      Log.d("HELPMEOUTHERE", step);
      //  }


    }
}