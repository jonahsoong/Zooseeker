package com.example.zookeeper;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;

public class LocationTest {
    @Rule
    public ActivityScenarioRule<GoogleMapsActivity> scenarioRule = new ActivityScenarioRule<>(GoogleMapsActivity.class);


}
