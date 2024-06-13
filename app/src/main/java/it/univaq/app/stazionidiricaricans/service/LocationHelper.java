package it.univaq.app.stazionidiricaricans.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

public class LocationHelper {

    //ho bisogno del contesto per poter recuperare la posizione
    public static void start(Context context, LocationListener listener) {
        int fineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (fineLocationPermission == PackageManager.PERMISSION_GRANTED && coarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            //prendo la posizione sia dal gps e dalla rete
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, listener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, listener);

        }
    }

    public static void stop(Context context, LocationListener listener) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(listener);
    }
}
