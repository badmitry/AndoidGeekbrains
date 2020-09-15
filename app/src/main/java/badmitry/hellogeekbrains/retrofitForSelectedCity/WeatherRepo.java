package badmitry.hellogeekbrains.retrofitForSelectedCity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepo {
    private static WeatherRepo instance = null;
    private APIWeatherForSelectedCity apiWeatherForSelectedCity;
    private APIForecastForSelectedCity apiForecastForSelectedCity;
    private APIForecastForCurrentCity apiForecastForCurrentCity;
    private APIWeatherForCurrentCity apiWeatherForCurrentCity;

    public static WeatherRepo getInstance() {
        if (instance == null) {
            instance = new WeatherRepo();
        }
        return instance;
    }

    private WeatherRepo() {
        apiWeatherForSelectedCity = createAdapterWeatherForSelectedCity();
        apiForecastForSelectedCity = createAdapterForecastForSelectedCity();
        apiForecastForCurrentCity = createAdapterForecastForCurrentCity();
        apiWeatherForCurrentCity = createAdapterWeatherForCurrentCity();
    }

    public APIWeatherForSelectedCity getApiWeatherForSelectedCity() {
        return apiWeatherForSelectedCity;
    }

    public APIForecastForSelectedCity getApiForecastForSelectedCity() {
        return apiForecastForSelectedCity;
    }

    public APIWeatherForCurrentCity getApiWeatherForCurrentCity() {
        return apiWeatherForCurrentCity;
    }

    public APIForecastForCurrentCity getApiForecastForCurrentCity() {
        return apiForecastForCurrentCity;
    }

    private APIForecastForSelectedCity createAdapterForecastForSelectedCity() {
        return buildRetrofit().create(APIForecastForSelectedCity.class);
    }

    private APIWeatherForSelectedCity createAdapterWeatherForSelectedCity() {
        return buildRetrofit().create(APIWeatherForSelectedCity.class);
    }

    private APIForecastForCurrentCity createAdapterForecastForCurrentCity() {
        return buildRetrofit().create(APIForecastForCurrentCity.class);
    }

    private APIWeatherForCurrentCity createAdapterWeatherForCurrentCity() {
        return buildRetrofit().create(APIWeatherForCurrentCity.class);
    }

    private Retrofit buildRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
