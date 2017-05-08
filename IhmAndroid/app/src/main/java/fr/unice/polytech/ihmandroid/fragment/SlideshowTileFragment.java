package fr.unice.polytech.ihmandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 26/04/2017.
 */

public class SlideshowTileFragment extends Fragment {

    private ImageView image;
    private TextView name;
    private TextView price;
    private static Product product;



    public SlideshowTileFragment() {

    }

    public static Fragment newInstance(Product prod){
        SlideshowTileFragment fragment = new SlideshowTileFragment();
        product=prod;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slideshow_tile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        image = (ImageView) getView().findViewById(R.id.slideshow_image);
        name = (TextView) getView().findViewById(R.id.slideshow_name);
        price = (TextView) getView().findViewById(R.id.slideshow_price);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));




    }




    }
