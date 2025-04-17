package com.formation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAOJdbc implements FournisseurDAO {

    private Connection connection;

    public FournisseurDAOJdbc() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/formation", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
    }

    @Override
    public void insert(Fournisseur fournisseur) {
        String sql = "INSERT INTO fournisseur (nom) VALUES ('" + fournisseur.getNom() + "')";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Fournisseur inséré : " + fournisseur.getNom());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String ancienNom, String nouveauNom) {
        String sql = "UPDATE fournisseur SET nom = '" + nouveauNom + "' WHERE nom = '" + ancienNom + "'";
        try (Statement stmt = connection.createStatement()) {
            int updated = stmt.executeUpdate(sql);
            if (updated > 0) {
                System.out.println("Fournisseur mis à jour : " + ancienNom + " -> " + nouveauNom);
            } else {
                System.out.println("Aucun fournisseur trouvé avec le nom : " + ancienNom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM fournisseur WHERE id = " + id;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Fournisseur supprimé avec ID : " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Fournisseur> findAll() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseur";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                Fournisseur f = new Fournisseur(id, nom);
                fournisseurs.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fournisseurs;
    }
}
