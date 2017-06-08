package fr.unice.polytech.ihmandroid.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.unice.polytech.ihmandroid.model.Product;

/**
 * Created by MSI on 29/05/2017.
 */

public class SortProductByPrice {

    public SortProductByPrice() {
    }

    public static List<Product> sort(List<Product> products){
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Double.valueOf(o1.getPrice()).compareTo(Double.valueOf(o2.getPrice()));
            }
        });

        return products;
    }
}
