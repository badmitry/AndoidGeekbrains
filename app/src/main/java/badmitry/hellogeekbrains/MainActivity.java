package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import badmitry.hellogeekbrains.fragments.FragmentChooseCities;
import badmitry.hellogeekbrains.fragments.FragmentDevelopers;
import badmitry.hellogeekbrains.fragments.FragmentOfHistory;
import badmitry.hellogeekbrains.fragments.FragmentSettings;
import badmitry.hellogeekbrains.fragments.FragmentWeather;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SingletonForSaveState singletonForSaveState = SingletonForSaveState.getInstance();
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
        setOnClickForSideMenuItems();
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
                setHistoryFragment();
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
        setFragment(fragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setOnClickForSideMenuItems() {
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
        setFragment(new FragmentChooseCities());
    }

    private void setSettingsFragment() {
        setFragment(new FragmentSettings());
    }

    private void setDevelopersFragment() {
        setFragment(new FragmentDevelopers());
    }

    private void setHistoryFragment() {
        setFragment(new FragmentOfHistory());
    }
}