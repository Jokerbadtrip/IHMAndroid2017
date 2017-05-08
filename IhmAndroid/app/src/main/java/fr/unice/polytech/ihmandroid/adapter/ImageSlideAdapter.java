package fr.unice.polytech.ihmandroid.adapter;



import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.fragment.PromotedViewFragment;
import fr.unice.polytech.ihmandroid.model.Product;


public class ImageSlideAdapter extends PagerAdapter {



    FragmentActivity activity;
    List<Product> products;
    PromotedViewFragment homeFragment;

    public ImageSlideAdapter(FragmentActivity activity, List<Product> products, PromotedViewFragment homeFragment) {
        this.activity = activity;
        this.products = products;
        this.homeFragment = homeFragment;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.promoted_img_layout, container, false);

        ImageView mImageView = (ImageView) view
                .findViewById(R.id.image_display);

        mImageView.setImageBitmap(BitmapFactory.decodeFile(products.get(position).getImage()));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
