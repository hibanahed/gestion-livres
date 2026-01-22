package ma.tp.gestionlivres.dao;

import ma.tp.gestionlivres.model.Auteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuteurDAO {

    public List<Auteur> findAll() {
        List<Auteur> auteurs = new ArrayList<>();

        String sql = "SELECT * FROM auteur";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Auteur a = new Auteur();
                a.setMatricule(rs.getInt("matricule"));
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                a.setGenre(rs.getString("genre"));
                auteurs.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return auteurs;
    }

    public void ajouterAuteur(Auteur auteur) {
        String sql = "INSERT INTO auteur (matricule, nom, prenom, genre) VALUES (?, ?, ?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, auteur.getMatricule());
            ps.setString(2, auteur.getNom());
            ps.setString(3, auteur.getPrenom());
            ps.setString(4, auteur.getGenre());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Auteur findByMatricule(int matricule) {
        String sql = "SELECT * FROM auteur WHERE matricule = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, matricule);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Auteur a = new Auteur();
                a.setMatricule(rs.getInt("matricule"));
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                a.setGenre(rs.getString("genre"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modifierAuteur(Auteur auteur) {
        String sql = "UPDATE auteur SET nom=?, prenom=?, genre=? WHERE matricule=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, auteur.getNom());
            ps.setString(2, auteur.getPrenom());
            ps.setString(3, auteur.getGenre());
            ps.setInt(4, auteur.getMatricule());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerAuteur(int matricule) {
        String sql = "DELETE FROM auteur WHERE matricule=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, matricule);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
