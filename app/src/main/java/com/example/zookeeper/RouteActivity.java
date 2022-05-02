package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class RouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        ArrayList<String> input = new ArrayList<>(Arrays.asList("lions","gorillas", "gorillas", "gators","arctic_foxes","gorillas"));
        PathGenerator gen = new PathGenerator(this);

        gen.generatePlan(input);
        String[] order = gen.getOrder();
        for(String s : order){
            Log.d("LOOKHERE",s);
        }
        double[] distance = gen.getDist();
        for(double d : distance){
            Log.d("lOOKHERE", d+"");
        }


    }

}