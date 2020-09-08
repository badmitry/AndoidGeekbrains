package badmitry.hellogeekbrains.services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .client(okHttpClient)
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
