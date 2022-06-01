package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        SharedPreferences pref = getSharedPreferences("sharedpref",MODE_PRIVATE);
        int page  = pref.getInt("pos", -1);
        Intent intent;
        Log.d("persistence!!", "" + page);
        switch(page){
            case -1:
                intent = new Intent(this, SearchActivity.class);
                break;
            case -2:
                intent = new Intent(this, PlanActivity.class);
                break;
            default:
                intent = new Intent(this, RoutePlanActivity.class);
        }
        startActivity(intent);
//        Intent intent = new Intent(this, GoogleMapsActivity.class);
//        startActivity(intent);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        SharedPreferences pref = getSharedPreferences("sharedpref",MODE_PRIVATE);
        int page  = pref.getInt("pos", -1);
        Intent intent;
        Log.d("pers", "h" + page);
        switch(page){
            case -1:
                intent = new Intent(this, SearchActivity.class);
                break;
            case -2:
                intent = new Intent(this, PlanActivity.class);
                break;
            default:
                intent = new Intent(this, RoutePlanActivity.class);

        }
        startActivity(intent);
    }

//    public void open_route(View view) {
//        Intent intent = new Intent(this,RoutePlanActivity.class);
//        startActivity(intent);
//    }
}