package com.formation;

public class Fournisseur {
    
    private int id;
    private String nom;
    
    public Fournisseur(){

    }

    public Fournisseur(int id2, String nom){
        this.nom = nom;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
}
