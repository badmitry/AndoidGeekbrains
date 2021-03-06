package badmitry.hellogeekbrains;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;
import java.util.Objects;

import badmitry.hellogeekbrains.fragments.FragmentChooseCities;
import badmitry.hellogeekbrains.fragments.FragmentDevelopers;
import badmitry.hellogeekbrains.fragments.FragmentMap;
import badmitry.hellogeekbrains.fragments.FragmentSettings;
import badmitry.hellogeekbrains.fragments.FragmentWeather;
import badmitry.hellogeekbrains.roomHistoryCities.CitySource;
import badmitry.hellogeekbrains.roomHistoryCities.HistoryCity;
import badmitry.hellogeekbrains.roomHistoryCities.InterfaceDAO;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private SingletonForSaveState singletonForSaveState;
    private final String homeFragment = "HomeFragment";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private InterfaceDAO interfaceDAO;
    private CitySource citySource;


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(getString(R.string.settings_shared_preferences), Context.MODE_PRIVATE);
        singletonForSaveState = SingletonForSaveState.getInstance();
        singletonForSaveState.setDarkTheme(sharedPreferences.getBoolean(getString(R.string.is_dark_theme), false));
        singletonForSaveState.setShowPressure(sharedPreferences.getBoolean(getString(R.string.show_pressure), false));
        singletonForSaveState.setShowSpeedOfWind(sharedPreferences.getBoolean(getString(R.string.show_speed), false));
        singletonForSaveState.setCity(sharedPreferences.getString("City", null));
        singletonForSaveState.setIsCity(sharedPreferences.getBoolean(getString(R.string.selected_city), false));
        LocationManager mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        singletonForSaveState.setLocationManager(mLocManager);
        if (singletonForSaveState.isDarkTheme()) {
            setTheme(R.style.darkStyle);
        } else {
            setTheme(R.style.lightStyle);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        interfaceDAO = App.getInstance().getInterfaceDao();
        setHomeFragment();
        setOnClickForSlideMenuItems();
        initNotificationChannel();
        initGetToken();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            boolean permissionsGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (permissionsGranted) recreate();
        }
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initGetToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("PushMessage", "getInstanceId failed", task.getException());
                        return;
                    }
                    String token = Objects.requireNonNull(task.getResult()).getToken();
                    Log.d("token", token);
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof FragmentWeather) {
            finish();
        } else {
            setHomeFragment();
            navigationView.setCheckedItem(R.id.nav_weather);
        }
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.history: {
                choseHistoryCity();
                break;
            }
            case R.id.clear_history: {
                clearHistoryCity();
                break;
            }
            case R.id.developers: {
                setDevelopersFragment();
                break;
            }
        }
    }

    public void setHomeFragment() {
        FragmentWeather fragment = new FragmentWeather();
        setFragment(fragment, homeFragment);
        navigationView.setCheckedItem(R.id.nav_weather);
    }

    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
    private void setOnClickForSlideMenuItems() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_weather: {
                    setHomeFragment();
                    drawer.closeDrawers();
                    break;
                }
                case R.id.nav_current_weather: {
                    singletonForSaveState.setIsCity(false);
                    singletonForSaveState.setLatLng(null);
                    editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.selected_city), false);
                    editor.commit();
                    setHomeFragment();
                    drawer.closeDrawers();
                    break;
                }
                case R.id.nav_city: {
                    setChooseCityFragment();
                    drawer.closeDrawers();
                    break;
                }
                case R.id.nav_map: {
                    setMapFragment();
                    drawer.closeDrawers();
                    break;
                }
                case R.id.nav_settings: {
                    setSettingsFragment();
                    drawer.closeDrawers();
                    break;
                }
            }
            return true;
        });
    }

    public void setChooseCityFragment() {
        setFragment(new FragmentChooseCities(), "");
        navigationView.setCheckedItem(R.id.nav_city);
    }

    public void setMapFragment() {
        setFragment(new FragmentMap(), "");
        navigationView.setCheckedItem(R.id.nav_map);
    }

    private void setSettingsFragment() {
        setFragment(new FragmentSettings(), "");
        navigationView.setCheckedItem(R.id.nav_settings);
    }

    private void setDevelopersFragment() {
        setFragment(new FragmentDevelopers(), "");
    }

    private void choseHistoryCity() {
        final int[] chosen = {-1};
        citySource = new CitySource(interfaceDAO);
        List<HistoryCity> itemss = citySource.getHistoryCities();
        String CELSIUS = "\u2103";
        final String[] items = new String[itemss.size()];
        for (int i = 0; i < itemss.size(); i++) {
            items[i] = itemss.get(i).city + " " + itemss.get(i).temp + " " + CELSIUS + " " + itemss.get(i).date;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.historyMyCities)
                .setSingleChoiceItems(items, chosen[0], (dialogInterface, item) -> chosen[0] = item)
                .setNegativeButton(R.string.dismiss_from_choose_history_city, (dialogInterface, i) -> Log.d("alert", getResources().getString(R.string.dismiss_from_choose_history_city)))
                .setPositiveButton("Ок", (dialogInterface, i) -> {
                    if (chosen[0] == -1) {
                        Log.d("alert", getResources().getString(R.string.dismiss_from_choose_history_city));
                    } else {
                        singletonForSaveState.setCity(itemss.get(chosen[0]).city);
                        singletonForSaveState.setLatLng(null);
                        singletonForSaveState.setIsCity(true);
                        FragmentWeather fragmentWeather = singletonForSaveState.getFragmentWeather();
                        if (fragmentWeather != null && fragmentWeather.isVisible()) {
                            fragmentWeather.startCreateMainScreen();
                        } else {
                            setHomeFragment();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void clearHistoryCity() {
        citySource = new CitySource(interfaceDAO);
        citySource.clearHistory();
    }
}