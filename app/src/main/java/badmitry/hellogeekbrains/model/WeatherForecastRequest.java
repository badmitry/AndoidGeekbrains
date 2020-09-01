package badmitry.hellogeekbrains.model;

import java.io.Serializable;

public class WeatherForecastRequest implements Serializable {
    private List[] list;

    public List[] getList() {
        return list;
    }

    public void setList(List[] list) {
        this.list = list;
    }
}
