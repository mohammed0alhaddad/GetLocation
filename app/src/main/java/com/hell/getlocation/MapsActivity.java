package com.hell.getlocation;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User-101");
    Marker myMarker;

    ValueEventListener listener= databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
           Double latitude= snapshot.child("latitude").getValue(Double.class);
           Double longitude=snapshot.child("longitude").getValue(Double.class);
            Toast.makeText(MapsActivity.this,latitude.toString(),Toast.LENGTH_LONG).show();



           LatLng location = new LatLng(latitude,longitude);
           // myMarker.setPosition(location);
            myMarker=mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
            try {
                if (location != null) {
                    myMarker.setPosition(location);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14f));
                }
            }catch (Exception e){
                Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();


            }

          //  mMap.addMarker(new MarkerOptions().position(location).title("Marker"));


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Sydney and move the camera

    }
}