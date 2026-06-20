package model;

import java.time.LocalDate;

public class Abonnement {

    private int       id;
    private int       membreId;
    private String    typeAbonnement;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String    statut;
    private double    prix;

    public Abonnement() {}

    public Abonnement(int id, int membreId, String typeAbonnement,
                      LocalDate dateDebut, LocalDate dateFin, String statut, double prix) {
        this.id             = id;
        this.membreId       = membreId;
        this.typeAbonnement = typeAbonnement;
        this.dateDebut      = dateDebut;
        this.dateFin        = dateFin;
        this.statut         = statut;
        this.prix           = prix;
    }

    public int       getId()             { return id; }
    public int       getMembreId()       { return membreId; }
    public String    getTypeAbonnement() { return typeAbonnement; }
    public LocalDate getDateDebut()      { return dateDebut; }
    public LocalDate getDateFin()        { return dateFin; }
    public String    getStatut()         { return statut; }
    public double    getPrix()           { return prix; }

    public void setId(int id)                          { this.id = id; }
    public void setMembreId(int membreId)              { this.membreId = membreId; }
    public void setTypeAbonnement(String t)            { this.typeAbonnement = t; }
    public void setDateDebut(LocalDate dateDebut)      { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin)          { this.dateFin = dateFin; }
    public void setStatut(String statut)               { this.statut = statut; }
    public void setPrix(double prix)                   { this.prix = prix; }
}