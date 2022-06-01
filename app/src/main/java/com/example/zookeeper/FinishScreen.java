package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinishScreen extends AppCompatActivity {
    private RouteViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);
        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);
    }

    public void onHomeClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        viewModel.deleteAllNoUpdate();
        startActivity(intent);
    }
}