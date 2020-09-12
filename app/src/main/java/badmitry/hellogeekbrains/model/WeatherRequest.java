package badmitry.hellogeekbrains.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherRequest implements Serializable {
    @SerializedName("wind") public Wind wind;
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("cod") public String cod;
}
