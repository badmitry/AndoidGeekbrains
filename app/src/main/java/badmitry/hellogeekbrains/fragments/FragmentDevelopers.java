package badmitry.hellogeekbrains.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.adapterRLV.AdapterForDevelopers;

public class FragmentDevelopers extends Fragment {
    private ArrayList<String> data = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createList(view);
    }

    private void createList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewForDevelopers);
        PackageInfo packageInfo;
        int versionNumber = 0;
        String versionName = "0";
        try {
            packageInfo = requireActivity().getPackageManager().getPackageInfo(requireActivity().getPackageName(), 0);
            versionNumber = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        data.add("Version: " + versionNumber + "." + versionName);
        data.add(getString(R.string.list_developers));
        data.addAll(Arrays.asList(getResources().getStringArray(R.array.developers)));
        AdapterForDevelopers adapterForDevelopers = new AdapterForDevelopers(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterForDevelopers);
    }
}
