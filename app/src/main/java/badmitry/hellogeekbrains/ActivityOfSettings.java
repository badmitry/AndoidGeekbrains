package badmitry.hellogeekbrains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import java.util.Objects;

public class ActivityOfSettings extends AppCompatActivity {

    private Button buttonOk;
    private CheckBox checkBoxSpeedOfWind;
    private CheckBox checkBoxPressure;
    private boolean showSpeedOfWind;
    private boolean showPressure;
    private boolean isDarkTheme;
    private RadioButton radioBtnDarkTheme;
    private RadioButton radioBtnLightTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initViews();
        setCheckBoxes();
        setOnButtonOkClkBehaviour();
        showBackBtn();
    }

    private void setCheckBoxes() {
        showSpeedOfWind = getIntent().getBooleanExtra("showSpeedOfWind", false);
        showPressure = getIntent().getBooleanExtra("showPressure", false);
        isDarkTheme = getIntent().getBooleanExtra("isDarkTheme", false);
        radioBtnDarkTheme.setChecked(isDarkTheme);
        radioBtnLightTheme.setChecked(!isDarkTheme);
        checkBoxSpeedOfWind.setChecked(showSpeedOfWind);
        checkBoxPressure.setChecked(showPressure);
    }

    private void setOnButtonOkClkBehaviour() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSettingsOnMainLayout();
            }
        });
    }

    private void initViews() {
        buttonOk = findViewById(R.id.buttonOk);
        checkBoxSpeedOfWind = findViewById(R.id.checkBoxSpeedOfWind);
        checkBoxPressure = findViewById(R.id.checkBoxPressure);
        radioBtnDarkTheme = findViewById(R.id.radioBtnDarkTheme);
        radioBtnLightTheme = findViewById(R.id.radioBtnLightTheme);
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
            changeSettingsOnMainLayout();
        }
        return true;
    }

    private void changeSettingsOnMainLayout() {
        Intent intent = new Intent();
        showPressure = checkBoxPressure.isChecked();
        showSpeedOfWind = checkBoxSpeedOfWind.isChecked();
        isDarkTheme = radioBtnDarkTheme.isChecked();
        intent.putExtra("showSpeedOfWind", showSpeedOfWind);
        intent.putExtra("showPressure", showPressure);
        intent.putExtra("isDarkTheme", isDarkTheme);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void setTheme(int resid) {
        if (isDarkTheme) {
            super.setTheme(R.style.darkStyle);
        }else{
            super.setTheme(R.style.lightStyle);
        }
    }

}