package fr.unice.polytech.ihmandroid.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.R;

public class Store implements Parcelable {


    private int id;
    private String name;
    private String city;
    private String adress;
    private String cityNumber;
    private String imageURL;
    private String description;
    private float lat;
    private float lng;
    private List<Product> inventory;


    public static final Parcelable.Creator<Store> CREATOR =
            new Parcelable.Creator<Store>(){

                @Override
                public Store createFromParcel(Parcel source) {
                    return new Store(source);
                }

                @Override
                public Store[] newArray(int size) {
                    return new Store[0];
                }
            };




    private Store(Parcel source){

        id = source.readInt();
        name = source.readString();
        city = source.readString();
        adress = source.readString();
        cityNumber = source.readString();
        imageURL  = source.readString();
        description = source.readString();
        lat = source.readFloat();
        lng = source.readFloat();
        inventory = source.readArrayList(null);

    }


    public Store(int id, String name, String city, String adress, String cityNumber, String imageURL, String description, float lat, float lng) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.adress = adress;
        this.cityNumber = cityNumber;
        this.imageURL=imageURL;
        this.description=description;
        this.lat = lat;
        this.lng = lng;

        inventory = new ArrayList<>();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(adress);
        dest.writeString(cityNumber);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeFloat(lat);
        dest.writeFloat(lng);
        dest.writeList(inventory);
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

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public int getId() {
        return id;
    }

    public void addProduct(Product product){
        inventory.add(product);
    }

    public void addAllProduct(List<Product> products){
        inventory.addAll(products);
    }



}
