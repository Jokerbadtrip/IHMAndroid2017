package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.ProductListAdapter;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.sorting.SortByName;
import fr.unice.polytech.ihmandroid.sorting.SortByPrice;

/**
 * Created by MSI on 08/05/2017.
 */

public class ProductListFragment extends Fragment {

    private ListView listView;
    private Spinner sorting;


    private String TAG = "ProductListFragment";


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
        sorting = (Spinner) view.findViewById(R.id.sorting_choice);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        final List<Product> products = bundle.getParcelableArrayList("products");

        final ProductListAdapter adapter = new ProductListAdapter(this.getContext(), products);

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

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sorting_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sorting.setAdapter(spinnerAdapter);

        sorting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sortingType = parent.getSelectedItem().toString();

                Log.d(TAG, "item chosen : "+sortingType);

                List<Product> newList = new ArrayList<Product>();

                if (sortingType.equals(getString(R.string.priceC))){

                    Log.d(TAG, "sorting by price ascending");
                    newList = SortByPrice.sort(products);
                }
                if (sortingType.equals(getString(R.string.priceD))){

                    Log.d(TAG, "sorting by price descending");
                    newList = SortByPrice.sort(products);
                    Collections.reverse(products);
                }
                if (sortingType.equals(getString(R.string.nameC))){

                    Log.d(TAG, "sorting by names ascending");
                    newList = SortByName.sort(products);
                }
                if (sortingType.equals(getString(R.string.nameD))){

                    Log.d(TAG, "sorting by names descending");
                    newList = SortByName.sort(products);
                    Collections.reverse(products);
                }
                if (sortingType.equals(getString(R.string.defaultSort))){
                    Log.d(TAG, "no sorting chosen");
                    newList = products;
                }



                adapter.refreshList(newList);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }






}
