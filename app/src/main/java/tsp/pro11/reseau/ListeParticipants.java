package tsp.pro11.reseau;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ListeParticipants implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * liste des utilisateurs auquels qui ont participé à un événement
     */
    private ArrayList<String> listeParticipants;

    public ArrayList<String> getlisteParticipants() {
        return listeParticipants;
    }

    ListeParticipants(ArrayList<String> listeParticipants) {
        this.listeParticipants = listeParticipants;
    }

    public void addUtilisateur(String utilisateur) {
        this.listeParticipants.add(utilisateur);
    }

    public String getUtilisateur(int indice) {
        return this.listeParticipants.get(indice);
    }

    public String toString() {
        String resultat = "Liste des participants : ";
        for (int i=0;i<this.listeParticipants.size();i++) {
            resultat = resultat + "\n" + listeParticipants.get(i).toString();
        }
        resultat = resultat + "\n";
        return resultat;
    }

    public int size() {
        return  listeParticipants.size();
    }

}
