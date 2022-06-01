package com.example.zookeeper;

import android.location.Location;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocationChecker {
    private static LatLng current = new LatLng(32.73459618734685,-117.14936);;


    public LocationChecker(){
    }
    public static String updateRoute(List<String> remaining, List<LatLng> coordinates){

        String closestExhibit = "";
        double closestDistance = Double.MAX_VALUE;
        for(int i = 0; i < remaining.size(); i++){
            String s = remaining.get(i);
            double dist = Math.sqrt(Math.pow(current.latitude-coordinates.get(i).latitude,2)
                    + Math.pow(current.longitude-coordinates.get(i).longitude,2));
            if(dist < closestDistance){
                closestExhibit = s;
                closestDistance = dist;
            }
        }
        return closestExhibit;

    }
    public static LatLng updateLocation(LatLng newCoord){
        LatLng oldLoc = current;
        current = newCoord;
        return oldLoc;
    }

    public static LatLng getLocation(){
        return current;
    }
}
