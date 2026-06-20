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

    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtEmail;
    @FXML private DatePicker dpDateInscription;
    @FXML private CheckBox chkActif;
    @FXML private Slider sliderIntensite;
    @FXML private Label lblIntensite;

    @FXML private TableView<Membre> tableMembres;
    @FXML private TableColumn<Membre, Integer> colId;
    @FXML private TableColumn<Membre, String> colNom;
    @FXML private TableColumn<Membre, String> colPrenom;
    @FXML private TableColumn<Membre, String> colTelephone;
    @FXML private TableColumn<Membre, String> colEmail;
    @FXML private TableColumn<Membre, LocalDate> colDate;
    @FXML private TableColumn<Membre, Double> colIntensite;
    @FXML private TableColumn<Membre, String> colActif;

    private final MembreDAO membreDAO = new MembreDAO();
    private final ObservableList<Membre> list = FXCollections.observableArrayList();

    private Membre selected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurerColonnes();
        configurerSlider();
        charger();
    }

    private void configurerColonnes() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        colIntensite.setCellValueFactory(new PropertyValueFactory<>("niveauIntensite"));
        colActif.setCellValueFactory(new PropertyValueFactory<>("etat"));
    }

    private void configurerSlider() {
        sliderIntensite.valueProperty().addListener((o, oldV, newV) ->
                lblIntensite.setText(String.valueOf(newV.intValue()))
        );
    }

    private void charger() {
        list.setAll(membreDAO.afficherTous());
        tableMembres.setItems(list);
    }

    @FXML
    private void handleAjouter() {
        Membre m = new Membre(
                txtNom.getText(),
                txtPrenom.getText(),
                txtTelephone.getText(),
                txtEmail.getText(),
                dpDateInscription.getValue(),
                chkActif.isSelected() ? "Actif" : "Inactif",
                sliderIntensite.getValue()
        );

        membreDAO.ajouter(m);
        charger();
    }

    @FXML
    private void handleModifier() {
        selected = tableMembres.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selected.setNom(txtNom.getText());
        selected.setPrenom(txtPrenom.getText());
        selected.setTelephone(txtTelephone.getText());
        selected.setEmail(txtEmail.getText());
        selected.setDateInscription(dpDateInscription.getValue());
        selected.setEtat(chkActif.isSelected() ? "Actif" : "Inactif");
        selected.setNiveauIntensite(sliderIntensite.getValue());

        membreDAO.modifier(selected);
        charger();
    }

    @FXML
    private void handleSupprimer() {
        selected = tableMembres.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        membreDAO.supprimer(selected.getId());
        charger();
    }

    @FXML
    private void handleReset() {
        txtNom.clear();
        txtPrenom.clear();
        txtTelephone.clear();
        txtEmail.clear();
        dpDateInscription.setValue(null);
        sliderIntensite.setValue(5);
        chkActif.setSelected(false);
    }
}