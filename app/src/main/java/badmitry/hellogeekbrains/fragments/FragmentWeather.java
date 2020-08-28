package badmitry.hellogeekbrains.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterForWeather;

public class FragmentWeather extends Fragment {

    private Button buttonShowWeather;
    private TextView textViewCity;
    private Button btnShowWeatherInInternet;
    private TextView textViewSpeedWindSign;
    private TextView textViewPressureSign;
    private TextView textViewSpeedWind;
    private TextView textViewPressure;
    private SingletonForSaveState singletonForSaveState;
    private RecyclerView recyclerView;

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
        textViewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) getActivity();
                assert ma != null;
                ma.setChooseCityFragment();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startCreateMainScreen() {
        btnShowWeatherInInternet.setVisibility(View.INVISIBLE);
        buttonShowWeather.setVisibility(View.INVISIBLE);
        if (singletonForSaveState.isCity()) {
            textViewCity.setVisibility(View.INVISIBLE);
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
        recyclerView = view.findViewById(R.id.recyclerViewForWeather);
    }

    private void setOnBtnShowWeatherClkBehaviour() {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                reloadWeather();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void reloadWeather() {
        singletonForSaveState.getWeatherFromInternet().updateCurrentWeather();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    public void showWeather() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
        AdapterForWeather adapter = new AdapterForWeather(singletonForSaveState.getArrayList(), 5);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        textViewPressure.setText(singletonForSaveState.getValueOfPressure() + getString(R.string.mm));
        textViewSpeedWind.setText(singletonForSaveState.getValueOfSpeedOfWind() + getString(R.string.mInS));
        textViewCity.setText(singletonForSaveState.getCity());
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
    }

    public void alertAboutConnectionFailed() {
        this.requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewCity.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.warning)
                        .setMessage(R.string.connection_failed)
                        .setIcon(R.mipmap.weather)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        System.out.println(1);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}