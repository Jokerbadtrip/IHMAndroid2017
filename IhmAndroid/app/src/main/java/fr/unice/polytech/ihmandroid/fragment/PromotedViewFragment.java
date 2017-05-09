package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;


import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.PromotedProductPagerAdapter;
import fr.unice.polytech.ihmandroid.model.Product;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by MSI on 09/05/2017.
 */

public class PromotedViewFragment extends Fragment {


    private FeatureCoverFlow coverFlow;
    private List<Product> promoted_products;

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
        return rootView;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        promoted_products = new ArrayList<>();
        promoted_products.add(new Product("","Prod","maison","",18.0,"lel"));
        promoted_products.add(new Product("","Prod2","electroménager","",180.0,"lel"));

        coverFlow.setAdapter(new PromotedProductPagerAdapter(this.getActivity(), promoted_products, getActivity().getSupportFragmentManager().beginTransaction()));


    }




}
