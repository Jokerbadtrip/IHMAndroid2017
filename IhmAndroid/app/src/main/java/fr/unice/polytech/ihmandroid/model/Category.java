package fr.unice.polytech.ihmandroid.model;

/**
 * Created by MSI on 26/04/2017.
 */

public enum Category {

    FURNITURE("Meubles"),
    ELECTRONIC("Electronique"),
    GAMES("Jeux vidéo");

    private String name;

    Category(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
