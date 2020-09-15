package badmitry.hellogeekbrains.retrofitForSelectedCity;

import badmitry.hellogeekbrains.model.WeatherRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIWeatherForSelectedCity {
    @GET("/data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String city,
                                     @Query("lang") String lang,
                                     @Query("appid") String appid);
}
