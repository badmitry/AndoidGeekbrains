package badmitry.hellogeekbrains.retrofitForSelectedCity;

import badmitry.hellogeekbrains.model.WeatherRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIWeatherForCurrentCity {
    @GET("/data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("lon") String lon,
                                     @Query("lat") String lat,
                                     @Query("lang") String lang,
                                     @Query("appid") String appid);
}
