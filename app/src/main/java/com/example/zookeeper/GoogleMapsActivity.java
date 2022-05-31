package com.example.zookeeper;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.zookeeper.databinding.ActivityGoogleMapsBinding;

import java.util.Arrays;
import java.util.List;

//This has to run on the background
public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    private final PermissionChecker permissionChecker = new PermissionChecker(this);
    private GoogleMap map;
    private ActivityGoogleMapsBinding binding;
    private List<SearchItem> animals;
    private Location lastVisitedLocation;
    private final ActivityResultLauncher<String[]>requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), perms -> {
        perms.forEach((perm, isGranted) -> {
            Log.i("GoogleMapsActivity", String.format("Permission %s granted:%s", perm, isGranted));
        });
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        var mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        animals = SearchItem.loadJSON(this, "exhibit_info.json");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //permissions setup
//        if(permissionChecker.ensurePermissions())return;

        {
            var requiredPermissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
            var hasNoLocationPerms = Arrays.stream(requiredPermissions)
                    .map(perm -> ContextCompat.checkSelfPermission(this, perm))
                    .allMatch(status -> status == PackageManager.PERMISSION_DENIED);
            if (hasNoLocationPerms) {
                requestPermissionLauncher.launch(requiredPermissions);
                return;
            }

        }

        //listen for location updates
        {
            var checkLoc = new LocationChecker(this);
            var provider = LocationManager.GPS_PROVIDER;
            var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            var locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Log.d("GoogleMapsActivity", String.format("Location changed: %s", location));

                    var lat = location.getLatitude();
                    var lng = location.getLongitude();
                    checkLoc.updateRoute(lat, lng);
                    //DistanceChecker

                }
            };
            locationManager.requestLocationUpdates(provider, 0, 0f, locationListener);
        }

    }
//    private boolean ensurePermissions(){
//        return permissionChecker.ensurePermissions();
//    }

}