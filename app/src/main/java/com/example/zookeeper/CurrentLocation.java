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
            ArrayList<String> remaining = pg.getRemaining();
            ArrayList<LatLng> loc = pg.getLocations(pg.getRemaining());
            LocationChecker check = new LocationChecker(currentLocation);
            remaining.add(0,check.updateRoute(pg.getRemaining(),loc));
            pg.generatePlan(remaining);
        }

        public void setPathGenerator(PathGenerator pg) {
            this.pg = pg;
        }

        public PathGenerator getPathGenerator(){
            return pg;
        }
    }

