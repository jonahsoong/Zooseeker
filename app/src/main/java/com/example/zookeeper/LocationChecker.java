package com.example.zookeeper;

import android.location.Location;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

public class LocationChecker {
    private RouteViewModel viewModel;


    public LocationChecker(ViewModelStoreOwner owner){
        viewModel = new ViewModelProvider(owner)
                .get(RouteViewModel.class);
    }
    public void updateRoute(double lat, double lng){
        //only remaining items
        List<RouteItem> route = viewModel.getList();
        //next Item
        RouteItem nextItem = null;
        RouteItem closestExhibit = nextItem;
        double closestDistance = Double.MAX_VALUE;
        for(RouteItem r : route){
            double dist = Math.sqrt(Math.pow(lat-r.lat,2) + Math.pow(lng-r.lng,2));
            if(dist < closestDistance){
                closestExhibit = nextItem;
                closestDistance = dist;
            }
        }

        if(!closestExhibit.equals(nextItem)){
            //TODO: pop alert
            //call replan
        }
        //off track but nextItem still the same
        else{
        //            //replan
        }
        //next exhibit(location)
        //locatino of remaining exhibits


    }
}
