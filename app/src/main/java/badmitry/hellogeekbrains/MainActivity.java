package badmitry.hellogeekbrains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showWeather(View view) {
        int i = random.nextInt(2);
        TextView textView = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        System.out.println(i);
        if (i == 1) {
           textView.setText(R.string.sun);
           imageView.setImageResource(R.drawable.sun);
        } else {
            textView.setText(R.string.rainy);
            imageView.setImageResource(R.drawable.rain);
        }
    }
}