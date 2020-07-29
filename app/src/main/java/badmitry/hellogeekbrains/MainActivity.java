package badmitry.hellogeekbrains;

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

    private EditText editTextInputCity;
    private String city;
    private String[] citys = {"Moscow", "SP", "Vidnoe", "Kazan", "Vladivostok", "Anadir",
            "Samara", "Murmansk", "Sochi", "Anapa"};

    private Button buttonOk;
    private CheckBox checkBoxSpeedOfWind;
    private CheckBox checkBoxPressure;
    private boolean speedWind = false;
    private boolean pressure = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startCreateMainScreen();
    }

    private void startCreateMainScreen() {
        setContentView(R.layout.activity_main);
        initViews();
        setOnButtonClkBehaviour();
        setOnСityClkBehaviour();
        setOnButtonSettingsClkBehaviour();
        if (city != null) {
            textViewCity.setText(city);
        }
        if (speedWind) {
            textViewSpeedWind.setVisibility(View.VISIBLE);
            textViewSpeedWindSign.setVisibility(View.VISIBLE);
        } else {
            textViewSpeedWind.setVisibility(View.INVISIBLE);
            textViewSpeedWindSign.setVisibility(View.INVISIBLE);
        }
        if (pressure) {
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
        textViewCity = findViewById(R.id.MyСity);
        imageViewWeather = (ImageView) findViewById(R.id.imageViewWeather);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        textViewSpeedWindSign = findViewById(R.id.textViewSpeedWindSign);
        textViewPressureSign = findViewById(R.id.textViewPressureSign);
        textViewSpeedWind = findViewById(R.id.textViewSpeedWind);
        textViewPressure = findViewById(R.id.textViewPressure);
        Log.d("initViews", "ok");
    }

    private void setOnButtonClkBehaviour() {
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnButtonShowWeather();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void clickOnButtonShowWeather() {
        int i = random.nextInt(2);
        int t = random.nextInt(15);
        buttonShowWeather.setText(R.string.reload);
        textViewTemperature.setText((t + 15) + getString(R.string.grad));
        textViewPressure.setText((100 + t * 2) + getString(R.string.mm));
        textViewSpeedWind.setText((t / 2) + getString(R.string.mInS));
        if (i == 1) {
            textViewWeather.setText(R.string.sun);
            imageViewWeather.setImageResource(R.drawable.sun);
        } else {
            textViewWeather.setText(R.string.rainy);
            imageViewWeather.setImageResource(R.drawable.rain);
        }
    }

    //Choese city

    private void setOnСityClkBehaviour() {
        textViewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.city);
                setEditTextFromChoseCityBehavior();
                LinearLayout linearLayout = findViewById(R.id.linearLayoutInScroll);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < citys.length; i++) {
                    final TextView textView = new TextView(getApplicationContext());
                    textView.setText(citys[i]);
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
        city = text;
        startCreateMainScreen();
        clickOnButtonShowWeather();
    }

    //Settings

    private void setOnButtonOkClkBehaviour() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speedWind = checkBoxSpeedOfWind.isChecked();
                pressure = checkBoxPressure.isChecked();
                startCreateMainScreen();
                clickOnButtonShowWeather();
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
                setOnButtonOkClkBehaviour();
            }
        });
    }

}