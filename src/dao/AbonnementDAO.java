package dao;

import model.Abonnement;
import util.Database;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AbonnementDAO {

    // Ajouter
    public void ajouter(Abonnement a) throws SQLException {

        String sql =
                "INSERT INTO abonnement(membre_id,type_abonnement,date_debut,date_fin,statut) VALUES(?,?,?,?,?)";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setInt(1, a.getMembreId());

            ps.setString(2, a.getTypeAbonnement());

            ps.setDate(3, Date.valueOf(a.getDateDebut()));

            ps.setDate(4, Date.valueOf(a.getDateFin()));

            ps.setString(5, a.getStatut());

            ps.executeUpdate();
        }
    }

    // Modifier
    public void modifier(Abonnement a) throws SQLException {

        String sql =
                "UPDATE abonnement SET membre_id=?, type_abonnement=?, date_debut=?, date_fin=?, statut=? WHERE id=?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setInt(1, a.getMembreId());

            ps.setString(2, a.getTypeAbonnement());

            ps.setDate(3, Date.valueOf(a.getDateDebut()));

            ps.setDate(4, Date.valueOf(a.getDateFin()));

            ps.setString(5, a.getStatut());

            ps.setInt(6, a.getId());

            ps.executeUpdate();
        }
    }

    // Supprimer
    public void supprimer(int id) throws SQLException {

        String sql = "DELETE FROM abonnement WHERE id=?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    // Lister tous les abonnements
    public List<Abonnement> listerTous() throws SQLException {

        List<Abonnement> liste = new ArrayList<>();

        String sql = "SELECT * FROM abonnement";

        try (Statement st = Database.getConnection().createStatement();

             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                liste.add(

                        new Abonnement(

                                rs.getInt("id"),

                                rs.getInt("membre_id"),

                                rs.getString("type_abonnement"),

                                rs.getDate("date_debut").toLocalDate(),

                                rs.getDate("date_fin").toLocalDate(),

                                rs.getString("statut")

                        )

                );

            }

        }

        return liste;
    }

    // Chercher par id
    public Abonnement chercherParId(int id)
            throws SQLException {

        String sql =
                "SELECT * FROM abonnement WHERE id=?";

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Abonnement(

                        rs.getInt("id"),

                        rs.getInt("membre_id"),

                        rs.getString("type_abonnement"),

                        rs.getDate("date_debut").toLocalDate(),

                        rs.getDate("date_fin").toLocalDate(),

                        rs.getString("statut")

                );
            }

        }

        return null;
    }

}