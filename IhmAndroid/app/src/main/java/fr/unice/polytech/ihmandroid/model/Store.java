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
    private String description;
    private List<Product> inventory;


    public Store(String id, String name, String city, String adress, String cityNumber, String imageURL, String description) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.adress = adress;
        this.cityNumber = cityNumber;
        this.imageURL=imageURL;
        this.description=description;

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

    public String getAdress() {
        return adress;
    }

    public String getCityNumber() {
        return cityNumber;
    }

    public String getDescription() {
        return description;
    }

    public void addProduct(Product product){
        inventory.add(product);
    }

    public void addAllProduct(List<Product> products){
        inventory.addAll(products);
    }

}
