package badmitry.hellogeekbrains.services;

import badmitry.hellogeekbrains.model.WeatherRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeather {
    @GET("/data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String city,
                                     @Query("lang") String lang,
                                     @Query("appid") String appid);
}
