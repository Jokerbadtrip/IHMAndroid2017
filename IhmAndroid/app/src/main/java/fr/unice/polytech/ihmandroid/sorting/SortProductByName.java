package fr.unice.polytech.ihmandroid.sorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 02/05/2017.
 */

public class SortProductByName {


    public SortProductByName() {
    }

    public static List<Product> sort(List<Product> products){
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return products;
    }
}
