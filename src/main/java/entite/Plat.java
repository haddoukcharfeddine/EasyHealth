package entite;

public class Plat {
    private int idP;
    private String nomPlat;
    private String description;
    private double prix;
    private int protein;
    private int calories;
    private int idUVendeur;
    private String Categorie;

    // Constructors


    public Plat(int idP, String nomPlat, String description, double prix, int protein, int calories, int idUVendeur,String categorie) {
        this.idP = idP;
        this.nomPlat = nomPlat;
        this.description = description;
        this.prix = prix;
        this.protein = protein;
        this.calories = calories;
        this.idUVendeur = idUVendeur;
        this.Categorie =categorie;
    }

    // Getters and Setters
    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getIdUVendeur() {
        return idUVendeur;
    }

    public void setIdUVendeur(int idUVendeur) {
        this.idUVendeur = idUVendeur;
    }

    public String getCategorie() {return Categorie;}

    public void setCategorie(String categorie) {Categorie = categorie;}
}


