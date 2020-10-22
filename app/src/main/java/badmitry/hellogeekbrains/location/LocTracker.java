package badmitry.hellogeekbrains.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import badmitry.hellogeekbrains.SingletonForSaveState;

public class LocTracker {

    Context context;
    private Location loc = null;
    private LocListener locationListener = null;
    private SingletonForSaveState singletonForSaveState;
    private boolean permission;

    public LocTracker(Context context) {
        this.context = context;
        singletonForSaveState = SingletonForSaveState.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            permission = false;
        } else {
            permission = true;
        }
    }

    public boolean isPermission() {
        return permission;
    }

    @SuppressLint("MissingPermission")
    public Location takeLocation() {
        if (locationListener == null) {
            locationListener = new LocListener();
        }
        LocationManager mLocManager = singletonForSaveState.getLocationManager();
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1000, locationListener);
        try {
            loc = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loc == null) {
            return takeLocation();
        } else {
            return loc;
        }
    }
}
