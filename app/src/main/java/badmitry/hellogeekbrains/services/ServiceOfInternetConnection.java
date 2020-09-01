package badmitry.hellogeekbrains.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import badmitry.hellogeekbrains.model.WeatherRequest;

public class ServiceOfInternetConnection extends IntentService {

    private String city;
    private static final String ACTION = "ServiceOfInternetConnection";
    private static final String CITY = "city";
    private SingletonForSaveState singletonForSaveState;
    private WeatherForecastRequest weatherForecastRequest;
    private WeatherRequest weatherRequest;

    public ServiceOfInternetConnection() {
        super("ServiceOfInternetConnection");
    }

    public static void startServiceOfInternetConnection(Context context) {
        Intent intent = new Intent(context, ServiceOfInternetConnection.class);
        intent.setAction(ACTION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            singletonForSaveState = SingletonForSaveState.getInstance();
            city = singletonForSaveState.getCity();
            currentWeather();
            ServiceOfParseDataFromInternet.startServiceOfParseDataFromInternet(this, weatherRequest, weatherForecastRequest);
        }
    }

    private void currentWeather() {
        final URL uri;
        try {
            uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" +
                    city + "&lang=ru&appid=94b18bc70bd073ad490a67a7c6ceb146");
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(2000);
                urlConnection.setReadTimeout(2000);
                BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(input);
                Gson gson = new Gson();
                weatherRequest = gson.fromJson(result, WeatherRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
                alertAboutConnectionFailed();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        forecastWeather();
    }

    private void forecastWeather() {
        try {
            final URL uri = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" +
                    city + "&appid=c0c4a4b4047b97ebc5948ac9c48c0559");
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String result = getLines(input);
                Gson gson = new Gson();
                weatherForecastRequest = gson.fromJson(result, WeatherForecastRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
                alertAboutConnectionFailed();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        StringBuilder rawData = new StringBuilder(1024);
        String tempVariable;
        while (true) {
            try {
                tempVariable = in.readLine();
                if (tempVariable == null) break;
                rawData.append(tempVariable).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData.toString();
    }


    private void alertAboutConnectionFailed() {
        singletonForSaveState.getFragmentWeather().alertAboutConnectionFailed();
    }
}
