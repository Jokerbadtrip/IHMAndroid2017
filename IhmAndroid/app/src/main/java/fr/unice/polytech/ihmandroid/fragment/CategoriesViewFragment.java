package fr.unice.polytech.ihmandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.CategoryViewAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 26/04/2017.
 */

public class CategoriesViewFragment extends Fragment {



    private RecyclerView recyclerView;

    public CategoriesViewFragment() {
    }


    public static Fragment newInstance(){
        Fragment fragment = new CategoriesViewFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_layout_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_categories);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        List<String> categories = new ArrayList<>();
        try {
            categories = buildCategories();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CategoryViewAdapter adapter = new CategoryViewAdapter(categories, getContext());
        recyclerView.setAdapter(adapter);



    }

    private List<String> buildCategories() throws IOException, SQLException {

        DatabaseHelper db = new DatabaseHelper(getContext());

        db.createDataBase();
        db.openDataBase();

        db.buildProducts();

        List<Product> products = db.getProducts();
        List<String> categories = new ArrayList<>();

        for (Product product : products){
            if (!isIn(categories, product.getCategory())){
                categories.add(product.getCategory());
            }
        }
        return categories;
    }

    private boolean isIn(List<String> categories, String category){

        for (String string : categories)
            if (category.equals(string))
                return true;

        return false;
    }

}
