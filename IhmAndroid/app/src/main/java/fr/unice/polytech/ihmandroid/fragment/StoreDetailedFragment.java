package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 05/05/2017.
 */

public class StoreDetailedFragment extends Fragment {


    private List<Product> products;

    private ImageView storeImage;
    private TextView storeName, storeDescription, storeAdress, storeCityNumber,storeCity;
    private Button storeToProduct, storeOnMap;

    public StoreDetailedFragment() {

    }

    public static Fragment newInstance(){
        Fragment fragment = new StoreDetailedFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.store_detailed_view, container, false);
        findViewById(rootView);
        return rootView;
    }

    private void findViewById(View view){
        storeImage = (ImageView) view.findViewById(R.id.store_image_detailed);
        storeName = (TextView) view.findViewById(R.id.store_name_detailed);
        storeDescription = (TextView) view.findViewById(R.id.store_description_detailed);
        storeAdress = (TextView) view.findViewById(R.id.store_adress_detailed);
        storeCityNumber = (TextView) view.findViewById(R.id.store_cityNumber_detailed);
        storeCity = (TextView) view.findViewById(R.id.store_city_detailed);
        storeToProduct = (Button) view.findViewById(R.id.store_product_button);
        storeOnMap = (Button) view.findViewById(R.id.view_store_on_map);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Bundle bundle = getArguments();
        final Store store = bundle.getParcelable("store");

        storeName.setText(store.getName());
        storeDescription.setText(store.getDescription());
        storeAdress.setText(store.getAdress());
        storeCityNumber.setText(store.getCityNumber()+", ");
        storeCity.setText(store.getCity());



        Glide.with(this.getContext()).load(store.getImageURL()).placeholder(R.drawable.store_placeholder).into(storeImage);

        storeToProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = ProductListFragment.newInstance();

                Bundle bundle = new Bundle();
                ArrayList<Product> products = (ArrayList<Product>) store.getInventory();

                bundle.putParcelableArrayList("products", products);

                fragment.setArguments(bundle);

                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        storeOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment fragment = StoreMapFragment.newInstance();

                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("store", store);

                fragment.setArguments(bundle1);

                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    }
}
