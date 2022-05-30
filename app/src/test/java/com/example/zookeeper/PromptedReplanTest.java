package com.example.zookeeper;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(RobolectricTestRunner.class)
public class PromptedReplanTest {
    Context appContext = null;
    PathGenerator path = null;
    @Before
    public void getContext(){
        appContext = ApplicationProvider.getApplicationContext();
        path = new PathGenerator(appContext);
    }
    @Test
    public void testEarlyReplan(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("entrance_exit_gate","flamingo","capuchin","koi"));
        PathGenerator gen = new PathGenerator(appContext);
        gen.generatePlan(input);
        //simulating what happens if you get closer to Koi Fish than Flamingo by location
        input = new ArrayList<>(Arrays.asList("koi","flamingo","capuchin"));
        gen.generatePlan(input);
        ArrayList<RouteExhibitItem> route2 = gen.getRoute();
        ArrayList<String> output = new ArrayList<>(Arrays.asList("Koi Fish","Flamingos","Capuchin Monkeys","Entrance and Exit Gate"));
        int j = 0;
        for(RouteExhibitItem i : route2){
            System.out.println(i.sink + " | " + output.get(j));
            Assert.assertEquals(i.sink,output.get(j));
            j++;
        }

    }
    @Test
    public void testLateReplan(){
        // for later addition
        /*ArrayList<String> input = new ArrayList<>(Arrays.asList("entrance_exit_gate","flamingo","capuchin","koi",));
        PathGenerator gen = new PathGenerator(appContext);
        gen.generatePlan(input);
        //simulating what happens if you get closer to Koi Fish than Flamingo by location
        input = new ArrayList<>(Arrays.asList("koi","flamingo","capuchin"));
        gen.generatePlan(input);
        ArrayList<RouteExhibitItem> route2 = gen.getRoute();
        ArrayList<String> output = new ArrayList<>(Arrays.asList("Koi Fish","Flamingos","Capuchin Monkeys","Entrance and Exit Gate"));
        int j = 0;
        for(RouteExhibitItem i : route2){
            System.out.println(i.name + " | " + output.get(j));
            Assert.assertEquals(i.name,output.get(j));
            j++;
        }*/
    }
}
