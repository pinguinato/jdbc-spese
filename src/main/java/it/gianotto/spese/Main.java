package it.gianotto.spese;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test Spese JDBC example, hello world!");
        Connection connection = null;
        String connectionUrl = "jdbc:mysql://localhost/spese";
        String mysqlUsername = "root";
        String mySqlPassword = "";

        try {

            System.out.println("Tentativo di connessione con DriverManager...");
            connection = DriverManager.getConnection(connectionUrl, mysqlUsername, mySqlPassword);
            System.out.println("Collegato al database con Driver Manager");

        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile ottenere una connessione da DriverManager";
            sqlException.printStackTrace();
        }

    }
}
