package fr.unice.polytech.ihmandroid.sorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 08/06/2017.
 */

public class SortStoreByCity {


    public SortStoreByCity() {
    }

    public static List<Store> sort(List<Store> stores){

        Collections.sort(stores, new Comparator<Store>() {
            @Override
            public int compare(Store o1, Store o2) {
                return o1.getCity().compareTo(o2.getCity());
            }
        });

        return stores;

    }

}
