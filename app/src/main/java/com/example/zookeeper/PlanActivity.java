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
        //adapter.setOnCheckBoxClickedHandler(viewModel::toggleCompleted);
        //adapter.setOnTextEditedHandler(viewModel::updateText);
        adapter.setOnDeleteButtonClicked(viewModel::deleteTodo);
        viewModel.getRoute().observe(this, adapter::setRouteItems);

        recyclerView = findViewById(R.id.route_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //this.newTodoText = this.findViewById(R.id.new_todo_text);
        //this.addTodoButton = this.findViewById(R.id.add_todo_btn);

        //addTodoButton.setOnClickListener(this::onAddTodoClicked);
    }

    public void onBackClicked(View view) {
        finish();
    }
}