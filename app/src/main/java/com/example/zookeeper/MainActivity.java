package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
       Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
//        Intent intent = new Intent(this, GoogleMapsActivity.class);
//        startActivity(intent);
    }

//    public void open_route(View view) {
//        Intent intent = new Intent(this,RoutePlanActivity.class);
//        startActivity(intent);
//    }
}