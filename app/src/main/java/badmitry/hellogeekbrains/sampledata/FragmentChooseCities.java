package badmitry.hellogeekbrains.sampledata;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

public class FragmentChooseCities extends Fragment {

    private EditText editTextInputCity;
    private ListView listViewOfCities;
    private TextView emptyTextView;
    private SingletonForSaveState singletonForSaveState;

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

    private void initViews(@NonNull View view) {
        editTextInputCity = view.findViewById(R.id.inputCity);
        listViewOfCities = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
        singletonForSaveState = SingletonForSaveState.getInstance();
    }

    private void initList() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.cities,
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
        singletonForSaveState.setCity(text);
        singletonForSaveState.getFragmentWeather().startCreateMainScreen();

    }
}