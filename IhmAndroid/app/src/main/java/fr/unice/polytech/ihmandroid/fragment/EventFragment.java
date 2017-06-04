package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.EventAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Event;

/**
 * Created by MSI on 03/06/2017.
 */

public class EventFragment extends Fragment{

    private ListView eventsView;


    private List<Event> events; // Les évènement sont ajoutés en dur à cause d'une Out of Memory Error à la lecture de la base de donnée.

    {
       events =  new ArrayList<>();
        events.add(new Event("Essaie de la Nintendo Switch",
                "Essaie de la Nintendo switch. 4 joueurs. Réduction pour les testeurs. Macrogamia Mayol, Toulon, 3/07/2017, 8h00",
                (float) 43.119303, (float) 5.934419));
        events.add(new Event("Rencontre avec les developpeurs d'Ubisoft", "Rencontre joueurs/developpeurs Ubisoft.\n" +
                "Parc des Expositions, Paris, 25/08/2017, 17h30.\n", (float) 48.830462, (float) 2.286748));
        events.add(new Event("Réduction sur tous les produits en stock", "Réductions sur tous les produits du magasin.\n" +
                "MacroGamia Agorazur, Sophia-Antipolis, 19/06/2017, 8h00.", (float) 43.616354, (float) 7.055222));
    }

    private final String TAG = "EventFragment";

    public EventFragment() {
    }

    public static Fragment newInstance(){
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment, container, false);
        findViewById(view);





        return view;

    }

    private void findViewById(View view) {

        eventsView = (ListView) view.findViewById(R.id.event_list);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Throws Out of Memory Error
//        DatabaseHelper db = new DatabaseHelper(getContext());
//        Log.d(TAG,"Trying to open database");
//        try {
//            db.createDataBase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            db.openDataBase();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Log.d(TAG, "database created");
//        events = db.buildEvents();
//
//        db.close();



        EventAdapter adapter = new EventAdapter(getContext(), events);
        eventsView.setAdapter(adapter);
        eventsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable("event", event);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = EventDetailedFragment.newInstance();
                fragment.setArguments(bundle);

                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

    }


}
