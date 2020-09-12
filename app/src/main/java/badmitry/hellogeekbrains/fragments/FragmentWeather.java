package badmitry.hellogeekbrains.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterForWeather;
import badmitry.hellogeekbrains.model.WeatherForecastRequest;
import badmitry.hellogeekbrains.model.WeatherRequest;
import badmitry.hellogeekbrains.room.App;
import badmitry.hellogeekbrains.room.CitySource;
import badmitry.hellogeekbrains.room.HistoryCity;
import badmitry.hellogeekbrains.room.InterfaceDAO;
import badmitry.hellogeekbrains.services.WeatherRepo;
import badmitry.hellogeekbrains.view.Thermometer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWeather extends Fragment {

    private Button buttonShowWeather;
    private TextView textViewCity;
    private Button btnShowWeatherInInternet;
    private TextView textViewSpeedWindSign;
    private TextView textViewPressureSign;
    private TextView textViewSpeedWind;
    private TextView textViewPressure;
    private SingletonForSaveState singletonForSaveState;
    private FrameLayout imageThermometer;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CitySource citySource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        singletonForSaveState = SingletonForSaveState.getInstance();
        singletonForSaveState.setFragmentWeather(this);
        initViews(view);
        setOnBtnShowWeatherClkBehaviour();
        setOnCityClkBehaviour();
        setOnBtnShowWeatherInInternet();
        startCreateMainScreen();
    }

    private void setOnBtnShowWeatherInInternet() {
        btnShowWeatherInInternet.setOnClickListener(view -> {
            String url = "https://yandex.ru/pogoda/" + singletonForSaveState.getCity();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }

    private void setOnCityClkBehaviour() {
        textViewCity.setOnClickListener(view -> {
            MainActivity ma = (MainActivity) getActivity();
            assert ma != null;
            ma.setChooseCityFragment();
        });
    }

    public void startCreateMainScreen() {
        btnShowWeatherInInternet.setVisibility(View.INVISIBLE);
        buttonShowWeather.setVisibility(View.INVISIBLE);

        if (singletonForSaveState.isCity()) {
            textViewCity.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            reloadWeather();
        }
    }

    private void initViews(View view) {
        buttonShowWeather = view.findViewById(R.id.buttonShowWeather);
        textViewCity = view.findViewById(R.id.myCity);
        textViewSpeedWindSign = view.findViewById(R.id.textViewSpeedWindSign);
        textViewPressureSign = view.findViewById(R.id.textViewPressureSign);
        textViewSpeedWind = view.findViewById(R.id.textViewSpeedWind);
        textViewPressure = view.findViewById(R.id.textViewPressure);
        btnShowWeatherInInternet = view.findViewById(R.id.btnShowWeatherInInternet);
        imageThermometer = view.findViewById(R.id.imageThermometer);
        progressBar = view.findViewById(R.id.progressBarr);
        recyclerView = view.findViewById(R.id.recyclerViewForWeather);
        InterfaceDAO interfaceDAO = App.getInstance().getInterfaceDao();
        citySource = new CitySource(interfaceDAO);
    }

    private void setOnBtnShowWeatherClkBehaviour() {
        buttonShowWeather.setOnClickListener(view -> reloadWeather());
    }

    private void reloadWeather() {
        WeatherRepo weatherRepo = WeatherRepo.getInstance();
        singletonForSaveState.getArrayList().clear();
        weatherRepo.getAPIWeather().loadWeather(singletonForSaveState.getCity(),
                "ru", "94b18bc70bd073ad490a67a7c6ceb146").enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(@NonNull Call<WeatherRequest> call, @NonNull Response<WeatherRequest> response) {
                if (response.body() != null && response.isSuccessful()) {
                    WeatherRequest weatherRequest = response.body();
                    singletonForSaveState.setValueOfPressure((double) 3 / 4 * weatherRequest.main.pressure);
                    singletonForSaveState.setValueOfSpeedOfWind(weatherRequest.wind.speed);
                    @SuppressLint("DefaultLocale") String temp = String.format("%2.1f", (weatherRequest.main.temp - 273.5));
                    singletonForSaveState.getArrayList().add(new String[]{temp, weatherRequest.weather[0].icon});
                    weatherRepo.getAPIForecast().loadWeather(singletonForSaveState.getCity(),
                            "ru", "c0c4a4b4047b97ebc5948ac9c48c0559").enqueue(new Callback<WeatherForecastRequest>() {
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
                                singletonForSaveState.getFragmentWeather().showWeather();
                                singletonForSaveState.getFragmentWeather().drawThermometer();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<WeatherForecastRequest> call, @NonNull Throwable t) {
                            alertAboutConnectionFailed();
                            singletonForSaveState.getFragmentWeather().showWeather();
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
                singletonForSaveState.getFragmentWeather().showWeather();
                singletonForSaveState.getFragmentWeather().drawThermometer();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showWeather() {
        this.requireActivity().runOnUiThread(() -> {
            if (singletonForSaveState.getArrayList().size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
                AdapterForWeather adapter = new AdapterForWeather(singletonForSaveState.getArrayList(), 5);
                setBackgroundImage();
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                insertNewCityInHistory();
            }
            textViewPressure.setText(singletonForSaveState.getValueOfPressure() + getString(R.string.mm));
            textViewSpeedWind.setText(singletonForSaveState.getValueOfSpeedOfWind() + getString(R.string.mInS));
            textViewCity.setText(singletonForSaveState.getCity());
            progressBar.setVisibility(View.INVISIBLE);
            btnShowWeatherInInternet.setVisibility(View.VISIBLE);
            buttonShowWeather.setVisibility(View.VISIBLE);
            textViewCity.setVisibility(View.VISIBLE);
            if (singletonForSaveState.isShowSpeedOfWind()) {
                textViewSpeedWind.setVisibility(View.VISIBLE);
                textViewSpeedWindSign.setVisibility(View.VISIBLE);
            } else {
                textViewSpeedWind.setVisibility(View.INVISIBLE);
                textViewSpeedWindSign.setVisibility(View.INVISIBLE);
            }
            if (singletonForSaveState.isShowPressure()) {
                textViewPressure.setVisibility(View.VISIBLE);
                textViewPressureSign.setVisibility(View.VISIBLE);
            } else {
                textViewPressure.setVisibility(View.INVISIBLE);
                textViewPressureSign.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void insertNewCityInHistory() {
        HistoryCity historyCity = new HistoryCity();
        historyCity.city = singletonForSaveState.getCity();
        historyCity.temp = singletonForSaveState.getArrayList().get(0)[0];
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date now = new Date();
        historyCity.date = sdfDate.format(now);
        citySource.addCity(historyCity);
    }

    private void setBackgroundImage() {
        ImageView imageView = requireActivity().findViewById(R.id.imageViewBackground);
        String weather = singletonForSaveState.getArrayList().get(0)[1];
        String url;
        switch (weather) {
            case "01d":
            case "01n":
                url = "https://images.unsplash.com/photo-1587540724311-9e76f5127c98?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1352&q=80";
                break;
            case "02d":
            case "02n":
                url = "https://images.unsplash.com/photo-1559060017-445fb9722f2a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80";
                break;
            case "03d":
            case "03n":
                url = "https://images.unsplash.com/photo-1559060017-445fb9722f2a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80";
                break;
            case "04d":
            case "04n":
                url = "https://images.unsplash.com/photo-1559060017-445fb9722f2a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80";
                break;
            case "09d":
            case "09n":
                url = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80";
                break;
            case "10d":
            case "10n":
                url = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80";
                break;
            case "11d":
            case "11n":
                url = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80";
                break;
            case "13d":
            case "13n":
                url = "https://images.unsplash.com/photo-1418985991508-e47386d96a71?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80";
                break;
            case "50d":
            case "50n":
                url = "https://images.unsplash.com/photo-1438803235109-d737bc3129ec?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1266&q=80";
                break;
            default:
                url = "https://images.unsplash.com/photo-1559060017-445fb9722f2a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80";
        }
        Picasso.get().load(url)
                .into(imageView);
        if (singletonForSaveState.isDarkTheme()) {
            imageView.setAlpha((float) 1);
        } else {
            imageView.setAlpha((float) 0.5);
        }

    }

    public void alertAboutConnectionFailed() {
        this.requireActivity();
        this.requireActivity().runOnUiThread(() -> {
            textViewCity.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.warning)
                    .setMessage(R.string.connection_failed)
                    .setIcon(R.mipmap.weather)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok,
                            (dialog, id) -> Log.d("netConnection", getString(R.string.connection_failed)));
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public void alertAboutWrongCity() {
        this.requireActivity();
        this.requireActivity().runOnUiThread(() -> {
            textViewCity.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.warning)
                    .setMessage(R.string.wrong_city)
                    .setIcon(R.mipmap.weather)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok,
                            (dialog, id) -> Log.d("netConnection", getString(R.string.connection_failed)));
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public void drawThermometer() {
        if (singletonForSaveState.getArrayList().size() > 3) {
            this.requireActivity().runOnUiThread(() -> {
                Thermometer thermometer = new Thermometer(requireActivity().getApplicationContext());
                imageThermometer.addView(thermometer);
            });
        }
    }
}