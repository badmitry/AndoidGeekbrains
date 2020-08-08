package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Objects;

public class ChooseCityActivity extends AppCompatActivity {
    private EditText editTextInputCity;
    private boolean isDarkTheme;
    private ListView listViewOfCities;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);
        initViews();
        isDarkTheme = getIntent().getBooleanExtra("isDarkTheme", false);
        initList();
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
        listViewOfCities = findViewById(R.id.cities_list_view);
        emptyTextView = findViewById(R.id.cities_list_empty_view);
    }

    private void initList() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.cities,
                android.R.layout.simple_list_item_activated_1);
        listViewOfCities.setAdapter(adapter);

        listViewOfCities.setEmptyView(emptyTextView);

        listViewOfCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                changeCityOnMainLayout(text);
            }
        });
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