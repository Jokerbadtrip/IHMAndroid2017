package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;


import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.PromotedProductAdapter;
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


        promoted_products = new ArrayList<>();
        promoted_products.add(new Product("","Prod","maison","",18.0,"Views are specific to android development and are just Objects. View is the super class(or Parent class) that all other child class views( like Button, TextView, ImageView) inherit from. Having a View class and its child classes also allows you to interact with your UI inside of your java code. LaunchActivity extends Activity is an example of setting up that inheritance. What you are telling your compiler in that line is that you want all the functionality that Activity has to be available to LaunchActivity.La British Broadcasting Corporation fondée en 1922 est une société de production et de diffusion de programmes de radio-télévision britannique. Ayant son siège au Royaume-Uni, c’est un non-departmental public body chargé des médiasScreenshots of a well known program that locks computers and demands a payment in Bitcoin have been shared online by parties claiming to be affected.\n" +
                "There have been reports of infections in Spain, Italy, Portugal, Russia and Ukraine.\n" +
                "It is not yet clear whether the attacks are all connected.The images of Europe taken from the International Space Station (ISS) have notched up tens of thousands of reactions on Facebook.\n" +
                "Belgium can be seen glowing more brightly than its neighbours.\n" +
                "The country's dense road network enjoys near-total streetlight coverage, with lights kept on throughout the night.\n" +
                "It uses about 2.2 million bulbs illuminate Belgium's roads - with 186 bulbs per "));
        promoted_products.add(new Product("","Prod2","electroménager","",180.0,"lel"));

        coverFlow.setAdapter(new PromotedProductAdapter(this.getActivity(), promoted_products, getActivity().getSupportFragmentManager().beginTransaction()));


    }




}
