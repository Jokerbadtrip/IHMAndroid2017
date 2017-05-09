package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 09/05/2017.
 */

public class PromotedProductDetailed extends Fragment {

    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private Product product;


    public PromotedProductDetailed() {
    }

    public static Fragment newInstance(){
        return new PromotedViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promoted_product_detailed, container, false);
        Bundle bundle = getArguments();
        product = (Product) bundle.getSerializable("product");
        findViewById(rootView);
        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        Glide.with(getContext())
                .load(product.getImage())
                .placeholder(R.drawable.store_placeholder)
                .into(productImage);
        return rootView;
    }

    private void findViewById(View view){
        productImage = (ImageView) view.findViewById(R.id.promoted_product_image);
        productName = (TextView) view.findViewById(R.id.promoted_product_name);
        productPrice = (TextView) view.findViewById(R.id.promoted_product_price);
    }



}
