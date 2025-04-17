package com.formation;

import java.util.List;

public interface FournisseurDAO {

    void insert (Fournisseur fournisseur);

    void update(String ancienNom, String nouveauNom);

    void delete (int id);

    List<Fournisseur> findAll ();
    
}
