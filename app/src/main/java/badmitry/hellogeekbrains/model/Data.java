package badmitry.hellogeekbrains.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {
    @SerializedName("dt") public double dt;
    @SerializedName("wind") public Wind wind;
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
}
