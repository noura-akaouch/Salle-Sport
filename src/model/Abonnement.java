package model;

import java.time.LocalDate;

public class Abonnement {

    private int id;
    private int membreId;
    private String typeAbonnement;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;

    public Abonnement() {
    }

    public Abonnement(int id,
                      int membreId,
                      String typeAbonnement,
                      LocalDate dateDebut,
                      LocalDate dateFin,
                      String statut) {

        this.id = id;
        this.membreId = membreId;
        this.typeAbonnement = typeAbonnement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public void setTypeAbonnement(String typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}