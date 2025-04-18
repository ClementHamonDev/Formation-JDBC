package com.formation;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FournisseurDaoJdbc2 implements FournisseurDAO {
    private Connection connection;

    public FournisseurDaoJdbc2(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Fournisseur fournisseur) {
        String sql = "INSERT INTO fournisseur (id, nom) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fournisseur.getId());
            stmt.setString(2, fournisseur.getNom());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String ancienNom, String nouveauNom) {
        String sql = "UPDATE fournisseur SET nom = ? WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nouveauNom);
            stmt.setString(2, ancienNom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM fournisseur WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Fournisseur> findAll() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT id, nom FROM fournisseur";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Fournisseur fournisseur = new Fournisseur();
                fournisseur.setId(rs.getInt("id"));
                fournisseur.setNom(rs.getString("nom"));
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }
    
}
