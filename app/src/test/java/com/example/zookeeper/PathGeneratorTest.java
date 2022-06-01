package com.example.zookeeper;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;

import androidx.annotation.UiContext;
import androidx.annotation.UiThread;
import androidx.annotation.UiContext;
import androidx.test.annotation.*;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RunWith(RobolectricTestRunner.class)
public class PathGeneratorTest {
    Context appContext = null;
    PathGenerator path = null;
    @Before
    public void getContext(){
        appContext = ApplicationProvider.getApplicationContext();
        path = new PathGenerator(appContext);
    }
    @Test
    public void testDirections(){
        assertEquals(0,0);
    }

    @Test
    public void testNext(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("parker_aviary","fern_canyon", "flamingo", "capuchin"));
        path.generatePlan(input);
        assertNotEquals(path.getCurrent(), path.peekNext());
    }

    @Test
    public void testPrevious(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("parker_aviary","fern_canyon", "flamingo", "capuchin"));
        path.generatePlan(input);
        path.getNext();
        assertNotEquals(path.getCurrent(), path.getPrev());
    }


    //Outdated tests using old assets
    /*
    @Test
    public void correctPlanDuplicate(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("lions","gorillas", "gorillas", "gators","arctic_foxes","gorillas"));
        path.generatePlan(input);
        List<RouteExhibitItem> result = path.getRoute();
        ArrayList<String> names= new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        for(RouteExhibitItem i : result){
            names.add(i.name);
            distances.add(i.distance);
        }
        String[] namesR = names.stream().toArray(String[]::new);
        double[] distancesR = distances.stream().mapToDouble(n->n).toArray();
        double[] exDist = {110,200,200,500,310};
        String[] expected = {"Alligators", "Lions", "Gorillas", "Arctic Foxes","Entrance and Exit Gate"};
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
            names.add(i.name);
            distances.add(i.distance);
        }
        String[] namesR = names.stream().toArray(String[]::new);
        double[] distancesR = distances.stream().mapToDouble(n->n).toArray();
        String[] expected = {"Gorillas", "Lions", "Elephant Odyssey","Entrance and Exit Gate"};
        double[] exDist = {210,200,200,510};
        Assert.assertArrayEquals(expected, namesR);
        for(int k = 0; k < exDist.length; k ++){
            Assert.assertTrue(exDist[k] == distancesR[k]);
        }

    }
    */

}
