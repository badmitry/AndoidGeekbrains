package badmitry.hellogeekbrains.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.util.Calendar;

import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import badmitry.hellogeekbrains.model.WeatherRequest;

public class ServiceOfParseDataFromInternet extends IntentService {
    private static final String ACTION = "parse";
    private static final String EXTRA_PARAM1 = "PARAM1";
    private static final String EXTRA_PARAM2 = "PARAM2";
    private SingletonForSaveState singletonForSaveState;

    public ServiceOfParseDataFromInternet() {
        super("ServiceOfParseDataFromInternet");
    }

    public static void startServiceOfParseDataFromInternet(Context context, WeatherRequest weatherRequest, WeatherForecastRequest weatherForecastRequest) {
        Intent intentParse = new Intent(context, ServiceOfParseDataFromInternet.class);
        intentParse.setAction(ACTION);
        intentParse.putExtra(EXTRA_PARAM1, weatherRequest);
        intentParse.putExtra(EXTRA_PARAM2, weatherForecastRequest);
        context.startService(intentParse);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            singletonForSaveState = SingletonForSaveState.getInstance();
            singletonForSaveState.getArrayList().clear();
            WeatherRequest weatherRequest = (WeatherRequest) intent.getSerializableExtra(EXTRA_PARAM1);
            WeatherForecastRequest weatherForecastRequest = (WeatherForecastRequest) intent.getSerializableExtra(EXTRA_PARAM2);
            assert weatherRequest != null;
            assert weatherForecastRequest != null;
            singletonForSaveState.setValueOfPressure((double) 3 / 4 * weatherRequest.getMain().getPressure());
            singletonForSaveState.setValueOfSpeedOfWind(weatherRequest.getWind().getSpeed());
            @SuppressLint("DefaultLocale") String temp = String.format("%2.1f", (weatherRequest.getMain().getTemp() - 273.5));
            singletonForSaveState.getArrayList().add(new String[]{temp, weatherRequest.getWeather()[0].getIcon()});

            final int[] unixData = new int[4];
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < 4; i++) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
                unixData[i] = (int) (calendar1.getTimeInMillis() / 1000);
            }

            for (int unixDatum : unixData) {
                for (int j = 0; j < weatherForecastRequest.getList().length; j++) {
                    if (weatherForecastRequest.getList()[j].getDt() == unixDatum) {
                        @SuppressLint("DefaultLocale") String tempString =
                                String.format("%2.1f", (weatherForecastRequest.getList()[j].getMain().getTemp() - 273.5));
                        singletonForSaveState.getArrayList().add(new String[]{tempString,
                                weatherForecastRequest.getList()[j].getWeather()[0].getIcon()});
                    }
                }
            }

            singletonForSaveState.getFragmentWeather().showWeather();
            singletonForSaveState.getFragmentWeather().drawThermometer();
        }
    }
}
