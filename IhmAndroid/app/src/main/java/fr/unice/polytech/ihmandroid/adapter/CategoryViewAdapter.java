package fr.unice.polytech.ihmandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Category;



/**
 * Created by MSI on 26/04/2017.
 */

public class CategoryViewAdapter extends ArrayAdapter {

    List<Category> categories = new ArrayList<>();

    public CategoryViewAdapter(@NonNull Context context) {
        super(context, 0);

        for (Category category:Category.values()) {
            categories.add(category);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.category_view_tile, null);
        }
        else{
            view =(View) convertView;
        }

        ImageView image = (ImageView) view.findViewById(R.id.placeholder_image);
        TextView name = (TextView) view.findViewById(R.id.category_name);

        name.setText(getItem(position).getName());


        return view;
    }

    @Override
    public Category getItem(int position){
        return categories.get(position);
    }

}
