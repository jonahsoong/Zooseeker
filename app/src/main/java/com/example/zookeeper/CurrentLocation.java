package com.example.zookeeper;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CurrentLocation extends Application {
        private PathGenerator pg;
        private LatLng currentLocation = new LatLng(32.73459618734685,-117.14936);

        public LatLng getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(LatLng currentLocation) {
            this.currentLocation = currentLocation;
            ArrayList<RouteExhibitItem> remaining = pg.getRemaining();
            ArrayList<String> names = new ArrayList<>();
            for(RouteExhibitItem r : remaining){
                names.add(r.source);
            }
            ArrayList<LatLng> loc = pg.getLocations(names);
            LocationChecker check = new LocationChecker(currentLocation);

            names.add(0,check.updateRoute(names,loc));
            pg.generatePlan(names);
        }

        public void setPathGenerator(PathGenerator pg) {
            this.pg = pg;
        }

        public PathGenerator getPathGenerator(){
            return pg;
        }
    }

