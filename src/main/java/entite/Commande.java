package entite;

import java.util.Date;
import java.util.List;

public class Commande {
    private int idc;
    private Date date;
    private double prixTotal;
    private List<Plat> platsChoisis;
    private int idUClient;
    private int idUVendeur;
    private int idULivreur;

    // Constructors
    public Commande() {}

    public Commande(int idc, Date date, double prixTotal, List<Plat> platsChoisis, int idUClient, int idUVendeur, int idULivreur) {
        this.idc = idc;
        this.date = date;
        this.prixTotal = prixTotal;
        this.platsChoisis = platsChoisis;
        this.idUClient = idUClient;
        this.idUVendeur = idUVendeur;
        this.idULivreur = idULivreur;
    }


    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<Plat> getPlatsChoisis() {
        return platsChoisis;
    }

    public void setPlatsChoisis(List<Plat> platsChoisis) {
        this.platsChoisis = platsChoisis;
    }

    public int getIdUClient() {
        return idUClient;
    }

    public void setIdUClient(int idUClient) {
        this.idUClient = idUClient;
    }

    public int getIdUVendeur() {
        return idUVendeur;
    }

    public void setIdUVendeur(int idUVendeur) {
        this.idUVendeur = idUVendeur;
    }

    public int getIdULivreur() {
        return idULivreur;
    }

    public void setIdULivreur(int idULivreur) {
        this.idULivreur = idULivreur;
    }

    // Methods
    public void calculerPrixTotal() {

    }
}


