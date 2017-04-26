package fr.unice.polytech.ihmandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.ProductViewAdapter;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 26/04/2017.
 */

public class ProductViewFragment extends Fragment {


    private ViewPager viewPager;
    private ListView listView;

    public ProductViewFragment() {
    }


    public static Fragment newInstance(){
        Fragment fragment = new ProductViewFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.products_layout_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager = (ViewPager) getView().findViewById(R.id.slideshow_products);
        listView = (ListView) getView().findViewById(R.id.list_categories);

        List<Product> products = new ArrayList<>();
        products.add(new Product("","Produit1", "", 14.4, "description"));
        products.add(new Product("","Produit2", "", 19.99, "description"));

        ProductViewAdapter productAdapter = new ProductViewAdapter(this.getContext(), products);
        listView.setAdapter(productAdapter);



    }

}
