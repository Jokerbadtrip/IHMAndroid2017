package fr.unice.polytech.ihmandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MSI on 27/05/2017.
 */

public class User implements Parcelable {


    private String firstname, name, address;
    private int fidelityPoints;

    public User() {

    }

    public User(String firstname, String name, String address, int fidelityPoints) {
        this.firstname = firstname;
        this.name = name;
        this.address = address;
        this.fidelityPoints = fidelityPoints;
    }

    public static final Parcelable.Creator<User> CREATOR =
            new Parcelable.Creator<User>(){

                @Override
                public User createFromParcel(Parcel source) {
                    return new User(source);
                }

                @Override
                public User[] newArray(int size) {
                    return new User[0];
                }
            };


    private User(Parcel source){
        firstname = source.readString();
        name = source.readString();
        address = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(name);
        dest.writeString(address);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
