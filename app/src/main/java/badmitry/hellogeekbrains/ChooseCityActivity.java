package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.Objects;
import badmitry.hellogeekbrains.fragments.FragmentChooseCities;

public class ChooseCityActivity extends AppCompatActivity {
    private boolean isDarkTheme;
    private SingletonForSaveState singletonForSaveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.city);
        singletonForSaveState = SingletonForSaveState.getInstance();
        isDarkTheme = getIntent().getBooleanExtra("isDarkTheme", false);
        showBackBtn();
    }

    @Override
    public void setTheme(int resid) {
        if (isDarkTheme) {
            super.setTheme(R.style.darkStyle);
        } else {
            super.setTheme(R.style.lightStyle);
        }
    }

    private void changeCityOnMainLayout(String text) {
        Intent intent = new Intent();
        intent.putExtra("city", text);
        setResult(RESULT_OK, intent);
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
            FragmentChooseCities fr = (FragmentChooseCities) getSupportFragmentManager().findFragmentById(R.id.fragmentChooseCities);
            System.out.println(fr);
            if (fr != null) {
                String text = fr.getCityFromEditText();
                if (text != null && !text.equals("")) {
                    singletonForSaveState.setCity(text);
                    changeCityOnMainLayout(text);
                }
            }
            finish();
        }
        return true;
    }
}