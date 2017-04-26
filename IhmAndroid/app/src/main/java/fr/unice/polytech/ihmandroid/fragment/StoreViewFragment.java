package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreViewAdapter;
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


        ListView listView = (ListView) getView().findViewById(R.id.list_content);
        List<Store> stores = new ArrayList<>();
        stores.add(new Store("","name","Marseille","","",""));
        stores.add(new Store("","nom","Lyon","","",""));
        StoreViewAdapter adapter = new StoreViewAdapter(this.getContext(), stores);
        listView.setAdapter(adapter);
    }

}
