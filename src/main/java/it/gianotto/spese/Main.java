package it.gianotto.spese;

import it.gianotto.spese.dao.SpesaDaoMySql;
import it.gianotto.spese.dataIntegration.MySqlConnection;
import it.gianotto.spese.model.Spesa;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        logger.info("SPESE JDBC - by Roberto Gianotto");
        MySqlConnection mySqlConnection = new MySqlConnection();
        mainApplicationMenu(mySqlConnection);
    }

    private static void mainApplicationMenu(MySqlConnection mySqlConnection) {
        Scanner s = new Scanner(System.in);

        System.out.println("Effettuare una scelta valida: ");
        System.out.println("1) Tutte le voci di spesa");
        System.out.println("2) Esci");

        int choice = s.nextInt();

        switch(choice) {
            case 1:
                SpesaDaoMySql spesaDaoMySql = new SpesaDaoMySql(mySqlConnection);
                // ritorna in output la lista delle spese
                List<Spesa> allExpenses = spesaDaoMySql.getAllSpese();
                System.out.println(allExpenses);
                break;
            case 2:
                // close connection
                mySqlConnection.closeConnection();
                break;
            default:
                System.out.println("Non hai effettuato una scelta tra quelle elencate disponibili.");
                break;
        }
    }
}
