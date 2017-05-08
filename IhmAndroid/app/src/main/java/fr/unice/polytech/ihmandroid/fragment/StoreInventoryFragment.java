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

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreInventoryAdapter;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 08/05/2017.
 */

public class StoreInventoryFragment extends Fragment {

    ListView listView;

    public StoreInventoryFragment() {
    }

    public static Fragment newInstance(){
        return new StoreInventoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.store_inventory_layout, container, false);
        findViewById(rootView);
        return rootView;
    }

    private void findViewById(View view){
        listView=(ListView) view.findViewById(R.id.inventory_list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        Store store = (Store) bundle.getSerializable("store");

        final StoreInventoryAdapter adapter = new StoreInventoryAdapter(this.getContext(), store.getInventory());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = ProductDetailedFragment.newInstance();

                Bundle bundle = new Bundle();

                if (product!=null){
                    bundle.putSerializable("product", product);
                    Log.e("product", "is not null");
                }
                else{
                    Log.e("product", "is null");
                }

                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    }


}
