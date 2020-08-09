package badmitry.hellogeekbrains;

import java.io.Serializable;

import badmitry.hellogeekbrains.sampledata.FragmentWeather;

public class SingletonForSaveState implements Serializable {

    private String city;
    private int isRain;
    private boolean showSpeedOfWind;
    private boolean showPressure;
    private int valueOfTemperature;
    private int valueOfSpeedOfWind;
    private int valueOfPressure;
    private boolean isDarkTheme;
    private FragmentWeather fragmentWeather;

    public void setFragmentWeather(FragmentWeather fragmentWeather) {
        this.fragmentWeather = fragmentWeather;
    }

    public FragmentWeather getFragmentWeather() {
        return fragmentWeather;
    }

    public int getIsRain() {
        return isRain;
    }

    public boolean isShowSpeedOfWind() {
        return showSpeedOfWind;
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public int getValueOfTemperature() {
        return valueOfTemperature;
    }

    public int getValueOfSpeedOfWind() {
        return valueOfSpeedOfWind;
    }

    public int getValueOfPressure() {
        return valueOfPressure;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setIsRain(int isRain) {
        this.isRain = isRain;
    }

    public void setShowSpeedOfWind(boolean showSpeedOfWind) {
        this.showSpeedOfWind = showSpeedOfWind;
    }

    public void setShowPressure(boolean showPressure) {
        this.showPressure = showPressure;
    }

    public void setValueOfTemperature(int valueOfTemperature) {
        this.valueOfTemperature = valueOfTemperature;
    }

    public void setValueOfSpeedOfWind(int valueOfSpeedOfWind) {
        this.valueOfSpeedOfWind = valueOfSpeedOfWind;
    }

    public void setValueOfPressure(int valueOfPressure) {
        this.valueOfPressure = valueOfPressure;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

    private static SingletonForSaveState instance;

    public void setCity(String city) {
        if (city != null && !city.equals("")) {
            this.city = city;
        }
    }

    public String getCity() {
        return city;
    }

    private SingletonForSaveState() {
    }

    public static SingletonForSaveState getInstance() {
        if (instance == null) {
            instance = new SingletonForSaveState();
        }
        return instance;
    }

    public boolean isCity() {
        return city != null;
    }
}
