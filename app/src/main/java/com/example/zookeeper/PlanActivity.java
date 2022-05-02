package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class PlanActivity extends AppCompatActivity {
    private RouteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);
        LiveData<List<RouteItem>> planned = viewModel.getRoute();
        Log.d("Planned", planned.toString());
    }

    public void onBackClicked(View view) {
        finish();
    }
}