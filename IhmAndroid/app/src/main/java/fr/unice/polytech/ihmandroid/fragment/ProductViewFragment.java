package fr.unice.polytech.ihmandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.CategoryViewAdapter;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 26/04/2017.
 */

public class ProductViewFragment extends Fragment {



    private GridView gridView;

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

        gridView = (GridView) getView().findViewById(R.id.grid_categories);

        List<Product> products = new ArrayList<>();
        products.add(new Product("","Produit1", "", "", 14.4, "description"));
        products.add(new Product("","Produit2", "", "", 19.99, "description"));

        CategoryViewAdapter categoryAdapter = new CategoryViewAdapter(this.getContext());
        gridView.setAdapter(categoryAdapter);



    }

}
