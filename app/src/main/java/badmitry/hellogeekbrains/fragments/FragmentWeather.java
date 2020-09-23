package badmitry.hellogeekbrains.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterForWeather;
import badmitry.hellogeekbrains.retrofitForSelectedCity.LoaderWeather;
import badmitry.hellogeekbrains.room.App;
import badmitry.hellogeekbrains.room.CitySource;
import badmitry.hellogeekbrains.room.HistoryCity;
import badmitry.hellogeekbrains.room.InterfaceDAO;
import badmitry.hellogeekbrains.view.Thermometer;

public class FragmentWeather extends Fragment {

    private String city;
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
    private LoaderWeather loaderWeather;

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
        loaderWeather = new LoaderWeather();
        setOnBtnShowWeatherClkBehaviour();
        setOnCityClkBehaviour();
        setOnBtnShowWeatherInInternet();
        startCreateMainScreen();
    }

    private void setOnBtnShowWeatherInInternet() {
        btnShowWeatherInInternet.setOnClickListener(view -> {
            String url = "https://yandex.ru/pogoda/" + city;
            Log.d("!!!!", "setOnBtnShowWeatherInInternet: " + city);
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
        textViewCity.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        LatLng latLng = singletonForSaveState.getLatLng();
        if (latLng != null) {
            loaderWeather.reloadWeatherForCurrentCity(latLng.longitude + "", latLng.latitude + "");
        } else {
            if (singletonForSaveState.isCity()) {
                loaderWeather.reloadWeatherForSelectedCity();
            } else {
                takeLocation();
            }
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
        buttonShowWeather.setOnClickListener(view -> startCreateMainScreen());
    }

    private void takeLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else {
            LocationManager mLocManager = singletonForSaveState.getLocationManager();
            Location loc = null;
            try {
                loc = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (loc == null) {
                loaderWeather.reloadWeatherForSelectedCity();
            } else {
                String lon = loc.getLongitude() + "";
                String lat = loc.getLatitude() + "";
                loaderWeather.reloadWeatherForCurrentCity(lon, lat);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void showWeather(String newCity) {
        if (newCity == null || newCity.equals("")) {
            city = getString(R.string.unknown_city);
        }else {
            city = newCity;
        }
        this.requireActivity().runOnUiThread(() -> {
            if (singletonForSaveState.getArrayList().size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
                AdapterForWeather adapter = new AdapterForWeather(singletonForSaveState.getArrayList(), 5);
                setBackgroundImage();
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                insertNewCityInHistory(city);
            }
            textViewPressure.setText(singletonForSaveState.getValueOfPressure() + getString(R.string.mm));
            textViewSpeedWind.setText(singletonForSaveState.getValueOfSpeedOfWind() + getString(R.string.mInS));
            progressBar.setVisibility(View.INVISIBLE);
            btnShowWeatherInInternet.setVisibility(View.VISIBLE);
            buttonShowWeather.setVisibility(View.VISIBLE);
            textViewCity.setText(city);
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

    private void insertNewCityInHistory(String newCity) {
        HistoryCity historyCity = new HistoryCity();
        historyCity.city = newCity;
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

    public void drawThermometer() {
        if (singletonForSaveState.getArrayList().size() > 3) {
            this.requireActivity().runOnUiThread(() -> {
                Thermometer thermometer = new Thermometer(requireActivity().getApplicationContext());
                imageThermometer.addView(thermometer);
            });
        }
    }
}