package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.StoreInventoryAdapter;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 08/05/2017.
 */

public class ProductListFragment extends Fragment {

    ListView listView;

    public ProductListFragment() {
    }

    public static Fragment newInstance(){
        return new ProductListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_list, container, false);
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
        List<Product> products = bundle.getParcelableArrayList("products");

        final StoreInventoryAdapter adapter = new StoreInventoryAdapter(this.getContext(), products);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = ProductDetailedFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putParcelable("product", product);

                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    }


}
