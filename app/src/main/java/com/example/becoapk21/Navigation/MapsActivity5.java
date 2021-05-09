package com.example.becoapk21.Navigation;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.becoapk21.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity5 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps5);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //mMap.getMapType(GoogleMap.MAP_TYPE_HYBRID); //for satellite view
        //enable to user to zoom in and zoom out from the screen
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Add a marker in haifa and move the camera
        LatLng haifa = new LatLng(32.796695, 35.0096217);
        LatLng haifa1 = new LatLng(32.8007352, 35.0127974);
        LatLng haifa2 = new LatLng(32.804323, 35.010142);

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(haifa).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(haifa1).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(haifa, 16));

        //draw route between two locations/points   32.797755, 35.017058
        Polyline line = mMap.addPolyline(new PolylineOptions().add(haifa, new LatLng(32.799020, 35.014927)).width(25).color(Color.BLUE).geodesic(true));
        Polyline line1 = mMap.addPolyline(new PolylineOptions().add(haifa1, new LatLng(32.801482, 35.011000)).width(25).color(Color.BLUE).geodesic(true));
        Polyline line2 = mMap.addPolyline(new PolylineOptions().add(haifa2, new LatLng(32.804323, 35.010142)).width(25).color(Color.BLUE).geodesic(true));
        //32.798576, 35.019687
//        Polyline line1 = mMap.addPolyline(new PolylineOptions().add(haifa1, new LatLng(32.8007352, 35.0127974)).width(25).color(Color.RED).geodesic(true));

        //put a mark where user click on map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                Toast.makeText(MapsActivity5.this, latLng.latitude + "," + latLng.longitude, Toast.LENGTH_SHORT).show();
//
//                mMap.clear();//remove previous marker
////                mMap.addMarker(new MarkerOptions().position(latLng).title("Haifa").icon(BitmapDescriptorFactory.fromResource(R.drawable.help_icon))).showInfoWindow();
//            }
//        });

    }
}