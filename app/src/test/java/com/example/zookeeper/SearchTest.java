package com.example.zookeeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.view.Menu;
import android.view.MenuItem;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    @Rule
    public ActivityScenarioRule<SearchActivity> scenarioRule = new ActivityScenarioRule<>(SearchActivity.class);

    @Test
    public void testLoadJson() {
        List<String> name = new ArrayList<>();
        name.add("Entrance and Exit Gate");
        name.add("Koi Fish");
        name.add("Flamingos");
        name.add("Capuchin Monkeys");
        name.add("Gorillas");
        name.add("Orangutans");
        name.add("Siamangs");
        name.add("Fern Canyon");
        name.add("Toucan");
        name.add("Blue Capped Motmot");
        name.add("Spoonbill");
        name.add("Hippos");
        name.add("Crocodiles");
        name.add("Bali Mynah");
        name.add("Emerald Dove");

        // Create a "scenario" to move through the activity lifecycle.
        // https://developer.android.com/guide/components/activities/activity-lifecycle
        ActivityScenario<SearchActivity> scenario = scenarioRule.getScenario();

        // Make sure the activity is in the created state (so onCreated is called).
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        // When it's ready, we're ready to test inside this lambda (anonymous inline function).
        scenario.onActivity(activity -> {

            for(String n : name) {
                System.out.println(n);
                assertEquals(true, activity.name.contains(n));
            }
            // No calculations have been run yet, so there shouldn't be a result!
        });
    }
//    @Test
//    public void testSearch(){
//        //use robolectric as dependent on clicking yes on alerts
//        assertEquals(0,0);
//    }
    //Outdated test using old assets


}
