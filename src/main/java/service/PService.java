package service;

import entite.Menu;
import entite.Plat;

import java.util.List;

public interface PService<T> {
    void ajouterPlat(Plat plat);

    void modifierPlat(Plat plat);

    void supprimerPlatById(int idP);

    void supprimerPlatByNom(String nomPlat);

    void modifierMenu(Menu menu);



    void ajouterMenu(Menu menu);

    void supprimerMenu(int idM);
    String platIdsToString(List<Plat> plats);
}
