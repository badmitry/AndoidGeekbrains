package badmitry.hellogeekbrains.model;

public class WeatherRequest {
    private Wind wind;
    private Main main;
    private Weather[] weather;

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWeathers(Weather[] weathers) {
        this.weather = weathers;
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }
}
