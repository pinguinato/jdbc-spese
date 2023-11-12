package it.gianotto.spese.dataIntegration;

import it.gianotto.spese.exception.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class MySqlConnection {
    // Connection: oggetto java che mappa una connesisone con un dbms
    // getConnection() --> restituisce all'esterno l'oggetto connection
    // openConnection() --> metodo che si occupa di aprire una nuova connessione con MySql
    // closeConnection() --> metodo che si occupa di chiudere la connessione con MySql
    // getConnectionStatus() --> metodo che si occupa di ritornare informazioni sulla connessione attuale

    private Connection dbConnection = null;

    public MySqlConnection() { /* empty constructor */ }

    public Connection getConnection() {
        if (Objects.isNull(dbConnection)) dbConnection = openConnection();

        return dbConnection;
    }

    private Connection openConnection() {
        // oggetto della connessione al DB
        Connection connection = null;
        // oggetto per agganciare il file di properties
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
            // db.properties load
            String dbUrl = properties.getProperty("MYSQL_DB_URL");
            String dbUsername = properties.getProperty("MYSQL_DB_USERNAME");
            String dbPassword = properties.getProperty("MYSQL_DB_PASSWORD");

            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        } catch (IOException ioException) {
            String errorMessage = "Impossibile leggere dal file db.properties";
            ioException.printStackTrace();
            throw new DatabaseException(errorMessage);
        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile ottenere una connessione da DriverManager";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }

        return connection;
    }

    public void closeConnection() {
        if (Objects.nonNull(dbConnection)) {
            try {
                dbConnection.close();
                dbConnection = null;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {
            System.out.println("Connessione già chiusa.");
        }
    }

    public String getConnectionStatus() {
        return Objects.nonNull(dbConnection) ? dbConnection.toString() : null;
    }

}
