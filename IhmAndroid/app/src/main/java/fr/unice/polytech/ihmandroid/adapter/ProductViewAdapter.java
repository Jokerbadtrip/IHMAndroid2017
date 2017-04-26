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

import org.w3c.dom.Text;

import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Product;


/**
 * Created by MSI on 26/04/2017.
 */

public class ProductViewAdapter extends ArrayAdapter {

    private List<Product> products;

    public ProductViewAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        products = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.product_view_tile, null);
        }
        else{
            view =(View) convertView;
        }
        Product product = getItem(position);
        ImageView image = (ImageView) view.findViewById(R.id.product_image);
        TextView name = (TextView) view.findViewById(R.id.product_name);
        TextView price = (TextView) view.findViewById(R.id.product_price);
        TextView description = (TextView) view.findViewById(R.id.product_description);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        description.setText(product.getDescription());

        return view;
    }

    @Override
    public Product getItem(int position){
        return products.get(position);
    }

}
