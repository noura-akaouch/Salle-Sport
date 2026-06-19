package controller;

import util.Database;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {

    @FXML
    private Label lblTotalMembres;

    @FXML
    private Label lblTotalAbonnements;

    @FXML
    private Label lblActifs;

    @FXML
    private Label lblExpires;

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {

        chargerStatistiques();

    }

    private void chargerStatistiques() {

        try {

            Connection cn = Database.getConnection();

            lblTotalMembres.setText(
                    String.valueOf(compter(
                            cn,
                            "SELECT COUNT(*) FROM membre"
                    ))
            );

            lblTotalAbonnements.setText(
                    String.valueOf(compter(
                            cn,
                            "SELECT COUNT(*) FROM abonnement"
                    ))
            );

            int actifs = compter(
                    cn,
                    "SELECT COUNT(*) FROM abonnement WHERE statut='Actif'"
            );

            int expires = compter(
                    cn,
                    "SELECT COUNT(*) FROM abonnement WHERE statut='Expiré'"
            );

            int suspendus = compter(
                    cn,
                    "SELECT COUNT(*) FROM abonnement WHERE statut='Suspendu'"
            );

            lblActifs.setText(String.valueOf(actifs));

            lblExpires.setText(String.valueOf(expires));

            pieChart.setData(

                    FXCollections.observableArrayList(

                            new PieChart.Data("Actif", actifs),

                            new PieChart.Data("Expiré", expires),

                            new PieChart.Data("Suspendu", suspendus)

                    )

            );

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    private int compter(Connection cn, String sql)
            throws Exception {

        PreparedStatement ps = cn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return rs.getInt(1);

        }

        return 0;

    }

}

