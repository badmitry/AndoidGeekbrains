package badmitry.hellogeekbrains;

import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import badmitry.hellogeekbrains.fragments.FragmentWeather;

public class SingletonForSaveState implements Serializable {

    private static SingletonForSaveState instance;
    private String city;
    private boolean selectedCity;
    private boolean showSpeedOfWind;
    private boolean showPressure;
    private String valueOfSpeedOfWind;
    private double valueOfPressure;
    private boolean isDarkTheme;
    private FragmentWeather fragmentWeather;
    private ArrayList<String[]> arrayList = new ArrayList<>();
    private Set<String> history = new HashSet<>();
    private LocationManager locationManager;
    private LatLng latLng;

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public Set<String> getHistory() {
        return history;
    }

    public ArrayList<String[]> getArrayList() {
        return arrayList;
    }

    private SingletonForSaveState(){
    }

    public static SingletonForSaveState getInstance() {
        if (instance == null) {
            instance = new SingletonForSaveState();
        }
        return instance;
    }

    public void setFragmentWeather(FragmentWeather fragmentWeather) {
        this.fragmentWeather = fragmentWeather;
    }

    public FragmentWeather getFragmentWeather() {
        return fragmentWeather;
    }

    public boolean isShowSpeedOfWind() {
        return showSpeedOfWind;
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public String getValueOfSpeedOfWind() {
        return valueOfSpeedOfWind;
    }

    public double getValueOfPressure() {
        return valueOfPressure;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setShowSpeedOfWind(boolean showSpeedOfWind) {
        this.showSpeedOfWind = showSpeedOfWind;
    }

    public void setShowPressure(boolean showPressure) {
        this.showPressure = showPressure;
    }

    public void setValueOfSpeedOfWind(String valueOfSpeedOfWind) {
        this.valueOfSpeedOfWind = valueOfSpeedOfWind;
    }

    public void setValueOfPressure(double valueOfPressure) {
        this.valueOfPressure = valueOfPressure;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

    public void setCity(String city) {
        if (city != null && !city.equals("")) {
            this.city = city;
        }
    }

    public String getCity() {
        return city;
    }

    public boolean isCity() {
        return selectedCity;
    }

    public void setIsCity(boolean selectedCity) {
        this.selectedCity = selectedCity;
    }
}
