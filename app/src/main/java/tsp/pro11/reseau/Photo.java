package tsp.pro11.reseau;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import tsp.pro11.database.DataBase;

public class Photo implements  Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9028432328352894308L;

    public byte[] photo;

    /**
     * crée une photo à partir du tableau de byte
     */
    public Photo(byte[] photo) {
        this.photo = photo;
    }

    public boolean estVide() {
        return this.photo == null;
    }
    /**
     * crée une photo à partir d'un fichier jpg
     * @param filename
     * 		donne le chemin du fichier jpg à convertir
     */
    public Photo(String filename) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        if (bos != null) {
            this.photo = bos.toByteArray();
        }
    }

    /**
     * Mets à jour la photo pour un événement
     *
     * @param nom de l'événement
     * @param filename chemin de la photo
     */
    public void updatePhoto(String nom) {
        // update sql
        String updateSQL = "UPDATE events "
                + "SET photo = ? "
                + "WHERE nom=?";

        try {
            DataBase sgbd = new DataBase("jdbc:sqlite:Peps.db");
            Connection conn = sgbd.connect();
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);
            pstmt.setBytes(1, this.photo);
            pstmt.setString(2, nom);

            pstmt.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * lit une photo et l'enregistre à l'endroit choisi
     *
     * @param filename
     * 			endroit où l'on veut que soit enregistrer la photo au format jpg
     * @throws IOException
     */
    public void readPhoto(String filename) throws IOException {

        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);

        System.out.println("Writing BLOB to file " + file.getAbsolutePath());
        ByteArrayInputStream input = new ByteArrayInputStream(this.photo);
        byte[] buffer = new byte[1024];
        while (input.read(buffer) > 0) {
            fos.write(buffer);
        }

    }
}
