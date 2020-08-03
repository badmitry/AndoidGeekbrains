package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random();
    private Button buttonShowWeather;
    private TextView textViewWeather;
    private TextView textViewTemperature;
    private TextView textViewCity;
    private ImageView imageViewWeather;
    private Button buttonSettings;
    private TextView textViewSpeedWindSign;
    private TextView textViewPressureSign;
    private TextView textViewSpeedWind;
    private TextView textViewPressure;
    private SingletonForSaveState singletonForSaveState;
    private int isRain;

    private EditText editTextInputCity;

    private Button buttonOk;
    private CheckBox checkBoxSpeedOfWind;
    private CheckBox checkBoxPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startCreateMainScreen();
        Log.d("!!!", "onCreate: " + System.currentTimeMillis());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        Log.d("!!!", "onStart: " + System.currentTimeMillis());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        Log.d("!!!", "onResume: " + System.currentTimeMillis());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        Log.d("!!!", "onPause: " + System.currentTimeMillis());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        Log.d("!!!", "onStop: " + System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        Log.d("!!!", "onDestroy: " + System.currentTimeMillis());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("isRain", isRain);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isRain = savedInstanceState.getInt("isRain");
    }

    private void startCreateMainScreen() {
        singletonForSaveState = SingletonForSaveState.getInstance();
        setContentView(R.layout.activity_main);
        initViews();
        setOnShowWeatherClkBehaviour();
        setOnCityClkBehaviour();
        setOnButtonSettingsClkBehaviour();
        showWeather();
        if (singletonForSaveState.getCity() != null) {
            textViewCity.setText(singletonForSaveState.getCity());
        }
        if (singletonForSaveState.isSpeedWind()) {
            textViewSpeedWind.setVisibility(View.VISIBLE);
            textViewSpeedWindSign.setVisibility(View.VISIBLE);
        } else {
            textViewSpeedWind.setVisibility(View.INVISIBLE);
            textViewSpeedWindSign.setVisibility(View.INVISIBLE);
        }
        if (singletonForSaveState.isPressure()) {
            textViewPressure.setVisibility(View.VISIBLE);
            textViewPressureSign.setVisibility(View.VISIBLE);
        } else {
            textViewPressure.setVisibility(View.INVISIBLE);
            textViewPressureSign.setVisibility(View.INVISIBLE);
        }
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
        Log.d("initViews", "ok");
    }

    private void setOnShowWeatherClkBehaviour() {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRain = (random.nextInt(2) + 1);
                int t = random.nextInt(15);
                buttonShowWeather.setText(R.string.reload);
                singletonForSaveState.setValueOfTemperature(t + 15);
                singletonForSaveState.setValueOfSpeedOfWind(t/2);
                singletonForSaveState.setValueOfPressure(100 + t / 2);
                showWeather();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showWeather() {
        textViewTemperature.setText(singletonForSaveState.getValueOfTemperature() + getString(R.string.grad));
        textViewPressure.setText(singletonForSaveState.getValueOfPressure() + getString(R.string.mm));
        textViewSpeedWind.setText(singletonForSaveState.getValueOfSpeedOfWind() + getString(R.string.mInS));
        if (isRain == 1) {
            textViewWeather.setText(R.string.sun);
            imageViewWeather.setImageResource(R.drawable.sun);
        } else if (isRain == 2) {
            textViewWeather.setText(R.string.rainy);
            imageViewWeather.setImageResource(R.drawable.rain);
        }
    }

    //Choose city

    private void setOnCityClkBehaviour() {
        textViewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.city);
                setEditTextFromChoseCityBehavior();
                LinearLayout linearLayout = findViewById(R.id.linearLayoutInScroll);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                String[] cities = singletonForSaveState.getCities();
                for (String city : cities) {
                    final TextView textView = new TextView(getApplicationContext());
                    textView.setText(city);
                    textView.setTextSize(50);
                    linearLayout.addView(textView);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String text = String.valueOf((textView.getText()));
                            changeCityOnMainLayout(text);
                        }
                    });
                }
            }
        });
    }

    private void setEditTextFromChoseCityBehavior() {
        editTextInputCity = findViewById(R.id.inputCity);
        editTextInputCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    String text = String.valueOf((editTextInputCity.getText()));
                    changeCityOnMainLayout(text);
                    return true;
                }
                return false;
            }
        });
    }

    private void changeCityOnMainLayout(String text) {
        setContentView(R.layout.activity_main);
        singletonForSaveState.setCity(text);
        startCreateMainScreen();
        showWeather();
    }

    //Settings

    private void setOnButtonOkClkBehaviour() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singletonForSaveState.setSpeedWind(checkBoxSpeedOfWind.isChecked());
                singletonForSaveState.setPressure(checkBoxPressure.isChecked());
                startCreateMainScreen();
                showWeather();
            }
        });
    }

    private void setOnButtonSettingsClkBehaviour() {
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.settings);
                buttonOk = findViewById(R.id.buttonOk);
                checkBoxSpeedOfWind = findViewById(R.id.checkBoxSpeedOfWind);
                checkBoxPressure = findViewById(R.id.checkBoxPressure);
                if (singletonForSaveState.isPressure()){
                    checkBoxPressure.setChecked(true);
                }
                if (singletonForSaveState.isSpeedWind()){
                    checkBoxSpeedOfWind.setChecked(true);
                }
                setOnButtonOkClkBehaviour();
                if (singletonForSaveState.isPressure()){
                    checkBoxPressure.isChecked();
                }
                if (singletonForSaveState.isSpeedWind()){
                    checkBoxSpeedOfWind.isChecked();
                }
            }
        });
    }

}