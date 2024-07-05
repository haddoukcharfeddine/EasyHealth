package service;

import java.util.List;

// Interface générique pour le service de gestion des plats
public interface PService<T> {

    // Méthode pour ajouter un plat
    void ajouterPlat(T t);

    // Méthode pour modifier un plat
    void modifierPlat(T t);

    // Méthode pour supprimer un plat par son identifiant
    void supprimerPlatById(int id);

    // Méthode pour supprimer un plat par son nom
    void supprimerPlatByNom(String nom);

    // Méthode pour récupérer tous les plats
    List<T> getAllPlats();

    // Vous pouvez ajouter d'autres méthodes spécifiques si nécessaires pour la gestion des plats
}
