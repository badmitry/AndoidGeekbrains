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
    private TextView textView;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnButtonClk();
    }

    private void initViews() {
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
    }

    private void setOnButtonClk() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }


//    public void showWeather(View view) {
//        int i = random.nextInt(2);
//        int t = random.nextInt(15);
//        TextView textView = (TextView) findViewById(R.id.textView2);
//        TextView textView3 = (TextView) findViewById(R.id.textView3);
//        ImageView imageView = (ImageView) findViewById(R.id.image);
//        System.out.println(i);
//        String weather;
//        if (i == 1) {
//            textView.setText(R.string.sun);
//            textView3.setText((t + 15) + "*C");
//            imageView.setImageResource(R.drawable.sun);
//        } else {
//            textView.setText(R.string.rainy);
//            textView3.setText((t + 15) + "*C");
//            imageView.setImageResource(R.drawable.rain);
//        }
//    }
}