package badmitry.hellogeekbrains;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import badmitry.hellogeekbrains.adapterRLV.AdapterForDevelopers;

public class ActivityDevelopers extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SingletonForSaveState singletonForSaveState = SingletonForSaveState.getInstance();
        if (singletonForSaveState.isDarkTheme()) {
            setTheme(R.style.darkStyle);
        } else {
            setTheme(R.style.lightStyle);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers);
        showBackBtn();
        createList();
    }

    private void createList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewForDevelopers);
        data.addAll(Arrays.asList(getResources().getStringArray(R.array.developers)));
        AdapterForDevelopers adapterForDevelopers = new AdapterForDevelopers(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterForDevelopers);
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
        finish();
        return true;
    }
}
