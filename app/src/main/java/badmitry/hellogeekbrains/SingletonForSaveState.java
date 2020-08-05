package badmitry.hellogeekbrains;

import java.io.Serializable;

public class SingletonForSaveState implements Serializable {
    private int valueOfTemperature;
    private int valueOfSpeedOfWind;
    private int valueOfPressure;
    private String[] cities = {"Moscow", "Saint-petersburg", "Vidnoe", "Kazan", "Vladivostok", "Kaliningrad",
            "Samara", "Murmansk", "Sochi", "Anapa"};
    private String city;
    private boolean speedWind = false;
    private boolean pressure = false;
    private static SingletonForSaveState instance;

    private SingletonForSaveState() {
    }

    static protected SingletonForSaveState getInstance() {
        if (instance == null) {
            instance = new SingletonForSaveState();
        }
        return instance;
    }

    public int getValueOfPressure() {
        return valueOfPressure;
    }

    protected int getValueOfTemperature() {
        return valueOfTemperature;
    }

    public String[] getCities() {
        return cities;
    }

    protected int getValueOfSpeedOfWind() {
        return valueOfSpeedOfWind;
    }

    protected String getCity() {
        return city;
    }

    protected boolean isSpeedWind() {
        return speedWind;
    }

    protected boolean isPressure() {
        return pressure;
    }

    protected void setValueOfTemperature(int valueOfTemperature) {
        this.valueOfTemperature = valueOfTemperature;
    }

    protected void setValueOfSpeedOfWind(int valueOfSpeedOfWind) {
        this.valueOfSpeedOfWind = valueOfSpeedOfWind;
    }

    protected void setCity(String city) {
        this.city = city;
    }

    protected void setSpeedWind(boolean speedWind) {
        this.speedWind = speedWind;
    }

    protected void setPressure(boolean pressure) {
        this.pressure = pressure;
    }

    public void setValueOfPressure(int valueOfPressure) {
        this.valueOfPressure = valueOfPressure;
    }
}
