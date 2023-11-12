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

        System.out.println("\n--------------------------------------------");
        System.out.println("|   SPESE JDBC                              |");
        System.out.println("|                   by Roberto Gianotto     |");
        System.out.println("|   November 2023                           |");
        System.out.println("--------------------------------------------\n");
        System.out.println("1) Visualizza tutte le spese");
        System.out.println("2) Visualizza una spesa");
        System.out.println("3) Aggiungi una spesa");
        System.out.println("4) Elimina una spesa");
        System.out.println("5) Stampa su file");
        System.out.println("0) Esci dal programma");
        System.out.println("Effettuare una scelta valida: ");
        int choice = s.nextInt();

        while(choice !=0) {

            if (choice == 1) {
                SpesaDaoMySql spesaDaoMySql = new SpesaDaoMySql(mySqlConnection);
                // ritorna in output la lista delle spese
                List<Spesa> allExpenses = spesaDaoMySql.getAllSpese();
                System.out.println(allExpenses);
                // close connection
                mySqlConnection.closeConnection();
            } else {
                System.out.println("Non hai effettuato una scelta tra quelle elencate disponibili.");
            }

            System.out.println("\n");
            System.out.println("1) Visualizza tutte le spese");
            System.out.println("2) Visualizza una spesa");
            System.out.println("3) Aggiungi una spesa");
            System.out.println("4) Elimina una spesa");
            System.out.println("5) Stampa su file");
            System.out.println("0) Esci dal programma");
            System.out.println("Effettuare una scelta valida: ");
            choice = s.nextInt();
        }
    }
}
