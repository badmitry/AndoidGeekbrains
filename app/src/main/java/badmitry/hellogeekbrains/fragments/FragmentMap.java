package badmitry.hellogeekbrains.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import badmitry.hellogeekbrains.MainActivity;
import badmitry.hellogeekbrains.R;
import badmitry.hellogeekbrains.SingletonForSaveState;

public class FragmentMap extends Fragment implements OnMapReadyCallback {
    private SingletonForSaveState singletonForSaveState;
    private GoogleMap gMap = null;
    LocationManager locationManager;
    @SuppressLint("StaticFieldLeak")
    private static View view;
    private Button buttonOk;
    private LatLng latLng;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
            return view;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        singletonForSaveState = SingletonForSaveState.getInstance();
        latLng = singletonForSaveState.getLatLng();
        locationManager = singletonForSaveState.getLocationManager();
        if (getActivity() != null) {
            MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
        initViews(view);
        setOnButtonClkListener();
    }

    private void initViews(View view) {
        buttonOk = view.findViewById(R.id.select_marker);
    }

    private void setOnButtonClkListener() {
        buttonOk.setOnClickListener(view -> {
            if (latLng != null) {
                singletonForSaveState.setLatLng(latLng);
                MainActivity ma = (MainActivity) getActivity();
                assert ma != null;
                ma.setHomeFragment();
            } else {
                Toast.makeText(requireActivity().getApplicationContext(), R.string.choose_point_on_map,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            gMap.setMyLocationEnabled(true);
            if (latLng != null) {
                gMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
            gMap.setOnMapClickListener(latLng2 -> {
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(latLng2).title("Marker"));
                latLng = latLng2;
            });
        }
    }
}
