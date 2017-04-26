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

import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 23/04/2017.
 */

public class ListViewAdapter extends ArrayAdapter {


    private List<Store> stores;

    public ListViewAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        this.stores = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView==null){
            view = inflater.inflate(R.layout.list_view_tile, null);
        }
        else{
            view = (View) convertView;
        }

        Store store = getItem(position);
        ImageView storeImage = (ImageView) view.findViewById(R.id.store_image);
        TextView storeName = (TextView) view.findViewById(R.id.store_name);
        TextView storeCity = (TextView) view.findViewById(R.id.store_city);
        storeName.setText(store.getName());
        storeCity.setText(store.getCity());

        return view;

    }


    @Override
    public Store getItem(int position){
        return stores.get(position);
    }
    @Override
    public int getCount(){
        return super.getCount();
    }


}
