package badmitry.hellogeekbrains.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

public class FragmentSettings extends Fragment {
    private Button buttonOk;
    private CheckBox checkBoxSpeedOfWind;
    private CheckBox checkBoxPressure;
    private RadioButton radioBtnDarkTheme;
    private RadioButton radioBtnLightTheme;
    private SingletonForSaveState singletonForSaveState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        singletonForSaveState = SingletonForSaveState.getInstance();
        initViews(view);
        setCheckBoxes();
        setOnButtonOkClkBehaviour();
    }

    private void setCheckBoxes() {
        radioBtnDarkTheme.setChecked(singletonForSaveState.isDarkTheme());
        radioBtnLightTheme.setChecked(!singletonForSaveState.isDarkTheme());
        checkBoxSpeedOfWind.setChecked(singletonForSaveState.isShowSpeedOfWind());
        checkBoxPressure.setChecked(singletonForSaveState.isShowPressure());
    }

    private void setOnButtonOkClkBehaviour() {
        buttonOk.setOnClickListener(view -> changeSettingsOnMainLayout());
    }

    private void initViews(View view) {
        buttonOk = view.findViewById(R.id.buttonOk);
        checkBoxSpeedOfWind = view.findViewById(R.id.checkBoxSpeedOfWind);
        checkBoxPressure = view.findViewById(R.id.checkBoxPressure);
        radioBtnDarkTheme = view.findViewById(R.id.radioBtnDarkTheme);
        radioBtnLightTheme = view.findViewById(R.id.radioBtnLightTheme);
    }

    @SuppressLint("ApplySharedPref")
    private void changeSettingsOnMainLayout() {
        SharedPreferences sharedPreferences = this.requireActivity()
                .getSharedPreferences("Settings", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        singletonForSaveState.setShowPressure(checkBoxPressure.isChecked());
        editor.putBoolean(getString(R.string.show_pressure), checkBoxPressure.isChecked());
        singletonForSaveState.setShowSpeedOfWind(checkBoxSpeedOfWind.isChecked());
        editor.putBoolean(getString(R.string.show_speed), checkBoxSpeedOfWind.isChecked());
        singletonForSaveState.setDarkTheme(radioBtnDarkTheme.isChecked());
        editor.putBoolean(getString(R.string.is_dark_theme), radioBtnDarkTheme.isChecked());
        editor.commit();
        MainActivity ma = (MainActivity) this.getActivity();
        assert ma != null;
        ma.recreate();
        ma.setHomeFragment();
    }
}
