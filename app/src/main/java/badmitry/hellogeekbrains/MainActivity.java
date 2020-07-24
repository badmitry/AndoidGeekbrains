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
        int t = random.nextInt(15);
        TextView textView = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        System.out.println(i);
        String weather;
        if (i == 1) {
            textView.setText(R.string.sun);
            textView3.setText((t + 15) + "*C");
            imageView.setImageResource(R.drawable.sun);
        } else {
            textView.setText(R.string.rainy);
            textView3.setText((t + 15) + "*C");
            imageView.setImageResource(R.drawable.rain);
        }
    }
}