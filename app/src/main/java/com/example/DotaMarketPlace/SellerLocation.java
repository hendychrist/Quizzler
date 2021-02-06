package com.example.DotaMarketPlace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.DotaMarketPlace.Kontroller.MenuForm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class SellerLocation extends AppCompatActivity implements OnMapReadyCallback {

    ImageView imgBack;
    GoogleMap map;
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerlocation);

        Bundle bundle = getIntent().getBundleExtra("Item");
        final Item items;
        items = (Item) bundle.getSerializable("Item");

        lat = Double.parseDouble(items.getLat());
        lng = Double.parseDouble(items.getLng());

        SupportMapFragment supportMapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng maps = new LatLng(lat,lng);

        map.addMarker(new MarkerOptions().position(maps).title("location"));

        map.moveCamera(CameraUpdateFactory.newLatLng(maps));
       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maps,10f));
    }

}
