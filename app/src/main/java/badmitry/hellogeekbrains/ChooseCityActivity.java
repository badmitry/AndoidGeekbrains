package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.Objects;
import badmitry.hellogeekbrains.fragments.FragmentChooseCities;

public class ChooseCityActivity extends AppCompatActivity {
    private SingletonForSaveState singletonForSaveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        singletonForSaveState = SingletonForSaveState.getInstance();
        if (singletonForSaveState.isDarkTheme()) {
            setTheme(R.style.darkStyle);
        } else {
            setTheme(R.style.lightStyle);
        }
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.city);
        showBackBtn();
    }

    private void showBackBtn() {
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentChooseCities fr = (FragmentChooseCities) getSupportFragmentManager().findFragmentById(R.id.fragmentChooseCities);
            if (fr != null) {
                String text = fr.getCityFromEditText();
                if (text != null && !text.equals("")) {
                    fr.changeCityOnMainLayout(text);
                }
            }
        }
        return true;
    }
}