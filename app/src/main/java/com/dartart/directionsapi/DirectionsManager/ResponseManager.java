package com.dartart.directionsapi.DirectionsManager;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codename-tkc on 27/03/2020.
 */

public class ResponseManager {
    private static List<LatLng> decoder(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static void drawOnMap(String result, GoogleMap mMap) throws JSONException {
        final JSONObject json = new JSONObject(result);
        JSONArray routeArray = json.getJSONArray("routes");
        JSONObject routes = routeArray.getJSONObject(0);
        JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
        String encodedString = overviewPolylines.getString("points");
        List<LatLng> list = decoder(encodedString);
        mMap.addPolyline(new PolylineOptions()
                .addAll(list)
                .width(12)
                .color(Color.parseColor("#ffb1fb"))//Google maps blue color
                .geodesic(true)
        );
    }
}