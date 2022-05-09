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
    private RouteViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView numAnimals;
    private Button generatePlanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);

        numAnimals = this.findViewById(R.id.num_animals);
        numAnimals.setText("Number of Exhibits: " + viewModel.getList().size());

        RouteAdapter adapter = new RouteAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnDeleteButtonClicked(viewModel::deleteTodo, numAnimals);
        viewModel.getRoute().observe(this, adapter::setRouteItems);
        //set view to text
        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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

    public void onBackClicked(View view) {
        finish();
    }


    public void onGenerateClicked(View view) {
        Intent intent = new Intent(this, RoutePlanActivity.class);
        startActivity(intent);
    }
}