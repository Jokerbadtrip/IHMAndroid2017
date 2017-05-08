package fr.unice.polytech.ihmandroid.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 08/05/2017.
 */

public class StoreInventoryAdapter extends ArrayAdapter {

    private List<Product> products;
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;


    public StoreInventoryAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        this.products = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView==null){
            view = inflater.inflate(R.layout.store_inventory_tile, null);
        }

        else{
            view = (View) convertView;
        }

        findViewById(view);
        productName.setText(getItem(position).getName());
        productPrice.setText(String.valueOf(getItem(position).getPrice()));

        Glide.with(this.getContext()).load(getItem(position).getImage()).placeholder(R.drawable.store_placeholder).into(productImage);

        return view;

    }

    private void findViewById(View view){
        productImage = (ImageView) view.findViewById(R.id.product_image);
        productName = (TextView) view.findViewById(R.id.product_name);
        productPrice=(TextView) view.findViewById(R.id.product_price);
    }

    @Override
    public Product getItem(int position){
        return products.get(position);
    }

    @Override
    public int getCount(){
        return super.getCount();
    }
}
