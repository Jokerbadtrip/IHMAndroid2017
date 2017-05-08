package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.ImageSlideAdapter;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.utils.PageIndicator;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * Created by MSI on 02/05/2017.
 */




public class PromotedViewFragment extends Fragment {

    private ViewPager viewPager;
    private PageIndicator indicator;
    private List<Product> products;
    private boolean stopSliding = false;

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

    private Runnable animateViewPager;
    private Handler handler;

    public PromotedViewFragment() {
    }


    public static Fragment newInstance(){
        Fragment fragment = new PromotedViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promoted_view_layout, container, false);

        return rootView;
    }



    @Override
    public void onResume() {
        if (products == null) {
            products = new ArrayList<>();
            products.add(new Product("","Produit1", "", "", 14.4, "description"));
            products.add(new Product("","Produit2", "", "", 19.99, "description"));
        } else {
            viewPager.setAdapter(new ImageSlideAdapter(this.getActivity(), products,
                    PromotedViewFragment.this));


        }
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.view_pager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (products != null && products.size() != 0) {
                            stopSliding = false;
                            runnable(products.size());
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });


    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (viewPager.getCurrentItem() == size - 1) {
                        viewPager.setCurrentItem(0);
                    } else {
                        viewPager.setCurrentItem(
                                viewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }



}