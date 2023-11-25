package it.gianotto.spese.dataIntegration;

import it.gianotto.spese.exception.DatabaseException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private MySqlConnection mySqlConnection;

    public TransactionManager(MySqlConnection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
    }

    public void startTransaction() {
        try {
            // apro una connessione al dbms
            Connection connection = mySqlConnection.getConnection();
            // il commit è l'operazione in cui viene confermato che il comando sql deve essere memorizzato e tutto ciò che ho fatto
            // deve comparire nelle tabelle del database, l'autocommit esegue in automatico il commit di ciò che faccio
            // in questo modo disattivo il fatto che ogni comando che arriva al dbms venga attivato subito
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException("Impossibile iniziare la transazione");
        }
    }

    public void rollbackTransaction() {
        try {
            Connection connection = mySqlConnection.getConnection();
            // è il metodo di rollback di una transazione
            connection.rollback();
        } catch (SQLException e) {
            throw new DatabaseException("Impossibile eseguire il rollback della transazione");
        }
    }

    public void closeTransaction() {
        try {
            Connection connection = mySqlConnection.getConnection();
            // questo metodo conferma tutte le operazioni che sono state fatte sul Dbms
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException("Impossibile eseguire il commit della transazione");
        }
    }
}
