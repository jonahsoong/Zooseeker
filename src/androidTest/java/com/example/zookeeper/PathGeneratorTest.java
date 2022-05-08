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
        String[] result = path.getOrder();
        double[] distances = path.getDist();
        double[] exDist = {110,200,200,500};
        String[] expected = {"Alligators", "Lions", "Gorillas", "Arctic Foxes"};
        Assert.assertArrayEquals(expected, result);
        for(int i = 0; i < exDist.length; i ++){
            Assert.assertTrue(exDist[i] == distances[i]);
        }
    }
    @Test
    public void linearPlan(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("gorillas","lions","elephant_odyssey"));
        path.generatePlan(input);
        String[] result = path.getOrder();
        double[] distances = path.getDist();
        String[] expected = {"Gorillas", "Lions", "Elephant Odyssey"};
        double[] exDist = {210,200,200};
        Assert.assertArrayEquals(expected, result);
        for(int i = 0; i < exDist.length; i ++){
            Assert.assertTrue(exDist[i] == distances[i]);
        }

    }
}
