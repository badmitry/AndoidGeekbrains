package badmitry.hellogeekbrains.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterChooseCity;
import badmitry.hellogeekbrains.adapterRLV.OnItemClicker;
import badmitry.hellogeekbrains.roomFavoritesCities.FavoriteCity;
import badmitry.hellogeekbrains.roomFavoritesCities.FavoritesInterfaceDAO;
import badmitry.hellogeekbrains.roomFavoritesCities.FavoritesSource;
import badmitry.hellogeekbrains.roomHistoryCities.App;

public class FragmentChooseCities extends Fragment implements OnItemClicker {

    private EditText editTextInputCity;
    private Button buttonAddToFavorites;
    private SingletonForSaveState singletonForSaveState;
    private RecyclerView recyclerView;
    private List<FavoriteCity> listData = new ArrayList<>();
    private FavoritesSource favoritesSource;
    private FavoritesInterfaceDAO favoritesInterfaceDAO;
    private AdapterChooseCity adapter;
    private LinearLayoutManager linearLayoutManager;

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
        setOnBtnAddToFavorites();
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
        buttonAddToFavorites = view.findViewById(R.id.button_add_to_favorites);
        favoritesInterfaceDAO = App.getInstance().getFavoritesInterfaceDao();
        favoritesSource = new FavoritesSource(favoritesInterfaceDAO);
        listData = favoritesSource.getFavoritesCities();
    }

    private void initList() {
        listData = favoritesSource.getFavoritesCities();
        linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
        adapter = new AdapterChooseCity(listData, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void updateList() {
        listData = favoritesSource.getFavoritesCities();
        adapter = new AdapterChooseCity(listData, this);
        recyclerView.invalidate();
    }

    private void setOnBtnAddToFavorites() {
        buttonAddToFavorites.setOnClickListener(view -> {
            String text = editTextInputCity.getText().toString();
            FavoriteCity favoriteCity = new FavoriteCity();
            favoriteCity.city = text;
            favoritesSource.addCity(favoriteCity);
            initList();
        });
    }

    private void setEditTextFromChoseCityBehavior() {
        editTextInputCity.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                    (i == KeyEvent.KEYCODE_ENTER)) {
                String text = editTextInputCity.getText().toString();
                changeCityOnMainLayout(text);
                return true;
            }
            return false;
        });
        editTextInputCity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
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
                .getSharedPreferences(getString(R.string.settings_shared_preferences), Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        singletonForSaveState.setIsCity(true);
        editor.putString("City", text);
        editor.putBoolean(getString(R.string.selected_city), true);
        editor.commit();
        singletonForSaveState.setCity(text);
        singletonForSaveState.getHistory().add(text);
        singletonForSaveState.setLatLng(null);
        MainActivity ma = (MainActivity) this.getActivity();
        assert ma != null;
        ma.setHomeFragment();
        editTextInputCity.setText("");

    }
}