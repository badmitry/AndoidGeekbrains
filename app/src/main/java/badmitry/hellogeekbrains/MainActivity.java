package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
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
    private int isRain;
    private boolean showSpeedOfWind;
    private boolean showPressure;
    private String city;
    private int valueOfTemperature;
    private int valueOfSpeedOfWind;
    private int valueOfPressure;
    private boolean isDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnBtnShowWeatherClkBehaviour();
        setOnCityClkBehaviour();
        setOnBtnSettingsClkBehaviour();
        setOnBtnShowWeatherInInternet();
        startCreateMainScreen();
    }

    @Override
    public void setTheme(int resId) {
        if (isDarkTheme) {
            super.setTheme(R.style.darkStyle);
        } else {
            super.setTheme(R.style.lightStyle);
        }
    }

    private void setOnBtnShowWeatherInInternet() {
        btnShowWeatherInInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://yandex.ru/pogoda/" + city;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CODE_CITY && resultCode == RESULT_OK && data != null) {
            city = data.getStringExtra("city");
        }
        if (requestCode == this.REQUEST_CODE_SETTING && resultCode == RESULT_OK && data != null) {
            showSpeedOfWind = data.getBooleanExtra("showSpeedOfWind", true);
            showPressure = data.getBooleanExtra("showPressure", true);
            isDarkTheme = data.getBooleanExtra("isDarkTheme", false);
        }
        startCreateMainScreen();
    }

    private void setOnCityClkBehaviour() {
        textViewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
                intent.putExtra("isDarkTheme", isDarkTheme);
                startActivityForResult(intent, REQUEST_CODE_CITY);
            }
        });
    }

    private void setOnBtnSettingsClkBehaviour() {
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityOfSettings.class);
                intent.putExtra("showSpeedOfWind", showSpeedOfWind);
                intent.putExtra("showPressure", showPressure);
                intent.putExtra("isDarkTheme", isDarkTheme);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("isRain", isRain);
        outState.putString("city", city);
        outState.putBoolean("showSpeedOfWind", showSpeedOfWind);
        outState.putBoolean("showPressure", showPressure);
        outState.putInt("valueOfTemperature", valueOfTemperature);
        outState.putInt("valueOfSpeedOfWind", valueOfSpeedOfWind);
        outState.putInt("valueOfPressure", valueOfPressure);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isRain = savedInstanceState.getInt("isRain");
        city = savedInstanceState.getString("city");
        showPressure = savedInstanceState.getBoolean("showPressure");
        showSpeedOfWind = savedInstanceState.getBoolean("showSpeedOfWind");
        valueOfTemperature = savedInstanceState.getInt("valueOfTemperature");
        valueOfSpeedOfWind = savedInstanceState.getInt("valueOfSpeedOfWind");
        valueOfPressure = savedInstanceState.getInt("valueOfPressure");
    }

    private void startCreateMainScreen() {
        if (city != null) {
            textViewCity.setText(city);
        }
        if (showSpeedOfWind) {
            textViewSpeedWind.setVisibility(View.VISIBLE);
            textViewSpeedWindSign.setVisibility(View.VISIBLE);
        } else {
            textViewSpeedWind.setVisibility(View.INVISIBLE);
            textViewSpeedWindSign.setVisibility(View.INVISIBLE);
        }
        if (showPressure) {
            textViewPressure.setVisibility(View.VISIBLE);
            textViewPressureSign.setVisibility(View.VISIBLE);
        } else {
            textViewPressure.setVisibility(View.INVISIBLE);
            textViewPressureSign.setVisibility(View.INVISIBLE);
        }
        if (city != null) {
            btnShowWeatherInInternet.setVisibility(View.VISIBLE);
        } else {
            btnShowWeatherInInternet.setVisibility(View.INVISIBLE);
        }
        showWeather();
    }

    private void initViews() {
        buttonShowWeather = findViewById(R.id.buttonShowWeather);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewWeather = findViewById(R.id.textViewWeather);
        textViewCity = findViewById(R.id.myCity);
        imageViewWeather = findViewById(R.id.imageViewWeather);
        buttonSettings = findViewById(R.id.buttonSettings);
        textViewSpeedWindSign = findViewById(R.id.textViewSpeedWindSign);
        textViewPressureSign = findViewById(R.id.textViewPressureSign);
        textViewSpeedWind = findViewById(R.id.textViewSpeedWind);
        textViewPressure = findViewById(R.id.textViewPressure);
        btnShowWeatherInInternet = findViewById(R.id.btnShowWeatherInInternet);
    }

    private void setOnBtnShowWeatherClkBehaviour() {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRain = (random.nextInt(2) + 1);
                int t = random.nextInt(15);
                buttonShowWeather.setText(R.string.reload);
                valueOfTemperature = t + 15;
                valueOfSpeedOfWind = t / 2;
                valueOfPressure = 100 + t / 2;
                showWeather();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showWeather() {
        textViewTemperature.setText(valueOfTemperature + getString(R.string.grad));
        textViewPressure.setText(valueOfPressure + getString(R.string.mm));
        textViewSpeedWind.setText(valueOfSpeedOfWind + getString(R.string.mInS));
        if (isRain == 1) {
            textViewWeather.setText(R.string.sun);
            imageViewWeather.setImageResource(R.drawable.sun);
        } else if (isRain == 2) {
            textViewWeather.setText(R.string.rainy);
            imageViewWeather.setImageResource(R.drawable.rain);
        }
    }

}