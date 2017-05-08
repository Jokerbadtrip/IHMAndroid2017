package fr.unice.polytech.ihmandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import fr.unice.polytech.ihmandroid.fragment.SlideshowTileFragment;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 26/04/2017.
 */

public class SlideshowPagerAdapter extends FragmentPagerAdapter {


    private List<Product> products;

    public SlideshowPagerAdapter(FragmentManager fm, List<Product> products) {
        super(fm);
        this.products=products;
    }

    @Override
    public Fragment getItem(int position) {
        Product product = products.get(position);
        return SlideshowTileFragment.newInstance(product);
    }

    @Override
    public int getCount() {
        return products.size();
    }
}
