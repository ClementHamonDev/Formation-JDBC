package com.formation;

import java.sql.*;
import java.util.Scanner;

public class TestFournisseurOperations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/formation", "root", "");
            Statement stmt = connection.createStatement()
        ) {
            // 1. Créer la table Fournisseur si elle n'existe pas
            String createTable = "CREATE TABLE IF NOT EXISTS fournisseur (" +
                                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                 "nom VARCHAR(255) NOT NULL)";
            stmt.execute(createTable);
            System.out.println("Table fournisseur vérifiée/créée.");

            // 2. Insérer une ligne
            String insertSql = "INSERT INTO fournisseur (nom) VALUES (?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, "Premier Fournisseur");
                ps.executeUpdate();
                System.out.println("Fournisseur 'Premier Fournisseur' inséré.");

                ps.setString(1, "Deuxième Fournisseur");
                ps.executeUpdate();
                System.out.println("Fournisseur 'Deuxième Fournisseur' inséré.");

                ps.setString(1, "Troisième Fournisseur");
                ps.executeUpdate();
                System.out.println("Fournisseur 'Troisième Fournisseur' inséré.");
            }

            // 3. Recherche par nom (LIKE)
            System.out.print("Entrez une partie du nom à rechercher : ");
            String rechercheNom = scanner.nextLine();
            String searchSql = "SELECT * FROM fournisseur WHERE nom LIKE ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSql)) {
                ps.setString(1, "%" + rechercheNom + "%");
                ResultSet rs = ps.executeQuery();
                System.out.println("Résultats de la recherche :");
                boolean found = false;
                while (rs.next()) {
                    System.out.println(" - ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom"));
                    found = true;
                }
                if (!found) System.out.println("Aucun résultat.");
                rs.close();
            }

            // 4. Recherche par ID
            System.out.print("Entrez un ID de fournisseur à rechercher : ");
            int idRecherche = Integer.parseInt(scanner.nextLine());
            String searchById = "SELECT * FROM fournisseur WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchById)) {
                ps.setInt(1, idRecherche);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("Fournisseur trouvé : " +
                        "ID = " + rs.getInt("id") + ", Nom = " + rs.getString("nom"));
                } else {
                    System.out.println("Aucun fournisseur trouvé avec cet ID.");
                }
                rs.close();
            }

            // 5. Insertion multiple
            System.out.print("Combien de fournisseurs voulez-vous insérer ? ");
            int nbFournisseurs = Integer.parseInt(scanner.nextLine());
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                for (int i = 1; i <= nbFournisseurs; i++) {
                    System.out.print("  Nom du fournisseur " + i + " : ");
                    String nom = scanner.nextLine();
                    ps.setString(1, nom);
                    ps.executeUpdate();
                }
                System.out.println("Fournisseurs ajoutés.");
            }

            // 6. Suppression par mot-clé
            System.out.print("Entrez un mot-clé pour supprimer les fournisseurs contenant ce mot : ");
            String motCle = scanner.nextLine();
            String deleteSql = "DELETE FROM fournisseur WHERE nom LIKE ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
                ps.setString(1, "%" + motCle + "%");
                int nbSupprimes = ps.executeUpdate();
                System.out.println("Fournisseurs supprimés : " + nbSupprimes);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de saisie : " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

