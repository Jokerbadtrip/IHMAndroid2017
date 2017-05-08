package fr.unice.polytech.ihmandroid.model;

/**
 * Created by MSI on 26/04/2017.
 */

public class Product {

    private String id;
    private String name;
    private String category;
    private String image;
    private double price;
    private String description;
    private boolean promoted = false;
    private boolean reduction = false;


    public Product(String id, String name, String category, String image, double price, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;

        if (image==null || image.equals("")){
            this.image="@drawable/product_placeholder";
        }
        else{
            this.image=image;
        }

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

    public String getImage() {
        return image;
    }
}
