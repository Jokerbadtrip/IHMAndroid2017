package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.PromotedProductAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Product;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by MSI on 09/05/2017.
 */

public class PromotedViewFragment extends Fragment {


    private FeatureCoverFlow coverFlow;


    public PromotedViewFragment() {
    }

    public static Fragment newInstance(){
        return new PromotedViewFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promoted_products_view, container,false);
        coverFlow = (FeatureCoverFlow) rootView.findViewById(R.id.coverflow);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int h = (int) Math.round(dm.heightPixels*0.60);
        int w = (int) Math.round(dm.widthPixels*0.80);

        coverFlow.setCoverHeight(h);
        coverFlow.setCoverWidth(w);
        coverFlow.setReflectionOpacity(0);
        return rootView;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        db.buildProducts();



        List<Product> promoted_products = buildPromotedProducts(db.getProducts());

        db.close();


        coverFlow.setAdapter(new PromotedProductAdapter(this.getActivity(), promoted_products, getActivity().getSupportFragmentManager().beginTransaction()));


    }

    private List<Product> buildPromotedProducts(List<Product> products){
        List<Product> promoted_products = new ArrayList<>();
        for (Product product : products) {
            if (product.isPromoted()){
                promoted_products.add(product);
            }
        }
        return promoted_products;
    }




}
