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
 * Created by MSI on 08/05/2017.
 */

public class ProductDetailedFragment extends Fragment {



    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private TextView productCategory;


    public ProductDetailedFragment() {
    }

    public static Fragment newInstance(){
        return new ProductDetailedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detailed_view, container, false);
        findViewById(rootView);
        return rootView;
    }

    private void findViewById(View view){

        productImage = (ImageView) view.findViewById(R.id.product_image);
        productName = (TextView) view.findViewById(R.id.product_name);
        productPrice = (TextView) view.findViewById(R.id.product_price);
        productDescription = (TextView) view.findViewById(R.id.product_description);
        productCategory = (TextView) view.findViewById(R.id.product_category);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        Product product = (Product) bundle.getSerializable("product");

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productCategory.setText("catégorie : " + product.getCategory());
        productPrice.setText("prix : " + String.valueOf(product.getPrice()));

        Glide.with(this.getContext()).load(product.getImage()).placeholder(R.drawable.store_placeholder).into(productImage);


    }




}


