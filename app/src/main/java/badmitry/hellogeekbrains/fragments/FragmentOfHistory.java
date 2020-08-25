package badmitry.hellogeekbrains.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;
import badmitry.hellogeekbrains.adapterRLV.AdapterForHistory;
import badmitry.hellogeekbrains.adapterRLV.OnItemClicker;

public class FragmentOfHistory extends Fragment implements OnItemClicker {

    private SingletonForSaveState singletonForSaveState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_of_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        singletonForSaveState = SingletonForSaveState.getInstance();
        createList(view);
    }

    private void createList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewForHistory);
        AdapterForHistory adapterForHistory = new AdapterForHistory(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterForHistory);
    }

    @Override
    public void onItemClicked(String text) {
        changeCityOnMainLayout(text);
    }

    public void changeCityOnMainLayout(String text) {
        Log.d("TAG", "changeCityOnMainLayout: ");
        singletonForSaveState.setCity(text);
        MainActivity ma = (MainActivity) this.getActivity();
        assert ma != null;
        ma.setHomeFragment();
    }
}
