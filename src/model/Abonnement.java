package model;

public class Abonnement {


    private int id;
    private String type;
    private double prix;
    private int duree;
    private int membreId;

    public Abonnement() {
    }

    public Abonnement(int id, String type, double prix, int duree, int membreId) {
        this.id = id;
        this.type = type;
        this.prix = prix;
        this.duree = duree;
        this.membreId = membreId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }
}
