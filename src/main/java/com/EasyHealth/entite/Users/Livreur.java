package com.EasyHealth.entite.Users;

import com.EasyHealth.entite.Enum.UserType;

public class Livreur extends User {
    private boolean disponible;

    public Livreur(int id, String nom, String email, String telephone, String adresse, boolean disponible) {
        super(id, nom, email, telephone, adresse, UserType.Livreur);
        this.disponible = disponible;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void accepterCommande() {
        // Implementation here
    }

    public void suivreLivraison() {
        // Implementation here
    }

    public void notifierClient() {
        // Implementation here
    }
}



