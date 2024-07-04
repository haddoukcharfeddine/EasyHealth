package service;

import java.util.List;

public interface PService<T> {

    void ajouterPlat(T t);

    void modifierPlat(T t);

    void supprimerPlatById(int id);

    void supprimerPlatByNom(String nom);

    List<T> getAllPlats();

    // Vous pouvez conserver d'autres méthodes génériques ici si nécessaire pour la gestion des plats
}
