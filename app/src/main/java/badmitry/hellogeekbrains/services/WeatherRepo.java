package badmitry.hellogeekbrains.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepo {
    private static WeatherRepo instance = null;
    private IWeather APIWeather;
    private IForecast APIForecast;

    public static WeatherRepo getInstance() {
        if (instance == null) {
            instance = new WeatherRepo();
        }
        return instance;
    }

    private WeatherRepo() {
        APIWeather = createAdapterWeather();
        APIForecast = createAdapterForecast();
    }

    public IWeather getAPIWeather() {
        return APIWeather;
    }

    public IForecast getAPIForecast() {
        return APIForecast;
    }

    private IForecast createAdapterForecast() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(IForecast.class);
    }

    private IWeather createAdapterWeather() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(IWeather.class);
    }
}
