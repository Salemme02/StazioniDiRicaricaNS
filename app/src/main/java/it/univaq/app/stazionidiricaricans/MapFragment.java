package it.univaq.app.stazionidiricaricans;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.univaq.app.stazionidiricaricans.databinding.FragmentMapBinding;
import it.univaq.app.stazionidiricaricans.model.Charger;
import it.univaq.app.stazionidiricaricans.service.LocationHelper;
import it.univaq.app.stazionidiricaricans.service.MainViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private FragmentMapBinding binding;
    private GoogleMap map;
    private MainViewModel mainViewModel;
    private Marker myMarker;
    private FloatingActionButton centerButton;

    private List<Charger> chargers = new ArrayList<Charger>();
    private List<Marker> markers = new ArrayList<Marker>();

    private ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean granted) {
                    if (granted) {
                        LocationHelper.start(requireContext(), MapFragment.this);
                    } else {
                        Toast.makeText(
                                        requireContext(),
                                        R.string.LocationRequiredMessage,
                                        Toast.LENGTH_LONG)
                                .show();

                    }
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        //recuperiamo il frammento tramite l'api google
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentMap); //dato che voglio il manager del frammento devo fare il get child

        centerButton = binding.centerButton;
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myMarker.getPosition(), 6));
            }
        });

        mapFragment.getMapAsync(this);

        //chiede i permessi
        int fineLocationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (fineLocationPermission == PackageManager.PERMISSION_DENIED) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //chiede i permessi
        int fineLocationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (fineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            LocationHelper.start(requireContext(), this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        LocationHelper.stop(requireContext(), this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                Charger charger = (Charger) marker.getTag();
                if (charger != null) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DetailActivity.EXTRA_CHARGER, charger);
                    Navigation.findNavController(requireView()).navigate(R.id.action_menu_map_to_detailActivity, bundle);

                }
            }
        });
        showMarkers();

    }


    private void showMarkers() {
        mainViewModel.getChargers().observe(getViewLifecycleOwner(), chargers -> { //dal mainViewModel otteniamo la lista delle stazioni di ricarica
            this.chargers = chargers;

            map.clear();
            chargers.forEach(this::createCharger);

        }); //observe perché la lista di charger può mutare
    }


    private void createCharger(Charger charger) {   //i markerOptions servono per caricare le informazioni sulla mappa
        MarkerOptions options = new MarkerOptions();
        options.title(charger.getOperator());
        options.position(new LatLng(charger.getLatitude(), charger.getLongitude()));
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        Marker marker = map.addMarker(options);
        marker.setTag(charger);
        markers.add(marker);
    }


    @Override
    public void onLocationChanged(@NonNull Location myLocation) {
        //map.clear();

        LatLng currentPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

//        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
//        bounds.include(currentPosition);

        if (myMarker == null) {
            MarkerOptions myMarkerOptions = new MarkerOptions();
            myMarkerOptions.title("My Position");
            myMarkerOptions.position(currentPosition);
            myMarker = map.addMarker(myMarkerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myMarker.getPosition(), 6));
        } else {
            myMarker.setPosition(currentPosition);
        }

//        new Thread(() -> {
//
//            if (!chargers.isEmpty()) {
//                for (Charger c : chargers) {
//
//                    Location chargerLocation = new Location("Charger");
//                    chargerLocation.setLatitude(c.getLatitude());
//                    chargerLocation.setLongitude(c.getLongitude());
//                    if (chargerLocation.distanceTo(myLocation) >= 200000) continue;
//
//                    bounds.include(new LatLng(chargerLocation.getLatitude(), chargerLocation.getLongitude()));
//                }
//
//            }
////            moveCamera deve essere eseguito sul main thread
//            binding.getRoot().post(() -> {
//                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 16));
//            });
//
//        }).start();

    }

}
