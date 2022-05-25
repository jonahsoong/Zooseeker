package com.example.zookeeper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Location Services", "Tracking Location");
        // If we get killed, after returning from here, restart
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
