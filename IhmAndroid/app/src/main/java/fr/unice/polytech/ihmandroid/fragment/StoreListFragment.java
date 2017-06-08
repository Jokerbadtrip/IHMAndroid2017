package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreViewAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Store;
import fr.unice.polytech.ihmandroid.sorting.SortStoreByCity;
import fr.unice.polytech.ihmandroid.sorting.SortStoreByName;

/**
 * Created by MSI on 26/04/2017.
 */

public class StoreListFragment extends Fragment {


    private ListView listView;
    private Spinner spinner;

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
        listView = (ListView) rootView.findViewById(R.id.list_content);
        spinner = (Spinner) rootView.findViewById(R.id.sorting_choice);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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



            stores.addAll(db.buildInventories(db.buildStores(), db.buildProducts()));

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
                Fragment fragment = StoreDetailedFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putParcelable("store", store);

                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
                
            }
        });


        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sorting_store, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        final ArrayList<Store> finalStores = stores;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortingType = parent.getSelectedItem().toString();

                List<Store> newList = new ArrayList<Store>();

                if (sortingType.equals(getString(R.string.nameC))){
                    newList = SortStoreByName.sort(finalStores);
                }
                if (sortingType.equals(getString(R.string.nameD))){
                    newList = SortStoreByName.sort(finalStores);
                    Collections.reverse(newList);
                }
                if (sortingType.equals(getString(R.string.cityC))){
                    newList = SortStoreByCity.sort(finalStores);
                }
                if (sortingType.equals(getString(R.string.cityD))){
                    newList = SortStoreByCity.sort(finalStores);
                    Collections.reverse(newList);
                }


                adapter.refreshList(newList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
