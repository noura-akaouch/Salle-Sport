package model;

import java.time.LocalDate;

public class Membre {

    private int       id;
    private String    nom;
    private String    prenom;
    private String    telephone;
    private String    email;
    private LocalDate dateInscription;

    // Constructeur complet (SELECT)
    public Membre(int id, String nom, String prenom, String telephone, String email, LocalDate dateInscription) {
        this.id              = id;
        this.nom             = nom;
        this.prenom          = prenom;
        this.telephone       = telephone;
        this.email           = email;
        this.dateInscription = dateInscription;
    }

    // Constructeur sans id (INSERT)
    public Membre(String nom, String prenom, String telephone, String email, LocalDate dateInscription) {
        this(0, nom, prenom, telephone, email, dateInscription);
    }

    // Getters
    public int       getId()              { return id; }
    public String    getNom()             { return nom; }
    public String    getPrenom()          { return prenom; }
    public String    getTelephone()       { return telephone; }
    public String    getEmail()           { return email; }
    public LocalDate getDateInscription() { return dateInscription; }

    // Setters
    public void setId(int id)                      { this.id = id; }
    public void setNom(String nom)                 { this.nom = nom; }
    public void setPrenom(String prenom)           { this.prenom = prenom; }
    public void setTelephone(String telephone)     { this.telephone = telephone; }
    public void setEmail(String email)             { this.email = email; }
    public void setDateInscription(LocalDate date) { this.dateInscription = date; }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}