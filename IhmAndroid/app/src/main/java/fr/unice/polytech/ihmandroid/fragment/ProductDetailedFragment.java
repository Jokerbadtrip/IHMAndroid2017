package fr.unice.polytech.ihmandroid.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.database.DatabaseHelper;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 08/05/2017.
 */

public class ProductDetailedFragment extends Fragment {



    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private TextView productCategory;
    private Button buyOnline, viewStores, shareButton, buyWithApp;



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
        buyOnline = (Button) view.findViewById(R.id.buy_product_online_button);
        viewStores = (Button) view.findViewById(R.id.view_stores_having_product_button);
        shareButton = (Button) view.findViewById(R.id.share_button);
        buyWithApp = (Button) view.findViewById(R.id.buy_product_with_app_button);

    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        final Product product = bundle.getParcelable("product");

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productCategory.setText("catégorie : " + product.getCategory());
        productPrice.setText("prix : " + String.valueOf(product.getPrice())+"€");

        viewStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(getContext());
                ArrayList<Store> stores = new ArrayList<Store>();
                try {
                    db.createDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    db.openDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                stores.addAll(db.buildInventories(db.buildStores(), db.buildProducts()));
                db.close();

                stores = buildStores(product, stores);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores", stores);

                Fragment fragment = StoreListFragment.newInstance();
                fragment.setArguments(bundle);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        buyOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = product.getName().replaceAll(" ","_");
                Uri uri = Uri.parse("http://www.macrogamia.com/products/"+name+"/buyOnline");
                Intent intent  = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = product.getName().replaceAll(" ","_");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, product.getName());
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.macrogamia.com/products/"+ name);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Partager avec : "));
            }
        });




        Glide.with(this.getContext()).load(product.getImage()).placeholder(R.drawable.store_placeholder).into(productImage);


    }

    private ArrayList<Store> buildStores(Product product, List<Store> stores){

        ArrayList<Store> newStores = new ArrayList<>();

        for (Store store : stores){
            for (Product product1 : store.getInventory()){
                if (product.getId()==product1.getId()){
                    newStores.add(store);
                }
            }
        }
        return newStores;



    }


    private void setShareIntent(Intent intent){



    }


}


