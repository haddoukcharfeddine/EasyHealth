package com.EasyHealth.service;

import com.EasyHealth.entite.Menu;
import com.EasyHealth.entite.Plat;

import java.util.List;

public interface PService<T> {
    void modifierPlat(Plat plat);

    void supprimerPlatById(int idP);

    void supprimerPlatByNom(String nomPlat);
    void ajouterPlat(Plat plat);
    void modifierMenu(Menu menu);



    void ajouterMenu(Menu menu);

    void supprimerMenu(int idM);
    String platIdsToString(List<Plat> plats);
}
