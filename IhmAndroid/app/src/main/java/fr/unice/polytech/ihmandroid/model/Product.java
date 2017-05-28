package fr.unice.polytech.ihmandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by MSI on 26/04/2017.
 */

public class Product implements Parcelable{

    private int id;
    private String name;
    private String category;
    private String image;
    private double price;
    private String description;
    private boolean promoted = false;
    private boolean reduction = false;




    public Product(int id, String name, String category, String image, double price, String description) {
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

    public static final Parcelable.Creator<Product> CREATOR =
            new Parcelable.Creator<Product>(){

                @Override
                public Product createFromParcel(Parcel source) {
                    return new Product(source);
                }

                @Override
                public Product[] newArray(int size) {
                    return new Product[0];
                }
            };

    private Product(Parcel source){
        id = source.readInt();
        name = source.readString();
        image = source.readString();
        price = source.readDouble();
        description = source.readString();

        int promoted;
        int reduction;

        promoted = source.readInt();
        if (promoted==1){
            this.promoted = true;
        }
        else this.promoted =false;

        reduction = source.readInt();
        if (reduction==1){
            this.reduction = true;
        }
        else this.reduction=false;
    }




    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeDouble(price);
        dest.writeString(description);

        if (promoted){
            dest.writeInt(1);
        }
        else dest.writeInt(0);

        if (reduction){
            dest.writeInt(1);
        }
        else dest.writeInt(0);

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
        price = price - price*(percentage/100);
    }

    public String getImage() {
        return image;
    }

    public boolean isPromoted(){
        return promoted;
    }

    public int getId() {
        return id;
    }


}
