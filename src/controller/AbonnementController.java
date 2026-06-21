package controller;

import dao.AbonnementDAO;
import dao.MembreDAO;
import model.Abonnement;
import model.Membre;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class AbonnementController {

    @FXML private ComboBox<String>      cmbMembre;
    @FXML private ComboBox<String>      cmbType;
    @FXML private ComboBox<String>      cmbStatut;
    @FXML private DatePicker            dateDebut;
    @FXML private DatePicker            dateFin;
    @FXML private TableView<Abonnement> tableAbonnement;

    @FXML private TableColumn<Abonnement, Integer>   colId;
    @FXML private TableColumn<Abonnement, String>    colMembre;   // ✅ String via lambda
    @FXML private TableColumn<Abonnement, String>    colType;
    @FXML private TableColumn<Abonnement, LocalDate> colDebut;
    @FXML private TableColumn<Abonnement, LocalDate> colFin;
    @FXML private TableColumn<Abonnement, String>    colStatut;
    @FXML private TableColumn<Abonnement, Double>    colPrix;

    private final AbonnementDAO abonnementDAO = new AbonnementDAO();
    private final MembreDAO     membreDAO     = new MembreDAO();
    private final ObservableList<Abonnement> abonnementList = FXCollections.observableArrayList();
    private List<Membre> membres;
    private Abonnement abonnementSelectionne = null;

    @FXML
    public void initialize() {
        cmbType.getItems().addAll("Mensuel", "Trimestriel", "Annuel");
        cmbStatut.getItems().addAll("Actif", "Expiré", "Suspendu");
        configurerColonnes();
        configurerSelectionTable();
        chargerMembres();
        chargerAbonnements();
    }

    private void configurerColonnes() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // ✅ membreId est int → on le convertit en String via lambda
        colMembre.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getMembreId()))
        );

        colType.setCellValueFactory(new PropertyValueFactory<>("typeAbonnement"));
        colDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    }

    private void configurerSelectionTable() {
        tableAbonnement.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        abonnementSelectionne = newSel;
                        remplirFormulaire(newSel);
                    }
                }
        );
    }

    private void chargerMembres() {
        try {
            membres = membreDAO.afficherTous();
            cmbMembre.getItems().clear();
            for (Membre m : membres) {
                cmbMembre.getItems().add(
                        m.getId() + " - " + m.getNom() + " " + m.getPrenom()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerAbonnements() {
        try {
            abonnementList.setAll(abonnementDAO.afficherTous());
            tableAbonnement.setItems(abonnementList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible de charger les abonnements.");
        }
    }

    @FXML
    public void ajouterAbonnement() {
        if (!validerFormulaire()) return;
        Abonnement a = new Abonnement(
                0,
                extraireMembreId(),
                cmbType.getValue(),
                dateDebut.getValue(),
                dateFin.getValue(),
                cmbStatut.getValue(),
                calculerPrix(cmbType.getValue())
        );
        try {
            abonnementDAO.ajouter(a);
            chargerAbonnements();
            reinitialiserFormulaire();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement ajouté.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible d'ajouter : " + e.getMessage());
        }
    }

    @FXML
    public void modifierAbonnement() {
        if (abonnementSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Sélectionnez un abonnement.");
            return;
        }
        if (!validerFormulaire()) return;

        abonnementSelectionne.setMembreId(extraireMembreId());
        abonnementSelectionne.setTypeAbonnement(cmbType.getValue());
        abonnementSelectionne.setDateDebut(dateDebut.getValue());
        abonnementSelectionne.setDateFin(dateFin.getValue());
        abonnementSelectionne.setStatut(cmbStatut.getValue());
        abonnementSelectionne.setPrix(calculerPrix(cmbType.getValue()));

        try {
            abonnementDAO.modifier(abonnementSelectionne);
            chargerAbonnements();
            reinitialiserFormulaire();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement modifié.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible de modifier : " + e.getMessage());
        }
    }

    @FXML
    public void supprimerAbonnement() {
        if (abonnementSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Sélectionnez un abonnement.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer cet abonnement ?");
        confirm.setContentText("Cette action est irréversible.");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                try {
                    abonnementDAO.supprimer(abonnementSelectionne.getId());
                    chargerAbonnements();
                    reinitialiserFormulaire();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Impossible de supprimer : " + e.getMessage());
                }
            }
        });
    }

    @FXML
    public void exporterCSV() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Enregistrer le fichier CSV");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        fc.setInitialFileName("abonnements.csv");
        java.io.File fichier = fc.showSaveDialog(
                tableAbonnement.getScene().getWindow()
        );
        if (fichier == null) return;
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            pw.println("ID,Membre ID,Type,Date début,Date fin,Statut,Prix");
            for (Abonnement a : abonnementList) {
                pw.printf("%d,%d,%s,%s,%s,%s,%.2f%n",
                        a.getId(), a.getMembreId(), a.getTypeAbonnement(),
                        a.getDateDebut(), a.getDateFin(),
                        a.getStatut(), a.getPrix());
            }
            showAlert(Alert.AlertType.INFORMATION, "Export réussi",
                    "Fichier CSV enregistré.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Export échoué : " + e.getMessage());
        }
    }

    private void remplirFormulaire(Abonnement a) {
        // ✅ String.valueOf() pour comparer int avec String
        for (String item : cmbMembre.getItems()) {
            if (item.startsWith(String.valueOf(a.getMembreId()) + " -")) {
                cmbMembre.setValue(item);
                break;
            }
        }
        cmbType.setValue(a.getTypeAbonnement());
        dateDebut.setValue(a.getDateDebut());
        dateFin.setValue(a.getDateFin());
        cmbStatut.setValue(a.getStatut());
    }

    private void reinitialiserFormulaire() {
        cmbMembre.setValue(null);
        cmbType.setValue(null);
        dateDebut.setValue(null);
        dateFin.setValue(null);
        cmbStatut.setValue(null);
        abonnementSelectionne = null;
        tableAbonnement.getSelectionModel().clearSelection();
    }

    private boolean validerFormulaire() {
        StringBuilder erreurs = new StringBuilder();
        if (cmbMembre.getValue() == null)
            erreurs.append("• Sélectionnez un membre.\n");
        if (cmbType.getValue() == null)
            erreurs.append("• Sélectionnez un type.\n");
        if (dateDebut.getValue() == null)
            erreurs.append("• La date de début est obligatoire.\n");
        if (dateFin.getValue() == null)
            erreurs.append("• La date de fin est obligatoire.\n");
        if (dateDebut.getValue() != null && dateFin.getValue() != null
                && !dateFin.getValue().isAfter(dateDebut.getValue()))
            erreurs.append("• La date de fin doit être après la date de début.\n");
        if (cmbStatut.getValue() == null)
            erreurs.append("• Sélectionnez un statut.\n");
        if (!erreurs.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs invalides", erreurs.toString());
            return false;
        }
        return true;
    }

    private int extraireMembreId() {
        String val = cmbMembre.getValue();
        return Integer.parseInt(val.split(" - ")[0].trim());
    }

    private double calculerPrix(String type) {
        return switch (type) {
            case "Mensuel"     -> 150.0;
            case "Trimestriel" -> 400.0;
            case "Annuel"      -> 1200.0;
            default            -> 0.0;
        };
    }

    private void showAlert(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}