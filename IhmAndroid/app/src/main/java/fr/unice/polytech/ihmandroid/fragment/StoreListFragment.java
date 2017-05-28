package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreViewAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 26/04/2017.
 */

public class StoreListFragment extends Fragment {


    private ListView listView;

    public StoreListFragment() {
    }


    public static Fragment newInstance() {
        StoreListFragment fragment = new StoreListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stores_layout_fragment, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.list_content);

        Bundle bundle = getArguments();
        ArrayList<Store> stores = new ArrayList<>();

        if (bundle == null){
            DatabaseHelper db = new DatabaseHelper(getContext());
            try {
                db.createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                db.openDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.buildStores();
            db.buildProducts();
            db.buildInventories();


            stores.addAll(db.getStores());

            db.close();

        }
        else{
            stores = bundle.getParcelableArrayList("stores");
        }




        final StoreViewAdapter adapter = new StoreViewAdapter(this.getContext(), stores);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store) parent.getItemAtPosition(position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = StoreDetailedViewFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putParcelable("store", store);

                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
                
            }
        });
    }

}
