package badmitry.hellogeekbrains.fragments;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterRLV;
import badmitry.hellogeekbrains.adapterRLV.OnItemClicker;

public class FragmentChooseCities extends Fragment implements OnItemClicker {

    private EditText editTextInputCity;
    private SingletonForSaveState singletonForSaveState;
    private RecyclerView recyclerView;
    private ArrayList<String> listData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
        setEditTextFromChoseCityBehavior();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClicked(String text) {
        changeCityOnMainLayout(text);
    }

    private void initViews(@NonNull View view) {
        editTextInputCity = view.findViewById(R.id.inputCity);
        singletonForSaveState = SingletonForSaveState.getInstance();
        editTextInputCity = view.findViewById(R.id.inputCity);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void initList() {
        listData.addAll(Arrays.asList(getResources().getStringArray(R.array.cities)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
        AdapterRLV adapter = new AdapterRLV(listData, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setEditTextFromChoseCityBehavior() {
        editTextInputCity.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changeCityOnMainLayout(String text) {
        boolean checkCity = false;
        for (int j = 0; j < listData.size(); j++) {
            if (listData.get(j).toLowerCase().equals(text.toLowerCase())) {
                singletonForSaveState.setCity(listData.get(j));
                singletonForSaveState.getFragmentWeather().startCreateMainScreen();
                checkCity = true;
            }
        }
        if (!checkCity) {
            Snackbar.make(editTextInputCity,"City " + text + " don`t found", Snackbar.LENGTH_LONG).show();
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                requireActivity().finish();
            }
        }
    }

    public String getCityFromEditText() {
        return editTextInputCity.getText().toString();
    }
}