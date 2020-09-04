package badmitry.hellogeekbrains.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherForecastRequest implements Serializable {
    @SerializedName("list") public Data[] list;
}
