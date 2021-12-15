package tsp.pro11.reseau;

import java.io.Serializable;

public class Event implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * nom de l'événement
     */
    private String nom;

    /**
     * nom de l'organisateur
     */
    private String organisateur;

    /**
     * date de l'événement
     */
    private String date;

    /**
     * prix d'entrée à l'événement
     */
    private double prix;

    /**
     * photo de l'événement
     */
    private Photo photo;

    public Event(String nom,String organisateur,String date,double prix) {
        this.nom = nom;
        this.organisateur = organisateur;
        this.date = date;
        this.prix = prix;
    }

    public Event(String nom,String organisateur,String date,double prix,Photo photo) {
        this.nom = nom;
        this.organisateur = organisateur;
        this.date = date;
        this.prix = prix;
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public String getDate() {
        return date;
    }

    public double getPrix() {
        return prix;
    }

    public Photo getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        if (this.aUnePhoto()) {
            return "Event [nom=" + nom + ", organisateur=" + organisateur + ", date=" + date + ", prix=" + prix + ", Avec une Photo" + "]";
        }
        else {
            return "Event [nom=" + nom + ", organisateur=" + organisateur + ", date=" + date + ", prix=" + prix + ", Sans Photo" + "]";
        }
    }

    public boolean aUnePhoto() {
        return photo != null;
    }

}
