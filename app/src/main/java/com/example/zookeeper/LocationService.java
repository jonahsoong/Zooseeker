package com.example.zookeeper;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

public class LocationService extends Service {
    private final LocationPermissionChecker permissionChecker = new LocationPermissionChecker(new SearchActivity());
    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (permissionChecker.ensurePermissions()) return START_STICKY;

        Log.d("Location Services", "Tracking Location");
        // If we get killed, after returning from here, restart
        var provider = LocationManager.GPS_PROVIDER;
        var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        var locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("GoogleMapsActivity", String.format("Location changed: %s", location));

                var lat = location.getLatitude();
                var lng = location.getLongitude();
                //checkLoc.updateRoute(lat, lng);
                //DistanceChecker

            }
        };
        locationManager.requestLocationUpdates(provider, 0, 0f, locationListener);
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("Location Shutting Down", "service done");
    }
}
