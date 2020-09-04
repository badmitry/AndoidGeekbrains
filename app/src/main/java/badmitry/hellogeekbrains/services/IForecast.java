package badmitry.hellogeekbrains.services;

import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IForecast {
    @GET("/data/2.5/forecast")
    Call<WeatherForecastRequest> loadWeather(@Query("q") String city,
                                             @Query("lang") String lang,
                                             @Query("appid") String appid);
}
