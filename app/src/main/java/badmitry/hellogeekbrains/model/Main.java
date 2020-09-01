package badmitry.hellogeekbrains.model;

import java.io.Serializable;

public class Main implements Serializable {
    private float temp;
    private int pressure;

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public float getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }
}
