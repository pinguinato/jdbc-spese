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
        // FIX: useDelimiter work on both Windows and Linux
        //s.useDelimiter(System.getProperty("line.separator"));
        s.useDelimiter("[;\r\n]");

        System.out.println("\n--------------------------------------------");
        System.out.println("|   SPESE JDBC                             |");
        System.out.println("|                   by Roberto Gianotto    |");
        System.out.println("|   November 2023                          |");
        System.out.println("--------------------------------------------\n");
        System.out.println("1) Visualizza tutte le spese");
        System.out.println("2) Visualizza una spesa");
        System.out.println("3) Aggiungi una spesa");
        System.out.println("4) Elimina una spesa");
        System.out.println("5) Stampa su file");
        System.out.println("6) Aggiorna una spesa");
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
            } else if (choice == 2) {
                SpesaDaoMySql spesaDaoMySql = new SpesaDaoMySql(mySqlConnection);
                // ritorna un dettaglio di una spesa in particolare
                System.out.println("Inserisci l'id della spesa che vuoi visualizzare: ");
                int idSpesa = s.nextInt();
                Spesa spesa = spesaDaoMySql.getSpesaDetails(idSpesa);
                System.out.println(spesa);
                // close connection
                mySqlConnection.closeConnection();
            } else if (choice == 3) {
                System.out.println("Inserisci una nuova spesa nel database delle spese: ");
                // inserimento dei nuovi dati per la nuova spesa
                System.out.println("Inserisci l'autore della nuova spesa:");
                String autoreSpesa = s.next();
                System.out.println("Inserisci il titolo della nuova spesa:");
                String titoloSpesa = s.next();
                System.out.println("Inserisci la descrizione della nuova spesa:");
                String descrizioneSpesa = s.next();
                System.out.println("Inserisci l'ammontare della nuova spesa:");
                double totaleSpesa = s.nextDouble();

                SpesaDaoMySql spesaDaoMySql = new SpesaDaoMySql(mySqlConnection);

                Spesa nuovaSpesa = Spesa.builder()
                        .titoloSpesa(titoloSpesa)
                        .descrizioneSpesa(descrizioneSpesa)
                        .autoreSpesa(autoreSpesa)
                        .totaleSpesa(totaleSpesa)
                        .build();

                System.out.println("Ho inserito nel database la nuova spesa di ID: " + spesaDaoMySql.addNewSpesa(nuovaSpesa));
            } else if (choice == 4) {
                System.out.println("Elimina una spesa nel database delle spese: ");
                System.out.println("Inserisci l'id della spesa da eliminare: ");
                int idSpesa = s.nextInt();

                SpesaDaoMySql spesaDaoMySql = new SpesaDaoMySql(mySqlConnection);
                spesaDaoMySql.deleteSpesaById(idSpesa);
            } else if (choice == 6) {
                System.out.println("Aggiorna una spesa nel database delle spese:");

                System.out.println("Inserisci i dati da aggiornare:");
                System.out.println("Inserisci l'id della spesa da aggiornare:");
                Integer idSpesaDaAggiornare = s.nextInt();

                System.out.println("Aggiorna l'autore della spesa:");
                String autoreSpesa = s.next();
                System.out.println("Aggiorna il titolo della spesa:");
                String titoloSpesa = s.next();
                System.out.println("Aggiorna la descrizione della spesa:");
                String descrizioneSpesa = s.next();
                System.out.println("Aggiorna l'ammontare della spesa:");
                double totaleSpesa = s.nextDouble();

                SpesaDaoMySql spesaDaoMySql = new SpesaDaoMySql(mySqlConnection);

                Spesa spesaAggiornata = Spesa.builder()
                        .idSpesa(idSpesaDaAggiornare)
                        .titoloSpesa(titoloSpesa)
                        .descrizioneSpesa(descrizioneSpesa)
                        .autoreSpesa(autoreSpesa)
                        .totaleSpesa(totaleSpesa)
                        .build();

                spesaDaoMySql.updateSpesa(spesaAggiornata);
                System.out.println("Aggiornamento della spesa effettuato");

            } else {
                System.out.println("Non hai effettuato una scelta tra quelle elencate disponibili.");
            }

            System.out.println("\n--------------------------------------------");
            System.out.println("|   SPESE JDBC                             |");
            System.out.println("|                   by Roberto Gianotto    |");
            System.out.println("|   November 2023                          |");
            System.out.println("--------------------------------------------\n");
            System.out.println("1) Visualizza tutte le spese");
            System.out.println("2) Visualizza una spesa");
            System.out.println("3) Aggiungi una spesa");
            System.out.println("4) Elimina una spesa");
            System.out.println("5) Stampa su file");
            System.out.println("6) Aggiorna una spesa");
            System.out.println("0) Esci dal programma");
            System.out.println("Effettuare una scelta valida: ");
            choice = s.nextInt();
        }
        // close the Scanner
        s.close();
    }
}
