package fr.unice.polytech.ihmandroid.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.sorting.SortByName;
import fr.unice.polytech.ihmandroid.sorting.SortByPrice;

/**
 * Created by MSI on 08/05/2017.
 */

public class ProductListAdapter extends ArrayAdapter {

    private List<Product> products;
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;

    private final String TAG = "ProductListAdapter";


    public ProductListAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        this.products = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView==null){
            view = inflater.inflate(R.layout.product_list_tile, null);
        }

        else{
            view = (View) convertView;
        }

        findViewById(view);
        productName.setText(((Product) super.getItem(position)).getName());
        productPrice.setText(String.valueOf(((Product)super.getItem(position)).getPrice())+"€");
        Glide.with(this.getContext()).load(((Product)super.getItem(position)).getImage()).placeholder(R.drawable.store_placeholder).into(productImage);

        return view;

    }

    private void findViewById(View view){
        productImage = (ImageView) view.findViewById(R.id.product_image);
        productName = (TextView) view.findViewById(R.id.product_name);
        productPrice=(TextView) view.findViewById(R.id.product_price);

    }



    @Override
    public int getCount(){
        return super.getCount();
    }


    public void refreshList(List<Product> products){

        Log.d(TAG, "refreshing product list");

        this.products = products;
        notifyDataSetChanged();
    }


}
