package com.dartart.directionsapi;

import android.app.DownloadManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dartart.directionsapi.DirectionsManager.GetRequestManager;
import com.dartart.directionsapi.DirectionsManager.RequestManager;
import com.dartart.directionsapi.DirectionsManager.ResponseManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng loc1 = new LatLng(3.835797, 11.484172);
        LatLng loc = new LatLng(3.875018, 11.512522);
        mMap.addMarker(new MarkerOptions().position(loc1).title("Dovv"));
        mMap.addMarker(new MarkerOptions().position(loc).title("XYZ"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
        final  String url = RequestManager.createUrlFromSrcDesLatLng(3.835797, 11.484172,3.875018, 11.512522);
        new Thread(new Runnable() {
            @Override
            public void run() {
              final String resp =   GetRequestManager.getResponse(url);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(GetRequestManager.checkIfErrorFromMessage(resp)){
                            Toast.makeText(MapsActivity.this, GetRequestManager.decodeErrorMessage(resp), Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                ResponseManager.drawOnMap(resp,googleMap);
                            } catch (JSONException e) {
                                Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
