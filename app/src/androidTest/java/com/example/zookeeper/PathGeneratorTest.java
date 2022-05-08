package com.example.zookeeper;


import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.annotation.UiContext;
import androidx.annotation.UiThread;
import androidx.annotation.UiContext;
import androidx.test.annotation.*;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathGeneratorTest {
    Context appContext = null;
    PathGenerator path = null;
    @Before
    public void getContext(){
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        path = new PathGenerator(appContext);
    }
    @Test
    public void correctPlanDuplicate(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("lions","gorillas", "gorillas", "gators","arctic_foxes","gorillas"));
        path.generatePlan(input);
        List<RouteExhibitItem> result = path.getRoute();
        ArrayList<String> names= new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        for(RouteExhibitItem i : result){
            if(i.name != "Entrance Gate"){
                names.add(i.name);
                distances.add(i.distance);
            }
        }
        String[] namesR = names.stream().toArray(String[]::new);
        double[] distancesR = distances.stream().mapToDouble(n->n).toArray();
        double[] exDist = {110,200,200,500};
        String[] expected = {"Alligators", "Lions", "Gorillas", "Arctic Foxes"};
        Assert.assertArrayEquals(expected, namesR);
        for(int k = 0; k < exDist.length; k ++){
            Assert.assertTrue(exDist[k] == distancesR[k]);
        }
    }
    @Test
    public void linearPlan(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("gorillas","lions","elephant_odyssey"));
        path.generatePlan(input);
        List<RouteExhibitItem> result = path.getRoute();
        ArrayList<String> names= new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        for(RouteExhibitItem i : result){
            if(i.name != "Entrance Gate"){
                names.add(i.name);
                distances.add(i.distance);
            }
        }
        String[] namesR = names.stream().toArray(String[]::new);
        double[] distancesR = distances.stream().mapToDouble(n->n).toArray();
        String[] expected = {"Gorillas", "Lions", "Elephant Odyssey"};
        double[] exDist = {210,200,200};
        Assert.assertArrayEquals(expected, namesR);
        for(int k = 0; k < exDist.length; k ++){
            Assert.assertTrue(exDist[k] == distancesR[k]);
        }

    }
}
