package badmitry.hellogeekbrains;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Calendar;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import badmitry.hellogeekbrains.model.WeatherRequest;

public class WeatherFromInternet {

    SingletonForSaveState singletonForSaveState;

    public WeatherFromInternet(SingletonForSaveState singletonForSaveState) {
        this.singletonForSaveState = singletonForSaveState;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateCurrentWeather() {
        try {
            final URL uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + singletonForSaveState.getCity() + "&lang=ru&appid=94b18bc70bd073ad490a67a7c6ceb146");
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setConnectTimeout(2000);
                        urlConnection.setReadTimeout(2000);
                        BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(input);
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        handler.post(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                singletonForSaveState.setValueOfPressure((double) 3 / 4 * weatherRequest.getMain().getPressure());
                                singletonForSaveState.setValueOfSpeedOfWind(weatherRequest.getWind().getSpeed());
                                @SuppressLint("DefaultLocale") String temp = String.format("%2.1f", (weatherRequest.getMain().getTemp() - 273.5));
                                singletonForSaveState.getArrayList().clear();
                                singletonForSaveState.getArrayList().add(new String[]{temp,
                                        weatherRequest.getWeather()[0].getIcon()});
                                updateForecastWeather();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        exceptionCatch();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateForecastWeather() {
        final int[] unixData = new int[4];
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 4; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
            unixData[i] = (int) (calendar1.getTimeInMillis() / 1000);
        }
        try {
            final URL uri = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + singletonForSaveState.getCity() + "&appid=c0c4a4b4047b97ebc5948ac9c48c0559");
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(input);
                        Gson gson = new Gson();
                        final WeatherForecastRequest weatherForecastRequest = gson.fromJson(result, WeatherForecastRequest.class);
                        handler.post(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                for (int unixDatum : unixData) {
                                    for (int j = 0; j < weatherForecastRequest.getList().length; j++) {
                                        if (weatherForecastRequest.getList()[j].getDt() == unixDatum) {
                                            @SuppressLint("DefaultLocale") String temp = String.format("%2.1f", (weatherForecastRequest.getList()[j].getMain().getTemp() - 273.5));
                                            singletonForSaveState.getArrayList().add(new String[]{temp,
                                                    weatherForecastRequest.getList()[j].getWeather()[0].getIcon()});
                                        }
                                    }
                                }
                                singletonForSaveState.getFragmentWeather().showWeather();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        exceptionCatch();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void exceptionCatch() {
        singletonForSaveState.getFragmentWeather().alertAboutConnectionFailed();
    }
}
