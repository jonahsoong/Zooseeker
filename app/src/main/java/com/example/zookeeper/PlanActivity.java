package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class PlanActivity extends AppCompatActivity {
    private RouteViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);

        RouteAdapter adapter = new RouteAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnDeleteButtonClicked(viewModel::deleteTodo);
        viewModel.getRoute().observe(this, adapter::setRouteItems);
        //set view to text
        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void onBackClicked(View view) {
        finish();
    }
}