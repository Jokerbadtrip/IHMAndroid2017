package fr.unice.polytech.ihmandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MSI on 03/06/2017.
 */

public class Event implements Parcelable{


    private String name;
    private String description;
    private float lat;
    private float lng;

    public Event(String name, String description, float lat, float lng) {
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }


    protected Event(Parcel in) {
        name = in.readString();
        description = in.readString();
        lat = in.readFloat();
        lng = in.readFloat();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeFloat(lat);
        dest.writeFloat(lng);
    }

    public String getName() {
        return name;
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



}
