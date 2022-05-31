package com.example.zookeeper;

import android.location.Location;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocationChecker {
    private LatLng current;


    public LocationChecker(LatLng current){
        this.current = current;
    }
    public String updateRoute(ArrayList<String> remaining, ArrayList<LatLng> coordinates){

        String closestExhibit = "";
        double closestDistance = Double.MAX_VALUE;
        int i = 0;
        for(String s : remaining){
            double dist = Math.sqrt(Math.pow(current.latitude-coordinates.get(i).latitude,2)
                    + Math.pow(current.longitude-coordinates.get(i).longitude,2));
            if(dist < closestDistance){
                closestExhibit = s;
                closestDistance = dist;
            }
        }
        return closestExhibit;



    }
}
