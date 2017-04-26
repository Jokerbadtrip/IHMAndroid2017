package fr.unice.polytech.ihmandroid.model;



public class Store {


    private String id;
    private String name;
    private String city;
    private String adress;
    private String cityNumber;
    private String imageURL;


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
        this.imageURL = imageURL;
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
}
