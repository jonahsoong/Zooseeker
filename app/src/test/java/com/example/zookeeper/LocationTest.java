package com.example.zookeeper;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationTest {
    @Test
    public void checkUpdateLocation(){
        LatLng newLoc = new LatLng(17, -117);
        LatLng oldLoc = LocationChecker.updateLocation(newLoc);
        assertEquals(LocationChecker.getLocation(), newLoc);
        LocationChecker.updateLocation(oldLoc);
    }
    @Test
    public void closestLocationChecker(){
        List<String> remainingExhibits = Arrays.asList("parker_aviary", "fern_canyon");
        List<LatLng> exhibitCoords = new ArrayList<>();
        exhibitCoords.add(new LatLng(32.73870593465047,-117.1695850705821));
        exhibitCoords.add(new LatLng(32.7337949159672,-117.1769866067953));

        LatLng newLoc = new LatLng(32.73870593465047,-117.1695850705821);
        LatLng oldLoc = LocationChecker.updateLocation(newLoc);

        assertEquals("parker_aviary", LocationChecker.updateRoute(remainingExhibits, exhibitCoords));

        newLoc = new LatLng(32.7337949159672,-117.1769866067953);
        LocationChecker.updateLocation(newLoc);

        assertEquals("fern_canyon", LocationChecker.updateRoute(remainingExhibits, exhibitCoords));
    }
}
