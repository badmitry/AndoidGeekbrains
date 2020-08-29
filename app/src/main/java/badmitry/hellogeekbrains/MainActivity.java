package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import badmitry.hellogeekbrains.fragments.FragmentChooseCities;
import badmitry.hellogeekbrains.fragments.FragmentDevelopers;
import badmitry.hellogeekbrains.fragments.FragmentSettings;
import badmitry.hellogeekbrains.fragments.FragmentWeather;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private SingletonForSaveState singletonForSaveState;
    private final String homeFragment = "HomeFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        singletonForSaveState = SingletonForSaveState.getInstance();
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
        setHomeFragment();
        setOnClickForSlideMenuItems();
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

    private void setOnClickForSlideMenuItems() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_weather: {
                        setHomeFragment();
                        drawer.closeDrawers();
                        break;
                    }
                    case R.id.nav_city: {
                        setChooseCityFragment();
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
            }
        });
    }

    public void setChooseCityFragment() {
        setFragment(new FragmentChooseCities(), "");
        navigationView.setCheckedItem(R.id.nav_city);
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
        String[] itemss = new String[singletonForSaveState.getHistory().size()];
        itemss = singletonForSaveState.getHistory().toArray(itemss);
        final String[] items = itemss;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.historyMyCities)
                .setSingleChoiceItems(items, chosen[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        chosen[0] = item;
                    }
                })
                .setNegativeButton(R.string.dismiss_from_choose_history_city, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("alert", getResources().getString(R.string.dismiss_from_choose_history_city));
                    }
                })
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (chosen[0] == -1) {
                            Log.d("alert", getResources().getString(R.string.dismiss_from_choose_history_city));
                        } else {
                            singletonForSaveState.setCity(items[chosen[0]]);
                            @SuppressLint("ResourceType") FragmentWeather fragmentWeather = (FragmentWeather)
                                    getSupportFragmentManager().findFragmentByTag(homeFragment);
                            if (fragmentWeather != null && fragmentWeather.isVisible()) {
                                singletonForSaveState.getFragmentWeather().startCreateMainScreen();
                            }else {
                                setHomeFragment();
                            }
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}