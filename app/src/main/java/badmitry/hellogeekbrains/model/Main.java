package badmitry.hellogeekbrains.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {
    @SerializedName("temp") public float temp;
    @SerializedName("pressure") public int pressure;
}
