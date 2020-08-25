package badmitry.hellogeekbrains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import badmitry.hellogeekbrains.fragments.FragmentWeather;

public class SingletonForSaveState implements Serializable {

    private static SingletonForSaveState instance;
    private String city;
    private boolean showSpeedOfWind;
    private boolean showPressure;
    private double valueOfSpeedOfWind;
    private double valueOfPressure;
    private boolean isDarkTheme;
    private FragmentWeather fragmentWeather;
    private WeatherFromInternet weatherFromInternet;
    private ArrayList<String[]> arrayList = new ArrayList<>();
    private Set<String> history = new HashSet<>();

    public Set<String> getHistory() {
        return history;
    }

    public ArrayList<String[]> getArrayList() {
        return arrayList;
    }

    private SingletonForSaveState() {
        weatherFromInternet = new WeatherFromInternet(this);
    }

    public static SingletonForSaveState getInstance() {
        if (instance == null) {
            instance = new SingletonForSaveState();
        }
        return instance;
    }

    public WeatherFromInternet getWeatherFromInternet() {
        return weatherFromInternet;
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

    public double getValueOfSpeedOfWind() {
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

    public void setValueOfSpeedOfWind(double valueOfSpeedOfWind) {
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
        return city != null;
    }
}
