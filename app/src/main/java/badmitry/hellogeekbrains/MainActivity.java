package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
//    private final int REQUEST_CODE_CITY = 1;
//    private final int REQUEST_CODE_SETTING = 2;
//    private Random random = new Random();
//    private Button buttonShowWeather;
//    private TextView textViewWeather;
//    private TextView textViewTemperature;
//    private TextView textViewCity;
//    private ImageView imageViewWeather;
//    private Button buttonSettings;
//    private Button btnShowWeatherInInternet;
//    private TextView textViewSpeedWindSign;
//    private TextView textViewPressureSign;
//    private TextView textViewSpeedWind;
//    private TextView textViewPressure;
//    private int isRain;
//    private boolean showSpeedOfWind;
//    private boolean showPressure;
//    private int valueOfTemperature;
//    private int valueOfSpeedOfWind;
//    private int valueOfPressure;
//    private boolean isDarkTheme;
//    private SingletonForSaveState singletonForSaveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        singletonForSaveState = SingletonForSaveState.getInstance();
//        singletonForSaveState.setMainActivity(this);
        setContentView(R.layout.activity_main);
//        initViews();
//        setOnBtnShowWeatherClkBehaviour();
//        setOnCityClkBehaviour();
//        setOnBtnSettingsClkBehaviour();
//        setOnBtnShowWeatherInInternet();
//        startCreateMainScreen();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
//
//    private void setOnBtnShowWeatherInInternet() {
//        btnShowWeatherInInternet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = "https://yandex.ru/pogoda/" + singletonForSaveState.getCity();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == this.REQUEST_CODE_CITY && resultCode == RESULT_OK && data != null) {
//            singletonForSaveState.setCity(data.getStringExtra("city"));
//        }
//        if (requestCode == this.REQUEST_CODE_SETTING && resultCode == RESULT_OK && data != null) {
//            showSpeedOfWind = data.getBooleanExtra("showSpeedOfWind", true);
//            showPressure = data.getBooleanExtra("showPressure", true);
//            isDarkTheme = data.getBooleanExtra("isDarkTheme", false);
//            startCreateMainScreen();
//        }
//    }

//    private void setOnCityClkBehaviour() {
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            textViewCity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
//                    intent.putExtra("isDarkTheme", isDarkTheme);
//                    startActivityForResult(intent, REQUEST_CODE_CITY);
//                }
//            });
//        }
//    }
//
//    private void setOnBtnSettingsClkBehaviour() {
//        buttonSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ActivityOfSettings.class);
//                intent.putExtra("showSpeedOfWind", showSpeedOfWind);
//                intent.putExtra("showPressure", showPressure);
//                intent.putExtra("isDarkTheme", isDarkTheme);
//                startActivityForResult(intent, REQUEST_CODE_SETTING);
//            }
//        });
//    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putInt("isRain", isRain);
//        outState.putBoolean("showSpeedOfWind", showSpeedOfWind);
//        outState.putBoolean("showPressure", showPressure);
//        outState.putInt("valueOfTemperature", valueOfTemperature);
//        outState.putInt("valueOfSpeedOfWind", valueOfSpeedOfWind);
//        outState.putInt("valueOfPressure", valueOfPressure);
//        outState.putBoolean("isDarkTheme", isDarkTheme);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        isRain = savedInstanceState.getInt("isRain");
//        showPressure = savedInstanceState.getBoolean("showPressure");
//        showSpeedOfWind = savedInstanceState.getBoolean("showSpeedOfWind");
//        valueOfTemperature = savedInstanceState.getInt("valueOfTemperature");
//        valueOfSpeedOfWind = savedInstanceState.getInt("valueOfSpeedOfWind");
//        valueOfPressure = savedInstanceState.getInt("valueOfPressure");
//        isDarkTheme = savedInstanceState.getBoolean("isDarkTheme");
//        startCreateMainScreen();
//    }

//    protected void startCreateMainScreen() {
//        if (singletonForSaveState.isCity()) {
//            String city = singletonForSaveState.getCity();
//            Log.d("main", city);
//            textViewCity.setText(city);
//            btnShowWeatherInInternet.setVisibility(View.VISIBLE);
//            generateWeather();
//            showWeather();
//        } else {
//            btnShowWeatherInInternet.setVisibility(View.INVISIBLE);
//        }
//        if (showSpeedOfWind) {
//            textViewSpeedWind.setVisibility(View.VISIBLE);
//            textViewSpeedWindSign.setVisibility(View.VISIBLE);
//        } else {
//            textViewSpeedWind.setVisibility(View.INVISIBLE);
//            textViewSpeedWindSign.setVisibility(View.INVISIBLE);
//        }
//        if (showPressure) {
//            textViewPressure.setVisibility(View.VISIBLE);
//            textViewPressureSign.setVisibility(View.VISIBLE);
//        } else {
//            textViewPressure.setVisibility(View.INVISIBLE);
//            textViewPressureSign.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void initViews() {
//        buttonShowWeather = findViewById(R.id.buttonShowWeather);
//        textViewTemperature = findViewById(R.id.textViewTemperature);
//        textViewWeather = findViewById(R.id.textViewWeather);
//        textViewCity = findViewById(R.id.myCity);
//        imageViewWeather = findViewById(R.id.imageViewWeather);
//        buttonSettings = findViewById(R.id.buttonSettings);
//        textViewSpeedWindSign = findViewById(R.id.textViewSpeedWindSign);
//        textViewPressureSign = findViewById(R.id.textViewPressureSign);
//        textViewSpeedWind = findViewById(R.id.textViewSpeedWind);
//        textViewPressure = findViewById(R.id.textViewPressure);
//        btnShowWeatherInInternet = findViewById(R.id.btnShowWeatherInInternet);
//    }
//
//    private void setOnBtnShowWeatherClkBehaviour() {
//        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                generateWeather();
//                showWeather();
//            }
//        });
//    }
//
//    private void generateWeather() {
//        Log.d("gen", "generateWeather: ");
//        isRain = (random.nextInt(2) + 1);
//        int t = random.nextInt(15);
//        buttonShowWeather.setText(R.string.reload);
//        valueOfTemperature = t + 15;
//        valueOfSpeedOfWind = t / 2;
//        valueOfPressure = 100 + t / 2;
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void showWeather() {
//        textViewTemperature.setText(valueOfTemperature + getString(R.string.grad));
//        textViewPressure.setText(valueOfPressure + getString(R.string.mm));
//        textViewSpeedWind.setText(valueOfSpeedOfWind + getString(R.string.mInS));
//        if (isRain == 1) {
//            textViewWeather.setText(R.string.sun);
//            imageViewWeather.setImageResource(R.drawable.sun);
//        } else if (isRain == 2) {
//            textViewWeather.setText(R.string.rainy);
//            imageViewWeather.setImageResource(R.drawable.rain);
//        }
//    }

}