package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import badmitry.hellogeekbrains.fragments.FragmentWeather;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SETTING = 2;
    SingletonForSaveState singletonForSaveState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singletonForSaveState = SingletonForSaveState.getInstance();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, ActivityOfSettings.class);
            intent.putExtra("showSpeedOfWind", singletonForSaveState.isShowSpeedOfWind());
            intent.putExtra("showPressure", singletonForSaveState.isShowPressure());
            intent.putExtra("isDarkTheme", singletonForSaveState.isDarkTheme());
            startActivityForResult(intent, REQUEST_CODE_SETTING);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING && resultCode == RESULT_OK && data != null) {
            singletonForSaveState.setShowSpeedOfWind(data.getBooleanExtra("showSpeedOfWind", true));
            singletonForSaveState.setShowPressure(data.getBooleanExtra("showPressure", true));
            singletonForSaveState.setDarkTheme(data.getBooleanExtra("isDarkTheme", false));
            FragmentWeather fw = (FragmentWeather) getSupportFragmentManager().findFragmentById(R.id.fragmentShowWeather);
            if (fw != null) {
                fw.startCreateMainScreen();
            }
        }
    }

}