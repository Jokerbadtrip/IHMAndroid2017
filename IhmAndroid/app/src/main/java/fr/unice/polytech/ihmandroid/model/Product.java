package fr.unice.polytech.ihmandroid.model;

/**
 * Created by MSI on 26/04/2017.
 */

public class Product {

    String id;
    String name;
    String category;
    double price;
    String description;
    boolean promoted = false;
    boolean reduction = false;


    public Product(String id, String name, String category, double price, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    public void setPromoted(){
        promoted  = true;
    }

    public void setReduction(double percentage){
        reduction = true;
        price = price - price*percentage;
    }
}
