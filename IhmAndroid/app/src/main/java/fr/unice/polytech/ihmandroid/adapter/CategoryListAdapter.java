package fr.unice.polytech.ihmandroid.adapter;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;


/**
 * Created by MSI on 26/04/2017.
 */

public class CategoryListAdapter extends ArrayAdapter {

    private List<String> categories = new ArrayList<>();
    private TextView categoryName;

    public CategoryListAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
        categories = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView==null){
            view = inflater.inflate(R.layout.category_view_tile, null);
        }
        else {
            view = convertView;
        }

        categoryName = (TextView) view.findViewById(R.id.category_name);
        String category = getItem(position);
        categoryName.setText(category);


        return view;


    }

    @Override
    public String getItem(int position){
        return categories.get(position);
    }
    @Override
    public int getCount(){
        return super.getCount();
    }





}
