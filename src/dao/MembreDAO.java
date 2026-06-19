package dao;

import model.Membre;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    // Ajouter un membre
    public void ajouter(Membre m) throws SQLException {
        String sql = "INSERT INTO membre (nom, prenom, telephone, email, date_inscription) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getTelephone());
            ps.setString(4, m.getEmail());
            ps.setDate(5, Date.valueOf(m.getDateInscription()));
            ps.executeUpdate();
        }
    }

    // Modifier un membre
    public void modifier(Membre m) throws SQLException {
        String sql = "UPDATE membre SET nom=?, prenom=?, telephone=?, email=?, date_inscription=? WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getTelephone());
            ps.setString(4, m.getEmail());
            ps.setDate(5, Date.valueOf(m.getDateInscription()));
            ps.setInt(6, m.getId());
            ps.executeUpdate();
        }
    }

    // Supprimer un membre
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM membre WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Lister tous les membres
    public List<Membre> listerTous() throws SQLException {
        List<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membre";
        try (Statement st = Database.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_inscription").toLocalDate()
                ));
            }
        }
        return liste;
    }

    // Chercher par id
    public Membre chercherParId(int id) throws SQLException {
        String sql = "SELECT * FROM membre WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_inscription").toLocalDate()
                );
            }
        }
        return null;
    }
}