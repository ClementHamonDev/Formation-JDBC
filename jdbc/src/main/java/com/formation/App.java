package com.formation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ResourceBundle config = ResourceBundle.getBundle("database");

        String url = config.getString("db.url");
        String user = config.getString("db.user");

        Statement stmt = null;
        ResultSet rs = null;

        try (Connection conn = DriverManager.getConnection(url, user, "")) {
            String read = "SELECT * from utilisateurs";

            String table = "CREATE TABLE Client(id INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(100))";

            stmt = conn.createStatement();
            boolean resultat = stmt.execute(table);
            rs = stmt.executeQuery(read);

            if (resultat) {
                // TODO
            }

            String insertString = "INSERT INTO Client (nom) VALUES (?)";
            try(PreparedStatement ps = conn.prepareStatement(insertString)){

                String[] noms = {"Clément", "Axel", "Adam"};
                for(String nom : noms){
                    ps.setString(1, nom);
                    ps.executeUpdate();
                    System.out.println("CLient ajouté : " + nom);
                }
            }

            while (rs.next()) {
                // System.out.println(rs.getInt("id") + " - " + rs.getString("nom"));
            }

        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
                e.printStackTrace();
            }

        }
    }
}
