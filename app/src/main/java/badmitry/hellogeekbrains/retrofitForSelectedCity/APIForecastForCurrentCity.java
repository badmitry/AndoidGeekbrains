package badmitry.hellogeekbrains.retrofitForSelectedCity;

import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIForecastForCurrentCity {
    @GET("/data/2.5/forecast")
    Call<WeatherForecastRequest> loadWeather(@Query("lon") String lon,
                                             @Query("lat") String lat,
                                             @Query("lang") String lang,
                                             @Query("appid") String appid);
}
