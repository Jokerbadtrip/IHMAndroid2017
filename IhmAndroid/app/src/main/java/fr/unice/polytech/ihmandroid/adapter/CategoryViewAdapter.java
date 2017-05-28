package fr.unice.polytech.ihmandroid.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;


/**
 * Created by MSI on 26/04/2017.
 */

public class CategoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> categories = new ArrayList<>();
    private Context context;
    private TextView textView;

    public class CategoryViewHolder extends RecyclerView.ViewHolder{


        public CategoryViewHolder(View itemView) {
            super(itemView);

        }


    }

    public CategoryViewAdapter(List<String> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view_tile, null);
        textView = (TextView) parent.findViewById(R.id.category_name);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String category = categories.get(position);
        textView.setText(category);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
