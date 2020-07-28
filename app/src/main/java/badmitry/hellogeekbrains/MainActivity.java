package badmitry.hellogeekbrains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random();
    private Button button;
    private TextView textWeather;
    private TextView temperature;
    private TextView сity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnButtonClkBehaviour();
        setOnСityClkBehaviour();
    }

    private void setOnСityClkBehaviour() {
        сity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initViews() {
        button = findViewById(R.id.button);
        temperature = findViewById(R.id.temperature);
        textWeather = findViewById(R.id.textWeather);
        сity = findViewById(R.id.MyСity);

    }

    private void setOnButtonClkBehaviour() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = random.nextInt(2);
                int t = random.nextInt(15);
                button.setText(R.string.reload);
                TextView textWeather = (TextView) findViewById(R.id.textWeather);
                TextView temperature = (TextView) findViewById(R.id.temperature);
                ImageView imageView = (ImageView) findViewById(R.id.image);
                System.out.println(i);
                temperature.setText((t + 15) + "*C");
                if (i == 1) {
                    textWeather.setText(R.string.sun);
                    imageView.setImageResource(R.drawable.sun);
                } else {
                    textWeather.setText(R.string.rainy);
                    imageView.setImageResource(R.drawable.rain);
                }
            }
        });
    }
}