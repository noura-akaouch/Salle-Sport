package dao;

import model.Abonnement;
import util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementDAO {

    public void ajouter(Abonnement a) throws SQLException {
        String sql = "INSERT INTO abonnement(membre_id,type_abonnement,date_debut,date_fin,statut,prix) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, a.getMembreId());
            ps.setString(2, a.getTypeAbonnement());
            ps.setDate(3, Date.valueOf(a.getDateDebut()));
            ps.setDate(4, Date.valueOf(a.getDateFin()));
            ps.setString(5, a.getStatut());
            ps.setDouble(6, a.getPrix());
            ps.executeUpdate();
        }
    }

    public void modifier(Abonnement a) throws SQLException {
        String sql = "UPDATE abonnement SET membre_id=?,type_abonnement=?,date_debut=?,date_fin=?,statut=?,prix=? WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, a.getMembreId());
            ps.setString(2, a.getTypeAbonnement());
            ps.setDate(3, Date.valueOf(a.getDateDebut()));
            ps.setDate(4, Date.valueOf(a.getDateFin()));
            ps.setString(5, a.getStatut());
            ps.setDouble(6, a.getPrix());
            ps.setInt(7, a.getId());
            ps.executeUpdate();
        }
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM abonnement WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Abonnement> afficherTous() throws SQLException {
        List<Abonnement> liste = new ArrayList<>();
        String sql = "SELECT * FROM abonnement";
        try (Statement st = Database.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) liste.add(mapRow(rs));
        }
        return liste;
    }

    public Abonnement chercherParId(int id) throws SQLException {
        String sql = "SELECT * FROM abonnement WHERE id=?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {   // ResultSet fermé correctement
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public double calculerRevenuActifs() throws SQLException {
        String sql = "SELECT SUM(prix) FROM abonnement WHERE statut='Actif'";
        try (Statement st = Database.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        }
        return 0.0;
    }

    private Abonnement mapRow(ResultSet rs) throws SQLException {
        return new Abonnement(
                rs.getInt("id"),
                rs.getInt("membre_id"),
                rs.getString("type_abonnement"),
                rs.getDate("date_debut").toLocalDate(),
                rs.getDate("date_fin").toLocalDate(),
                rs.getString("statut"),
                rs.getDouble("prix")
        );
    }
}