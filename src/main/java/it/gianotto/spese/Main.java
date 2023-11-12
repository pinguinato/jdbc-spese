package it.gianotto.spese;

import it.gianotto.spese.dataIntegration.MySqlConnection;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        logger.info("Test Spese JDBC example, hello world!");
        logger.info("Tentativo di connessione con DriverManager...");
        MySqlConnection mySqlConnection = new MySqlConnection();
        mySqlConnection.getConnection();
        logger.info("Collegato al database con Driver Manager");
        logger.info(mySqlConnection.getConnectionStatus());
        mySqlConnection.closeConnection();
    }
}
