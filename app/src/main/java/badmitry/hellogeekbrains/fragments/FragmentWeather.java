package badmitry.hellogeekbrains.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import badmitry.hellogeekbrains.ChooseCityActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterForWeather;

public class FragmentWeather extends Fragment {

    private final int REQUEST_CODE_CITY = 1;
    private Random random = new Random();
    private Button buttonShowWeather;
    private TextView textViewCity;
    private Button btnShowWeatherInInternet;
    private TextView textViewSpeedWindSign;
    private TextView textViewPressureSign;
    private TextView textViewSpeedWind;
    private TextView textViewPressure;
    private SingletonForSaveState singletonForSaveState;
    private RecyclerView recyclerView;
    private ArrayList<int[]> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        btnShowWeatherInInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://yandex.ru/pogoda/" + singletonForSaveState.getCity();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void setOnCityClkBehaviour() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            textViewCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ChooseCityActivity.class);
                    intent.putExtra("isDarkTheme", singletonForSaveState.isDarkTheme());
                    startActivityForResult(intent, REQUEST_CODE_CITY);
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startCreateMainScreen() {
        if (singletonForSaveState.isCity()) {
            String city = singletonForSaveState.getCity();
            textViewCity.setText(city);
            btnShowWeatherInInternet.setVisibility(View.VISIBLE);
            buttonShowWeather.setVisibility(View.VISIBLE);
            generateWeather();
            showWeather();
        } else {
            btnShowWeatherInInternet.setVisibility(View.INVISIBLE);
            buttonShowWeather.setVisibility(View.INVISIBLE);
        }
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
    }

    private void initViews(View view) {
        buttonShowWeather = view.findViewById(R.id.buttonShowWeather);
        textViewCity = view.findViewById(R.id.myCity);
        textViewSpeedWindSign = view.findViewById(R.id.textViewSpeedWindSign);
        textViewPressureSign = view.findViewById(R.id.textViewPressureSign);
        textViewSpeedWind = view.findViewById(R.id.textViewSpeedWind);
        textViewPressure = view.findViewById(R.id.textViewPressure);
        btnShowWeatherInInternet = view.findViewById(R.id.btnShowWeatherInInternet);
        recyclerView = view.findViewById(R.id.recyclerViewForWeather);
    }

    private void setOnBtnShowWeatherClkBehaviour() {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (singletonForSaveState.isCity()) {
                    generateWeather();
                    showWeather();
                }
            }
        });
    }

    private void generateWeather() {
        arrayList.clear();
        singletonForSaveState.setIsRain(random.nextInt(2) + 1);
        int t = random.nextInt(15);
        buttonShowWeather.setText(R.string.reload);
        singletonForSaveState.setValueOfTemperature(t + 15);
        singletonForSaveState.setValueOfSpeedOfWind(t / 2);
        singletonForSaveState.setValueOfPressure(100 + t / 2);
        arrayList.add(new int[]{singletonForSaveState.getValueOfTemperature(), singletonForSaveState.getIsRain()});
        for (int i = 0; i < 4; i++) {
            arrayList.add(new int[]{(random.nextInt(15) + 15), (random.nextInt(2) + 1)});
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void showWeather() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
        AdapterForWeather adapter;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            adapter = new AdapterForWeather(arrayList, 5);
        } else {
            adapter = new AdapterForWeather(arrayList, 1);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        textViewPressure.setText(singletonForSaveState.getValueOfPressure() + getString(R.string.mm));
        textViewSpeedWind.setText(singletonForSaveState.getValueOfSpeedOfWind() + getString(R.string.mInS));
    }
}