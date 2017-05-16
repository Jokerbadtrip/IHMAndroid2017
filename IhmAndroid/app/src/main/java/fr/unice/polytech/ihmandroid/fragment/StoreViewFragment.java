package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreViewAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 26/04/2017.
 */

public class StoreViewFragment extends Fragment {


    private ListView listView;

    public StoreViewFragment() {
    }


    public static Fragment newInstance() {
        StoreViewFragment fragment = new StoreViewFragment();
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

        List<Store> stores = new ArrayList<>();
        stores.addAll(db.getStores());

        db.close();


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

                if (store!=null){
                    bundle.putSerializable("store", store);
                    Log.e("store", "is not null");
                }
                else{
                    Log.e("store", "is null");
                }

                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
                
            }
        });
    }

}
