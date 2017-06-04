package fr.unice.polytech.ihmandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Event;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 04/06/2017.
 */

public class EventDetailedFragment extends Fragment implements OnMapReadyCallback{


    private TextView title, description;
    private MapView mapView;
    private Button shareButton;

    private Event event;

    public EventDetailedFragment() {
    }

    public static Fragment newInstance(){
        return new EventDetailedFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_detailed_layout, container, false);
        findViewById(view);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        event = getArguments().getParcelable("event");

        return view;

    }

    private void findViewById(View view) {

        title = (TextView) view.findViewById(R.id.event_title);
        description = (TextView) view.findViewById(R.id.event_description);
        mapView = (MapView) view.findViewById(R.id.map);
        shareButton = (Button) view.findViewById(R.id.share_button);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title.setText(event.getName());
        description.setText(event.getDescription());
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, event.getName());
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.macrogamia.com/events/"+ event.getName());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Partager avec : "));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



        LatLng coord = new LatLng(event.getLat(), event.getLng());

        googleMap.addMarker(new MarkerOptions()
        .title(event.getName())
        .position(coord));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 16));
        mapView.onResume();

    }
}
