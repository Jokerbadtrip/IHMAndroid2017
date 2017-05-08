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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreViewAdapter;
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
        List<Store> stores = new ArrayList<>();
        stores.add(new Store("","name","Marseille","","",""));
        stores.add(new Store("","nom","Lyon","","",""));
        final StoreViewAdapter adapter = new StoreViewAdapter(this.getContext(), stores);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Serializable product = (Serializable) parent.getItemAtPosition(position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = StoreDetailedViewFragment.newInstance();

                Bundle bundle = new Bundle();

                if (product!=null){
                    bundle.putSerializable("product", product);
                    Log.e("product", "is not null");
                }
                else{
                    Log.e("product", "is null");
                }

                fragment.setArguments(bundle);
                ft.replace(R.id.store_detailed_view, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

}
