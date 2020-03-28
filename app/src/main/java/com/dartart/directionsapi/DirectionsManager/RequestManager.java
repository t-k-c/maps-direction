package com.dartart.directionsapi.DirectionsManager;


/**
 * Created by codename-tkc on 27/03/2020.
 */

public class RequestManager {
    public static String createUrlFromSrcDesLatLng( double sourcelatitiude,double sourcelongitude, double destlatitude, double destlongitude) {
        return  new StringBuilder()
                .append("https://maps.googleapis.com/maps/api/directions/json")
                .append("?origin=")
                .append(Double.toString(sourcelatitiude))
                .append(",")
                .append(Double.toString(sourcelongitude))
                .append("&destination=")
                .append(Double.toString(destlatitude))
                .append(",")
                .append(Double.toString(destlongitude))
                .append("&sensor=false&mode=driving&alternatives=true")
                .append("&key=AIzaSyD8rUeTzJj27joBjm-bS583ESgmPhi-Inc").toString();
    }
}
