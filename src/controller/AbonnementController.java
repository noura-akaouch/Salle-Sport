package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import model.Abonnement;

public class AbonnementController {

    @FXML
    private ComboBox<String> cmbMembre;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private ComboBox<String> cmbStatut;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TableView<Abonnement> tableAbonnement;

    @FXML
    public void initialize() {

        cmbType.getItems().addAll(
                "Mensuel",
                "Trimestriel",
                "Annuel"
        );

        cmbStatut.getItems().addAll(
                "Actif",
                "Expiré",
                "Suspendu"
        );
    }

    @FXML
    public void ajouterAbonnement() {

    }

    @FXML
    public void modifierAbonnement() {

    }

    @FXML
    public void supprimerAbonnement() {

    }

    @FXML
    public void exporterCSV() {

    }
}