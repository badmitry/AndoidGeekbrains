package badmitry.hellogeekbrains.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterChooseCity;
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
        AdapterChooseCity adapter = new AdapterChooseCity(listData, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setEditTextFromChoseCityBehavior() {
        editTextInputCity.setOnKeyListener((view, i, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                    (i == KeyEvent.KEYCODE_ENTER)) {
                String text = editTextInputCity.getText().toString();
                changeCityOnMainLayout(text);
                return true;
            }
            return false;
        });
    }

    @SuppressLint("ApplySharedPref")
    public void changeCityOnMainLayout(String text) {
        SharedPreferences sharedPreferences = this.requireActivity()
                .getSharedPreferences("Settings", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("City", text);
        editor.commit();
        singletonForSaveState.setCity(text);
        singletonForSaveState.getHistory().add(text);
        MainActivity ma = (MainActivity) this.getActivity();
        assert ma != null;
        ma.setHomeFragment();
        editTextInputCity.setText("");

    }
}