package controller;
import dao.MembreDAO;
import model.Membre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MembreController implements Initializable {

    @FXML private TextField  txtNom;
    @FXML private TextField  txtPrenom;
    @FXML private TextField  txtTelephone;
    @FXML private TextField  txtEmail;
    @FXML private DatePicker dpDateInscription;
    @FXML private CheckBox   chkActif;
    @FXML private Slider     sliderIntensite;
    @FXML private Label      lblIntensite;
    @FXML private Label      lblStatus;

    @FXML private TableView<Membre>          tableMembres;
    @FXML private TableColumn<Membre, Integer>   colId;
    @FXML private TableColumn<Membre, String>    colNom;
    @FXML private TableColumn<Membre, String>    colPrenom;
    @FXML private TableColumn<Membre, String>    colTelephone;
    @FXML private TableColumn<Membre, String>    colEmail;
    @FXML private TableColumn<Membre, LocalDate> colDate;
    @FXML private TableColumn<Membre, String>    colActif;      // ✅ ajouté
    // colIntensite supprimée : le slider est un outil de saisie, pas une colonne BDD

    private final MembreDAO              membreDAO = new MembreDAO();
    private final ObservableList<Membre> list      = FXCollections.observableArrayList();
    private Membre selected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblIntensite.textProperty().bind(
                sliderIntensite.valueProperty().asString("%.0f")
        );
        configurerColonnes();
        configurerSelectionTable();
        charger();
    }

    private void configurerColonnes() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        colActif.setCellValueFactory(new PropertyValueFactory<>("etat")); // ✅ ajouté
    }

    private void configurerSelectionTable() {
        tableMembres.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        selected = newSel;
                        remplirFormulaire(newSel);
                    }
                }
        );
    }

    private void charger() {
        try {
            list.setAll(membreDAO.afficherTous());
            tableMembres.setItems(list);
            setStatus("Membres chargés : " + list.size());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible de charger les membres : " + e.getMessage());
        }
    }

    @FXML
    private void handleAjouter() {
        if (!validerFormulaire()) return;

        String etat = chkActif.isSelected() ? "Actif" : "Inactif"; // ✅ lu depuis checkbox

        Membre m = new Membre(
                txtNom.getText().trim(),
                txtPrenom.getText().trim(),
                txtTelephone.getText().trim(),
                txtEmail.getText().trim(),
                dpDateInscription.getValue(),
                etat
        );

        try {
            membreDAO.ajouter(m);
            charger();
            reinitialiserFormulaire();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre ajouté avec succès.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible d'ajouter : " + e.getMessage());
        }
    }

    @FXML
    private void handleModifier() {
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner un membre à modifier.");
            return;
        }
        if (!validerFormulaire()) return;

        selected.setNom(txtNom.getText().trim());
        selected.setPrenom(txtPrenom.getText().trim());
        selected.setTelephone(txtTelephone.getText().trim());
        selected.setEmail(txtEmail.getText().trim());
        selected.setDateInscription(dpDateInscription.getValue());
        selected.setEtat(chkActif.isSelected() ? "Actif" : "Inactif"); // ✅ ajouté

        try {
            membreDAO.modifier(selected);
            charger();
            reinitialiserFormulaire();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Membre modifié avec succès.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible de modifier : " + e.getMessage());
        }
    }

    @FXML
    private void handleSupprimer() {
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner un membre à supprimer.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le membre ?");
        confirm.setContentText("Cette action est irréversible.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    membreDAO.supprimer(selected.getId());
                    charger();
                    reinitialiserFormulaire();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Impossible de supprimer : " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleReset() {
        reinitialiserFormulaire();
        setStatus("Formulaire réinitialisé.");
    }

    private void remplirFormulaire(Membre m) {
        txtNom.setText(m.getNom());
        txtPrenom.setText(m.getPrenom());
        txtTelephone.setText(m.getTelephone());
        txtEmail.setText(m.getEmail());
        dpDateInscription.setValue(m.getDateInscription());
        chkActif.setSelected("Actif".equals(m.getEtat())); // ✅ ajouté
    }

    private void reinitialiserFormulaire() {
        txtNom.clear();
        txtPrenom.clear();
        txtTelephone.clear();
        txtEmail.clear();
        dpDateInscription.setValue(null);
        chkActif.setSelected(true); // ✅ défaut = Actif
        selected = null;
        tableMembres.getSelectionModel().clearSelection();
    }

    private boolean validerFormulaire() {
        StringBuilder erreurs = new StringBuilder();

        if (txtNom.getText() == null || txtNom.getText().trim().isEmpty())
            erreurs.append("• Le nom est obligatoire.\n");
        if (txtPrenom.getText() == null || txtPrenom.getText().trim().isEmpty())
            erreurs.append("• Le prénom est obligatoire.\n");
        if (txtTelephone.getText() == null || !txtTelephone.getText().trim().matches("\\d{10}"))
            erreurs.append("• Le téléphone doit contenir 10 chiffres.\n");
        if (txtEmail.getText() == null || !txtEmail.getText().trim().contains("@"))
            erreurs.append("• L'email est invalide.\n");
        if (dpDateInscription.getValue() == null)
            erreurs.append("• La date d'inscription est obligatoire.\n");

        if (!erreurs.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs invalides", erreurs.toString());
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setStatus(String message) {
        if (lblStatus != null) lblStatus.setText(message);
    }
}