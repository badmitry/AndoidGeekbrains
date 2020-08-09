package badmitry.hellogeekbrains.sampledata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Random;
import badmitry.hellogeekbrains.ActivityOfSettings;
import badmitry.hellogeekbrains.ChooseCityActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

import static android.app.Activity.RESULT_OK;

public class FragmentWeather extends Fragment {

    private final int REQUEST_CODE_CITY = 1;
    private final int REQUEST_CODE_SETTING = 2;
    private Random random = new Random();
    private Button buttonShowWeather;
    private TextView textViewWeather;
    private TextView textViewTemperature;
    private TextView textViewCity;
    private ImageView imageViewWeather;
    private Button buttonSettings;
    private Button btnShowWeatherInInternet;
    private TextView textViewSpeedWindSign;
    private TextView textViewPressureSign;
    private TextView textViewSpeedWind;
    private TextView textViewPressure;
    private SingletonForSaveState singletonForSaveState;

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
        setOnBtnSettingsClkBehaviour();
        setOnBtnShowWeatherInInternet();
        startCreateMainScreen();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CODE_CITY && resultCode == RESULT_OK && data != null) {
            singletonForSaveState.setCity(data.getStringExtra("city"));
        }
        if (requestCode == this.REQUEST_CODE_SETTING && resultCode == RESULT_OK && data != null) {
            singletonForSaveState.setShowSpeedOfWind(data.getBooleanExtra("showSpeedOfWind", true));
            singletonForSaveState.setShowPressure(data.getBooleanExtra("showPressure", true));
            singletonForSaveState.setDarkTheme(data.getBooleanExtra("isDarkTheme", false));
        }
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

    private void setOnBtnSettingsClkBehaviour() {
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityOfSettings.class);
                intent.putExtra("showSpeedOfWind", singletonForSaveState.isShowSpeedOfWind());
                intent.putExtra("showPressure", singletonForSaveState.isShowPressure());
                intent.putExtra("isDarkTheme", singletonForSaveState.isDarkTheme());
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });
    }

    protected void startCreateMainScreen() {
        if (singletonForSaveState.isCity()) {
            String city = singletonForSaveState.getCity();
            textViewCity.setText(city);
            btnShowWeatherInInternet.setVisibility(View.VISIBLE);
            generateWeather();
            showWeather();
        } else {
            btnShowWeatherInInternet.setVisibility(View.INVISIBLE);
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
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        textViewWeather = view.findViewById(R.id.textViewWeather);
        textViewCity = view.findViewById(R.id.myCity);
        imageViewWeather = view.findViewById(R.id.imageViewWeather);
        buttonSettings = view.findViewById(R.id.buttonSettings);
        textViewSpeedWindSign = view.findViewById(R.id.textViewSpeedWindSign);
        textViewPressureSign = view.findViewById(R.id.textViewPressureSign);
        textViewSpeedWind = view.findViewById(R.id.textViewSpeedWind);
        textViewPressure = view.findViewById(R.id.textViewPressure);
        btnShowWeatherInInternet = view.findViewById(R.id.btnShowWeatherInInternet);
    }

    private void setOnBtnShowWeatherClkBehaviour() {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateWeather();
                showWeather();
            }
        });
    }

    private void generateWeather() {
        Log.d("gen", "generateWeather: ");
        singletonForSaveState.setIsRain(random.nextInt(2) + 1);
        int t = random.nextInt(15);
        buttonShowWeather.setText(R.string.reload);
        singletonForSaveState.setValueOfTemperature(t + 15);
        singletonForSaveState.setValueOfSpeedOfWind(t / 2);
        singletonForSaveState.setValueOfPressure(100 + t / 2);
    }

    @SuppressLint("SetTextI18n")
    private void showWeather() {
        textViewTemperature.setText(singletonForSaveState.getValueOfTemperature() + getString(R.string.grad));
        textViewPressure.setText(singletonForSaveState.getValueOfPressure() + getString(R.string.mm));
        textViewSpeedWind.setText(singletonForSaveState.getValueOfSpeedOfWind() + getString(R.string.mInS));
        if (singletonForSaveState.getIsRain() == 1) {
            textViewWeather.setText(R.string.sun);
            imageViewWeather.setImageResource(R.drawable.sun);
        } else if (singletonForSaveState.getIsRain() == 2) {
            textViewWeather.setText(R.string.rainy);
            imageViewWeather.setImageResource(R.drawable.rain);
        }
    }
}