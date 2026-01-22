package ma.tp.gestionlivres.dao;

import ma.tp.gestionlivres.model.Auteur;
import ma.tp.gestionlivres.model.Livre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    public List<Livre> findAll() {

        List<Livre> livres = new ArrayList<>();

        String sql = """
            SELECT l.isbn, l.titre, l.description, l.date_edition, l.editeur,
                   a.matricule, a.nom, a.prenom, a.genre
            FROM livre l
            JOIN auteur a ON l.matricule = a.matricule
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Auteur auteur = new Auteur();
                auteur.setMatricule(rs.getInt("matricule"));
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                auteur.setGenre(rs.getString("genre"));

                Livre livre = new Livre();
                livre.setIsbn(rs.getInt("isbn"));
                livre.setTitre(rs.getString("titre"));
                livre.setDescription(rs.getString("description"));
                livre.setDateEdition(rs.getDate("date_edition"));
                livre.setEditeur(rs.getString("editeur"));
                livre.setAuteur(auteur);

                livres.add(livre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livres;
    }
    public void ajouterLivre(Livre livre) {

        String sql = """
        INSERT INTO livre
        (isbn, titre, description, date_edition, editeur, matricule)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, livre.getIsbn());
            ps.setString(2, livre.getTitre());
            ps.setString(3, livre.getDescription());
            ps.setDate(4, livre.getDateEdition());
            ps.setString(5, livre.getEditeur());
            ps.setInt(6, livre.getAuteur().getMatricule());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void supprimerLivre(int isbn) {

        String sql = "DELETE FROM livre WHERE isbn = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, isbn);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Livre findByIsbn(int isbn) {

        String sql = """
        SELECT l.*, a.nom, a.prenom, a.genre
        FROM livre l
        JOIN auteur a ON l.matricule = a.matricule
        WHERE l.isbn = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, isbn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Auteur auteur = new Auteur();
                auteur.setMatricule(rs.getInt("matricule"));
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                auteur.setGenre(rs.getString("genre"));

                Livre livre = new Livre();
                livre.setIsbn(rs.getInt("isbn"));
                livre.setTitre(rs.getString("titre"));
                livre.setDescription(rs.getString("description"));
                livre.setDateEdition(rs.getDate("date_edition"));
                livre.setEditeur(rs.getString("editeur"));
                livre.setAuteur(auteur);

                return livre;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void modifierLivre(Livre livre) {

        String sql = """
    UPDATE livre
    SET titre = ?, description = ?, date_edition = ?, editeur = ?, matricule = ?
    WHERE isbn = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getDescription());
            ps.setDate(3, livre.getDateEdition());
            ps.setString(4, livre.getEditeur());
            ps.setInt(5, livre.getAuteur().getMatricule()); // ← update author
            ps.setInt(6, livre.getIsbn());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Auteur> findAllAuteurs() {
        List<Auteur> auteurs = new ArrayList<>();
        String sql = "SELECT matricule, nom, prenom FROM auteur";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Auteur auteur = new Auteur();
                auteur.setMatricule(rs.getInt("matricule"));
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                auteurs.add(auteur);
                System.out.println("DEBUG: Added auteur: " + auteur.getMatricule() + " - " + auteur.getNom() + " " + auteur.getPrenom());
            }
            System.out.println("DEBUG: findAllAuteurs() returned " + auteurs.size() + " auteurs");

        } catch (Exception e) {
            System.err.println("ERROR in findAllAuteurs(): " + e.getMessage());
            e.printStackTrace();
        }

        return auteurs;
    }

    public List<Livre> searchByAuteur(int matricule) {
        List<Livre> livres = new ArrayList<>();
        
        String sql = """
            SELECT l.isbn, l.titre, l.description, l.date_edition, l.editeur,
                   a.matricule, a.nom, a.prenom, a.genre
            FROM livre l
            JOIN auteur a ON l.matricule = a.matricule
            WHERE a.matricule = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, matricule);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Auteur auteur = new Auteur();
                auteur.setMatricule(rs.getInt("matricule"));
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                auteur.setGenre(rs.getString("genre"));

                Livre livre = new Livre();
                livre.setIsbn(rs.getInt("isbn"));
                livre.setTitre(rs.getString("titre"));
                livre.setDescription(rs.getString("description"));
                livre.setDateEdition(rs.getDate("date_edition"));
                livre.setEditeur(rs.getString("editeur"));
                livre.setAuteur(auteur);

                livres.add(livre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livres;
    }

    public List<Livre> searchByTitre(String titre) {
        List<Livre> livres = new ArrayList<>();
        
        String sql = """
            SELECT l.isbn, l.titre, l.description, l.date_edition, l.editeur,
                   a.matricule, a.nom, a.prenom, a.genre
            FROM livre l
            JOIN auteur a ON l.matricule = a.matricule
            WHERE l.titre LIKE ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + titre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Auteur auteur = new Auteur();
                auteur.setMatricule(rs.getInt("matricule"));
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                auteur.setGenre(rs.getString("genre"));

                Livre livre = new Livre();
                livre.setIsbn(rs.getInt("isbn"));
                livre.setTitre(rs.getString("titre"));
                livre.setDescription(rs.getString("description"));
                livre.setDateEdition(rs.getDate("date_edition"));
                livre.setEditeur(rs.getString("editeur"));
                livre.setAuteur(auteur);

                livres.add(livre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livres;
    }

    public List<Livre> searchByDateEdition(String dateDebut, String dateFin) {
        List<Livre> livres = new ArrayList<>();
        
        String sql = """
            SELECT l.isbn, l.titre, l.description, l.date_edition, l.editeur,
                   a.matricule, a.nom, a.prenom, a.genre
            FROM livre l
            JOIN auteur a ON l.matricule = a.matricule
            WHERE l.date_edition BETWEEN ? AND ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, dateDebut);
            ps.setString(2, dateFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Auteur auteur = new Auteur();
                auteur.setMatricule(rs.getInt("matricule"));
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                auteur.setGenre(rs.getString("genre"));

                Livre livre = new Livre();
                livre.setIsbn(rs.getInt("isbn"));
                livre.setTitre(rs.getString("titre"));
                livre.setDescription(rs.getString("description"));
                livre.setDateEdition(rs.getDate("date_edition"));
                livre.setEditeur(rs.getString("editeur"));
                livre.setAuteur(auteur);

                livres.add(livre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livres;
    }

}
