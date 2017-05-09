package fr.unice.polytech.ihmandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.unice.polytech.ihmandroid.R;

import fr.unice.polytech.ihmandroid.fragment.ProductDetailedFragment;
import fr.unice.polytech.ihmandroid.model.Product;


/**
 * Created by MSI on 09/05/2017.
 */

public class PromotedProductPagerAdapter extends BaseAdapter {


    private List<Product> products;
    private Activity activity;
    private FragmentTransaction ft;

    public PromotedProductPagerAdapter(Activity activity, @NonNull List<Product> products, FragmentTransaction ft) {
        this.products=products;
        this.activity=activity;
        this.ft = ft;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.promoted_product_detailed, null, false);
        }
        else{
            view=convertView;
        }

        ImageView productImage = (ImageView) view.findViewById(R.id.promoted_product_image);
        TextView productName = (TextView) view.findViewById(R.id.promoted_product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.promoted_product_price);

        productName.setText(products.get(position).getName());
        productPrice.setText(String.valueOf(products.get(position).getPrice()));
        Glide.with(activity)
                .load(products.get(position).getImage())
                .placeholder(R.drawable.store_placeholder)
                .into(productImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = ProductDetailedFragment.newInstance();

                Product product = getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);

                fragment.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;

    }
}
