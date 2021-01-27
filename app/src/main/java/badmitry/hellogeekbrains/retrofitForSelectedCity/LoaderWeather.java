package badmitry.hellogeekbrains.retrofitForSelectedCity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Calendar;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import badmitry.hellogeekbrains.model.WeatherRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoaderWeather {
    SingletonForSaveState singletonForSaveState;

    public LoaderWeather() {
        this.singletonForSaveState = SingletonForSaveState.getInstance();
    }

    public void reloadWeatherForSelectedCity() {
        WeatherRepo weatherRepo = WeatherRepo.getInstance();
        singletonForSaveState.getArrayList().clear();
        weatherRepo.getApiWeatherForSelectedCity().loadWeather(singletonForSaveState.getCity(),
                singletonForSaveState.getFragmentWeather().getString(R.string.lang),
                "94b18bc70bd073ad490a67a7c6ceb146").enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(@NonNull Call<WeatherRequest> call, @NonNull Response<WeatherRequest> response) {
                if (response.body() != null && response.isSuccessful()) {
                    WeatherRequest weatherRequest = response.body();
                    singletonForSaveState.setValueOfPressure((double) 3 / 4 * weatherRequest.main.pressure);
                    @SuppressLint("DefaultLocale") String speedOfWind = String.format("%2.1f", weatherRequest.wind.speed);
                    singletonForSaveState.setValueOfSpeedOfWind(speedOfWind);
                    @SuppressLint("DefaultLocale") String temp = String.format("%2.1f", (weatherRequest.main.temp - 273.5));
                    singletonForSaveState.getArrayList().add(new String[]{temp, weatherRequest.weather[0].icon});
                    weatherRepo.getApiForecastForSelectedCity().loadWeather(singletonForSaveState.getCity(),
                            singletonForSaveState.getFragmentWeather().getString(R.string.lang),
                            "94b18bc70bd073ad490a67a7c6ceb146").enqueue(new Callback<WeatherForecastRequest>() {
                        @Override
                        public void onResponse(@NonNull Call<WeatherForecastRequest> call, @NonNull Response<WeatherForecastRequest> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                WeatherForecastRequest weatherForecastRequest = response.body();

                                final int[] unixData = new int[4];
                                Calendar calendar = Calendar.getInstance();
                                for (int i = 0; i < 4; i++) {
                                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
                                    unixData[i] = (int) (calendar1.getTimeInMillis() / 1000);
                                }
                                for (int unixDatum : unixData) {
                                    for (int j = 0; j < weatherForecastRequest.list.length; j++) {
                                        if (weatherForecastRequest.list[j].dt == unixDatum) {
                                            @SuppressLint("DefaultLocale") String tempString =
                                                    String.format("%2.1f", (weatherForecastRequest.list[j].main.temp - 273.5));
                                            singletonForSaveState.getArrayList().add(new String[]{tempString,
                                                    weatherForecastRequest.list[j].weather[0].icon});
                                        }
                                    }
                                }
                                singletonForSaveState.getFragmentWeather().showWeather(singletonForSaveState.getCity());
                                singletonForSaveState.getFragmentWeather().drawThermometer();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<WeatherForecastRequest> call, @NonNull Throwable t) {
                            alertAboutConnectionFailed();
                            singletonForSaveState.getFragmentWeather().showWeather(singletonForSaveState.getCity());
                            singletonForSaveState.getFragmentWeather().drawThermometer();
                        }
                    });
                } else {
                    alertAboutWrongCity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherRequest> call, @NonNull Throwable t) {
                alertAboutConnectionFailed();
                singletonForSaveState.getFragmentWeather().showWeather(singletonForSaveState.getCity());
                singletonForSaveState.getFragmentWeather().drawThermometer();
            }
        });
    }

    public void reloadWeatherForCurrentCity(String lon, String lat) {
        WeatherRepo weatherRepo = WeatherRepo.getInstance();
        singletonForSaveState.getArrayList().clear();
        weatherRepo.getApiWeatherForCurrentCity().loadWeather(lon, lat,
                singletonForSaveState.getFragmentWeather().getString(R.string.lang),
                "94b18bc70bd073ad490a67a7c6ceb146").enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(@NonNull Call<WeatherRequest> call, @NonNull Response<WeatherRequest> response) {
                if (response.body() != null && response.isSuccessful()) {
                    WeatherRequest weatherRequest = response.body();
                    singletonForSaveState.setValueOfPressure((double) 3 / 4 * weatherRequest.main.pressure);
                    @SuppressLint("DefaultLocale") String speedOfWind = String.format("%2.1f", weatherRequest.wind.speed);
                    singletonForSaveState.setValueOfSpeedOfWind(speedOfWind);
                    @SuppressLint("DefaultLocale") String temp = String.format("%2.1f", (weatherRequest.main.temp - 273.5));
                    singletonForSaveState.getArrayList().add(new String[]{temp, weatherRequest.weather[0].icon});
                    weatherRepo.getApiForecastForCurrentCity().loadWeather(lon, lat,
                            singletonForSaveState.getFragmentWeather().getString(R.string.lang),
                            "94b18bc70bd073ad490a67a7c6ceb146").enqueue(new Callback<WeatherForecastRequest>() {
                        @Override
                        public void onResponse(@NonNull Call<WeatherForecastRequest> call, @NonNull Response<WeatherForecastRequest> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                WeatherForecastRequest weatherForecastRequest = response.body();

                                final int[] unixData = new int[4];
                                Calendar calendar = Calendar.getInstance();
                                for (int i = 0; i < 4; i++) {
                                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
                                    unixData[i] = (int) (calendar1.getTimeInMillis() / 1000);
                                }
                                for (int unixDatum : unixData) {
                                    for (int j = 0; j < weatherForecastRequest.list.length; j++) {
                                        if (weatherForecastRequest.list[j].dt == unixDatum) {
                                            @SuppressLint("DefaultLocale") String tempString =
                                                    String.format("%2.1f", (weatherForecastRequest.list[j].main.temp - 273.5));
                                            singletonForSaveState.getArrayList().add(new String[]{tempString,
                                                    weatherForecastRequest.list[j].weather[0].icon});
                                        }
                                    }
                                }
                                singletonForSaveState.getFragmentWeather().showWeather(weatherRequest.name);
                                singletonForSaveState.getFragmentWeather().drawThermometer();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<WeatherForecastRequest> call, @NonNull Throwable t) {
                            alertAboutConnectionFailed();
                            singletonForSaveState.getFragmentWeather().showWeather(weatherRequest.name);
                            singletonForSaveState.getFragmentWeather().drawThermometer();
                        }
                    });
                } else {
                    alertAboutWrongCity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherRequest> call, @NonNull Throwable t) {
                alertAboutConnectionFailed();
                singletonForSaveState.getFragmentWeather().showWeather(singletonForSaveState.getCity());
                singletonForSaveState.getFragmentWeather().drawThermometer();
            }
        });
    }

    public void alertAboutConnectionFailed() {
        singletonForSaveState.getFragmentWeather().requireActivity();
        singletonForSaveState.getFragmentWeather().requireActivity().runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(singletonForSaveState.getFragmentWeather().getActivity());
            builder.setTitle(R.string.warning)
                    .setMessage(R.string.connection_failed)
                    .setIcon(R.mipmap.weather)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok,
                            (dialog, id) -> Log.d("netConnection", singletonForSaveState.getFragmentWeather().getString(R.string.connection_failed)));
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public void alertAboutWrongCity() {
        singletonForSaveState.getFragmentWeather().requireActivity();
        singletonForSaveState.getFragmentWeather().requireActivity().runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(singletonForSaveState.getFragmentWeather().getActivity());
            builder.setTitle(R.string.warning)
                    .setMessage(R.string.wrong_city)
                    .setIcon(R.mipmap.weather)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok,
                            (dialog, id) -> Log.d("netConnection", singletonForSaveState.getFragmentWeather().getString(R.string.connection_failed)));
            AlertDialog alert = builder.create();
            alert.show();
        });
    }
}
