package model;

import java.time.LocalDate;

public class Membre {

    private int       id;
    private String    nom;
    private String    prenom;
    private String    telephone;
    private String    email;
    private LocalDate dateInscription;
    private String    etat;
    private double    niveauIntensite;

    public Membre(int id, String nom, String prenom, String telephone,
                  String email, LocalDate dateInscription, String etat, double niveauIntensite) {
        this.id              = id;
        this.nom             = nom;
        this.prenom          = prenom;
        this.telephone       = telephone;
        this.email           = email;
        this.dateInscription = dateInscription;
        this.etat            = etat;
        this.niveauIntensite = niveauIntensite;
    }

    // Constructeur sans id (INSERT)
    public Membre(String nom, String prenom, String telephone, String email,
                  LocalDate dateInscription, String etat, double niveauIntensite) {
        this(0, nom, prenom, telephone, email, dateInscription, etat, niveauIntensite);
    }

    public int       getId()              { return id; }
    public String    getNom()             { return nom; }
    public String    getPrenom()          { return prenom; }
    public String    getTelephone()       { return telephone; }
    public String    getEmail()           { return email; }
    public LocalDate getDateInscription() { return dateInscription; }
    public String    getEtat()            { return etat; }
    public double    getNiveauIntensite() { return niveauIntensite; }
    // colonne "Actif" dans le FXML utilise property="actif"
    public boolean   isActif()            { return "Actif".equals(etat); }

    public void setId(int id)                      { this.id = id; }
    public void setNom(String nom)                 { this.nom = nom; }
    public void setPrenom(String prenom)           { this.prenom = prenom; }
    public void setTelephone(String telephone)     { this.telephone = telephone; }
    public void setEmail(String email)             { this.email = email; }
    public void setDateInscription(LocalDate date) { this.dateInscription = date; }
    public void setEtat(String etat)               { this.etat = etat; }
    public void setNiveauIntensite(double n)       { this.niveauIntensite = n; }

    @Override
    public String toString() { return prenom + " " + nom; }
}