package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    RouteViewModel viewModel;
    RecyclerView recyclerView;
    TextView numAnimals;
    Button generatePlanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);

        //set number of animals textview
        numAnimals = this.findViewById(R.id.num_animals);
        numAnimals.setText("Number of Exhibits: " + viewModel.getList().size());

        RouteAdapter adapter = new RouteAdapter();
        adapter.setHasStableIds(true);
        //initialize delete button and store numAnimals text view when delete is clicked
        adapter.setOnDeleteButtonClicked(viewModel::deleteTodo, numAnimals);

        viewModel.getRoute().observe(this, adapter::setRouteItems);
        //set view to text
        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //start the plan
        this.generatePlanBtn = this.findViewById(R.id.generate_btn);
        generatePlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.getIds().isEmpty()){
                    finish();
                } else {
                    Intent intent = new Intent(PlanActivity.this, RoutePlanActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    //back to search
    public void onBackClicked(View view) {
        finish();
    }

    //generate plan
    public void onGenerateClicked(View view) {
        Intent intent = new Intent(this, RoutePlanActivity.class);
        startActivity(intent);
    }
    //clear all
    public void onClearButtonClicked(View view){
        TextView num = findViewById(R.id.num_animals);
        viewModel.deleteAll(num);
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }
}