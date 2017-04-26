package fr.unice.polytech.ihmandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by MSI on 26/04/2017.
 */

public class SlideshowPagerAdapter extends FragmentPagerAdapter {


    private int count;

    public SlideshowPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
