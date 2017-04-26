package fr.unice.polytech.ihmandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.unice.polytech.ihmandroid.R;

/**
 * Created by MSI on 26/04/2017.
 */

public class SlideshowTileFragment extends Fragment{

    private ImageView image;
    private TextView name;
    private TextView price;


    public SlideshowTileFragment() {
    }

    public static Fragment newInstance(){
        SlideshowTileFragment fragment = new SlideshowTileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.slideshow_tile_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        image = (ImageView) getView().findViewById(R.id.slideshow_image);
        name = (TextView) getView().findViewById(R.id.slideshow_name);
        price = (TextView) getView().findViewById(R.id.slideshow_price);


    }




    }
