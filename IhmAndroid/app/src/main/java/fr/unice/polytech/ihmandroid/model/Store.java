package fr.unice.polytech.ihmandroid.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;

public class Store implements Serializable {


    private String id;
    private String name;
    private String city;
    private String adress;
    private String cityNumber;
    private String imageURL;
    private List<Product> inventory;

    /**
     * Constructor for a Store
     * @param id the id
     * @param name the name
     * @param city the city where it is located
     * @param adress the adress
     * @param cityNumber the city number
     * @param imageURL the url of the image
     */
    public Store(String id, String name, String city, String adress, String cityNumber, String imageURL) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.adress = adress;
        this.cityNumber = cityNumber;
        if (imageURL==null || imageURL.equals("")){
            this.imageURL = null;
        }
        else{
            this.imageURL = imageURL;
        }

        inventory = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getImageURL() {
        return imageURL;
    }


    public void addProduct(Product product){
        inventory.add(product);
    }

    public void addAllProduct(List<Product> products){
        inventory.addAll(products);
    }

}
