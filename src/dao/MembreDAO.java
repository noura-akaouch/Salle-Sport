package dao;

import model.Membre;
import util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    // =========================
    // AJOUTER
    // =========================
    public void ajouter(Membre m) {
        String sql = "INSERT INTO membre (nom, prenom, telephone, email, date_inscription, etat) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getTelephone());
            ps.setString(4, m.getEmail());
            ps.setDate(5, Date.valueOf(m.getDateInscription()));
            ps.setString(6, m.getEtat());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du membre", e);
        }
    }

    // =========================
    // MODIFIER
    // =========================
    public void modifier(Membre m) {
        String sql = "UPDATE membre SET nom=?, prenom=?, telephone=?, email=?, date_inscription=?, etat=? WHERE id=?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getTelephone());
            ps.setString(4, m.getEmail());
            ps.setDate(5, Date.valueOf(m.getDateInscription()));
            ps.setString(6, m.getEtat());
            ps.setInt(7, m.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du membre", e);
        }
    }

    // =========================
    // SUPPRIMER
    // =========================
    public void supprimer(int id) {
        String sql = "DELETE FROM membre WHERE id=?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du membre", e);
        }
    }

    // =========================
    // AFFICHER TOUS
    // =========================
    public List<Membre> afficherTous() {
        List<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membre";

        try (Statement st = Database.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des membres", e);
        }

        return liste;
    }

    // =========================
    // CHERCHER PAR ID
    // =========================
    public Membre chercherParId(int id) {
        String sql = "SELECT * FROM membre WHERE id=?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche membre par ID", e);
        }

        return null;
    }

    // =========================
    // RECHERCHER PAR NOM
    // =========================
    public List<Membre> rechercherParNom(String motCle) {
        List<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membre WHERE nom LIKE ? OR prenom LIKE ?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setString(1, "%" + motCle + "%");
            ps.setString(2, "%" + motCle + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche membres", e);
        }

        return liste;
    }

    // =========================
    // MAPPING RESULTSET -> OBJET
    // =========================
    private Membre mapRow(ResultSet rs) throws SQLException {
        return new Membre(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("telephone"),
                rs.getString("email"),
                rs.getDate("date_inscription").toLocalDate(),
                rs.getString("etat"),
                0.0 // intensité non stockée
        );
    }
}