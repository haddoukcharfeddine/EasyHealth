package entite.Users;

import entite.Enum.UserType;

public class Livreur extends User {
    private boolean disponible;

    public Livreur(int id, String nom, String email, String telephone, String adresse,String password, boolean disponible) {
        super(id, nom, email, telephone, adresse,password, UserType.Livreur);
        this.disponible = disponible;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void accepterCommande() {

    }

    public void suivreLivraison() {

    }

    public void notifierClient() {

    }
}



