package dao;

import model.Membre;
import util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    public void ajouter(Membre m) throws SQLException {
        String sql = "INSERT INTO membre (nom, prenom, telephone, email, date_inscription, etat) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getTelephone());
            ps.setString(4, m.getEmail());
            ps.setDate(5, Date.valueOf(m.getDateInscription()));
            ps.setString(6, m.getEtat());
            ps.executeUpdate();
        }
    }

    public void modifier(Membre m) throws SQLException {
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
        }
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM membre WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Renommé en afficherTous() pour correspondre à l'appel dans AbonnementController
    public List<Membre> afficherTous() throws SQLException {
        List<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membre";
        try (Statement st = Database.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapRow(rs));
            }
        }
        return liste;
    }

    public Membre chercherParId(int id) throws SQLException {
        String sql = "SELECT * FROM membre WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql);) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {   // ResultSet fermé correctement
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<Membre> rechercherParNom(String motCle) throws SQLException {
        List<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membre WHERE nom LIKE ? OR prenom LIKE ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + motCle + "%");
            ps.setString(2, "%" + motCle + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) liste.add(mapRow(rs));
            }
        }
        return liste;
    }

    private Membre mapRow(ResultSet rs) throws SQLException {
        return new Membre(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("telephone"),
                rs.getString("email"),
                rs.getDate("date_inscription").toLocalDate(),
                rs.getString("etat"),
                0.0   // niveauIntensite non stocké en BDD, valeur par défaut
        );
    }
}