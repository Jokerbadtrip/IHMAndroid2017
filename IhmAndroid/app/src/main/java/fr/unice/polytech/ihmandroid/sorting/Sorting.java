package fr.unice.polytech.ihmandroid.sorting;

/**
 * Created by MSI on 02/05/2017.
 */

public enum Sorting {

    BY_CITY("Trier par ville"),
    BY_NAME("Trier par nom");


    private String name;


    Sorting(String name) {
        this.name = name;
    }
}
