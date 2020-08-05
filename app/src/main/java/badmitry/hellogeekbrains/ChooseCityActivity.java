package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Objects;

public class ChooseCityActivity extends AppCompatActivity {
    private EditText editTextInputCity;
    private LinearLayout linearLayout;
    private SingletonForSaveState singletonForSaveState;
    private boolean isDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);
        initViews();
        isDarkTheme = getIntent().getBooleanExtra("isDarkTheme", false);
        createListOfCities();
        setEditTextFromChoseCityBehavior();
        showBackBtn();
    }

    @Override
    public void setTheme(int resid) {
        if (isDarkTheme) {
            super.setTheme(R.style.darkStyle);
        }else{
            super.setTheme(R.style.lightStyle);
        }
    }

    private void initViews() {
        editTextInputCity = findViewById(R.id.inputCity);
        linearLayout = findViewById(R.id.linearLayoutInScroll);
        singletonForSaveState = SingletonForSaveState.getInstance();
    }

    private void createListOfCities() {
        String[] cities = singletonForSaveState.getCities();
        for (String city : cities) {
            final TextView textView = new TextView(getApplicationContext());
            textView.setText(city);
            textView.setTextSize(60);
            linearLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = textView.getText().toString();
                    changeCityOnMainLayout(text);
                }
            });
        }
    }

    private void setEditTextFromChoseCityBehavior() {
        editTextInputCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    String text = editTextInputCity.getText().toString();
                    changeCityOnMainLayout(text);
                    return true;
                }
                return false;
            }
        });
    }

    private void changeCityOnMainLayout(String text) {
        Intent intent = new Intent();
        intent.putExtra("city", text);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showBackBtn() {
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            String text = editTextInputCity.getText().toString();
            changeCityOnMainLayout(text);
        }
        return true;
    }
}