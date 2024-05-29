package com.EasyHealth.entite;

import java.util.List;

public class Menu {
    private int idM;
    private String nomMenu;
    private static List<Plat> plats;

    // Constructors
    public Menu() {}

    public Menu(int idM, String nomMenu, List<Plat> plats) {
        this.idM = idM;
        this.nomMenu = nomMenu;
        this.plats = plats;
    }

    // Getters and Setters
    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public String getNomMenu() {
        return nomMenu;
    }

    public void setNomMenu(String nomMenu) {
        this.nomMenu = nomMenu;
    }

    public static List<Plat> getPlats() {
        return plats;
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }
}
