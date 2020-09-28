package badmitry.hellogeekbrains.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
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
import badmitry.hellogeekbrains.location.LocTracker;

public class FragmentMap extends Fragment implements OnMapReadyCallback {
    private SingletonForSaveState singletonForSaveState;
    private GoogleMap gMap = null;
    LocationManager locationManager;
    @SuppressLint("StaticFieldLeak")
    private static View view;
    private Button buttonOk;
    private Button buttonPlus;
    private Button buttonMinus;
    private LatLng latLng;
    private LocTracker locTracker;

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
        setOnButtonOkClkListener();
        setOnButtonMinusClkListener();
        setOnButtonPlusClkListener();
    }

    private void initViews(View view) {
        buttonOk = view.findViewById(R.id.select_marker);
        buttonPlus = view.findViewById(R.id.plus);
        buttonMinus = view.findViewById(R.id.minus);
    }

    private void setOnButtonOkClkListener() {
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

    private void setOnButtonPlusClkListener() {
        buttonPlus.setOnClickListener(view -> gMap.animateCamera(CameraUpdateFactory.zoomIn()));
    }

    private void setOnButtonMinusClkListener() {
        buttonMinus.setOnClickListener(view -> gMap.animateCamera(CameraUpdateFactory.zoomOut()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Location location;
        gMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            gMap.setMyLocationEnabled(true);
            if (latLng != null) {
                gMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
            } else {
                if (locTracker == null) {
                    locTracker = new LocTracker(requireActivity());
                }
                location = locTracker.takeLocation();
                LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                moveCamera(newLatLng, 8);
            }
            gMap.setOnMapClickListener(latLng2 -> {
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(latLng2).title("Marker"));
                latLng = latLng2;
            });
        }
    }

    private void moveCamera(LatLng target, float zoom) {
        if (target == null || zoom < 1) return;
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(target, zoom));
    }
}
