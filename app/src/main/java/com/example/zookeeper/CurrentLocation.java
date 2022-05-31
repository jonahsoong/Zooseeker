package com.example.zookeeper;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

public class CurrentLocation extends Application {
        private PathGenerator pg;
        private LatLng currentLocation = new LatLng(32.73459618734685,-117.14936);

        public LatLng getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(LatLng currentLocation) {
            this.currentLocation = currentLocation;
        }

        public void setPathGenerator(PathGenerator pg) {
            this.pg = pg;
        }

        public PathGenerator getPathGenerator(){
            return pg;
        }
    }

