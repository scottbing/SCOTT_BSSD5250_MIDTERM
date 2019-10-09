package com.example.sb_bssd5250_midterm;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapWithMarker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_with_marker);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near latitude and longitude
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // get coordinates
        Bundle b = getIntent().getExtras();
        double lat = b.getDouble("latitude");
        double lon = b.getDouble("longitude");

        // convert values for display
        String latitude = String.valueOf(lat);
        String longitude = String.valueOf(lon);

        // Add a marker at the requested latitude and longitude and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        LatLng latlon = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latlon).title("Marker at: " + latitude + ", " + longitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlon));
    }
}
