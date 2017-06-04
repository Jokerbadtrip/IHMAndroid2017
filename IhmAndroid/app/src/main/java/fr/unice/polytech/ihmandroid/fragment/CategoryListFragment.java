package fr.unice.polytech.ihmandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.adapter.CategoryListAdapter;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 26/04/2017.
 */

public class CategoryListFragment extends Fragment {



    private ListView listView;

    public CategoryListFragment() {
    }


    public static Fragment newInstance(){
        Fragment fragment = new CategoryListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_layout_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_categories);
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

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), categories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = (String) parent.getItemAtPosition(position);
                Fragment fragment = ProductListFragment.newInstance();
                Bundle bundle = new Bundle();

                ArrayList<Product> products = new ArrayList<Product>();

                try {
                    products = buildProducts(category);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                bundle.putParcelableArrayList("products", products);
                fragment.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });



    }

    private List<String> buildCategories() throws IOException, SQLException {

        DatabaseHelper db = new DatabaseHelper(getContext());

        db.createDataBase();
        db.openDataBase();


        List<Product> products = db.buildProducts();
        List<String> categories = new ArrayList<>();

        for (Product product : products){
            if (!isIn(categories, product.getCategory())){
                categories.add(product.getCategory());
            }
        }

        db.close();
        return categories;
    }

    private boolean isIn(List<String> categories, String category){

        for (String string : categories)
            if (category.equals(string))
                return true;

        return false;
    }

    private ArrayList<Product> buildProducts(String category) throws IOException, SQLException {
        ArrayList<Product> products = new ArrayList<>();

        DatabaseHelper db  = new DatabaseHelper(getContext());

        db.createDataBase();
        db.openDataBase();


        for (Product product : db.buildProducts()){
            if (product.getCategory().equals(category)){
                products.add(product);
            }
        }

        db.close();

        return products;
    }

}
