package badmitry.hellogeekbrains;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class LocListener implements LocationListener {
    private SingletonForSaveState singletonForSaveState;

    /**
     *  Called when the location has changed.
     * */
    @Override
    public void onLocationChanged(Location location) {
        Log.d("111", "onLocationChanged: " + location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { /* Empty */ }
    @Override
    public void onProviderEnabled(@NotNull String provider) { /* Empty */ }
    @Override
    public void onProviderDisabled(@NotNull String provider) { /* Empty */ }
}