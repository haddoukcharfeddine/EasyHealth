package entite;

// Classe représentant un plat
public class Plat {
    private int idP;              // Identifiant du plat
    private String nomPlat;       // Nom du plat
    private String description;   // Description du plat
    private double prix;          // Prix du plat
    private int protein;          // Quantité de protéines dans le plat
    private int calories;         // Nombre de calories dans le plat
    private String idUVendeur;    // Identifiant unique du vendeur du plat
    private String categorie;     // Catégorie du plat
    private byte[] imageData;    // Données de l'image du plat sous forme de tableau d'octets

    // Constructeur pour initialiser un plat avec toutes ses propriétés
    public Plat(int idP, String nomPlat, String description, double prix, int protein, int calories, String idUVendeur, String categorie, byte[] imageData) {
        this.idP = idP;
        this.nomPlat = nomPlat;
        this.description = description;
        this.prix = prix;
        this.protein = protein;
        this.calories = calories;
        this.idUVendeur = idUVendeur;
        this.categorie = categorie;
        this.imageData = imageData;
    }

    // Getters and setters pour accéder et modifier les attributs privés

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

    public String getIdUVendeur() {
        return idUVendeur;
    }

    public void setIdUVendeur(String idUVendeur) {
        this.idUVendeur = idUVendeur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}


