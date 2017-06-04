package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 03/06/2017.
 */

public class StoreMapFragment extends Fragment implements OnMapReadyCallback{


    private TextView storeAddress;
    private MapView map;
    private Store store;

    private final String TAG = "StoreMapFragment";

    public StoreMapFragment() {
    }

    public static Fragment newInstance(){
        return new StoreMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        findViewById(rootView);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);
        store = getArguments().getParcelable("store");
        return rootView;
    }

    private void findViewById(View rootView) {

        storeAddress = (TextView) rootView.findViewById(R.id.store_address);
        map = (MapView) rootView.findViewById(R.id.map);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storeAddress.setText(store.getAdress()+ "\n" + store.getCity() + ", "+store.getCityNumber());

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng coord = new LatLng(store.getLat(),store.getLng());
        googleMap.addMarker(new MarkerOptions()
        .position(coord)
        .title(store.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 16));
        map.onResume();

    }
}
