package it.gianotto.spese.dao;

import it.gianotto.spese.dataIntegration.MySqlConnection;
import it.gianotto.spese.dataIntegration.TransactionManager;
import it.gianotto.spese.model.Spesa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SpesaDaoMySqlTest {

    private SpesaDaoMySql spesaDaoMySql;
    private MySqlConnection mySqlConnection;
    private TransactionManager transactionManager;

    private Spesa spesaTest1;
    private Spesa spesaTest2;

    // tutto ciò che avviene prima di ogni metodo di test
    @BeforeEach
    void setUp() throws Exception {
        mySqlConnection = new MySqlConnection(); // creo una nuova connessione al DB
        spesaDaoMySql = new SpesaDaoMySql(mySqlConnection); // creo un nuovo DAO
        transactionManager = new TransactionManager(mySqlConnection);

        transactionManager.startTransaction(); // avvia una transazione

        // questi dati devono essere presenti nella tabella di mysql
        spesaTest1 = new Spesa(1, "Spesa di Test 1", "Un esempio di spesa di prova 1", "Roberto Gianotto", 10.00);
        spesaTest2 = new Spesa(2, "Spesa di Test 2", "Un esempio di spesa di prova 2", "Roberto Gianotto", 10.00);
    }

    @AfterEach
    void tearDown() throws Exception {
        // questa funziona può annullare le operazioni precedenti
        transactionManager.rollbackTransaction();
        spesaDaoMySql.resettaAutoIncrement(); // reset dell'auto increment in caso di inserimenti di test
        mySqlConnection.closeConnection();
    }

    @Test
    void creaUnaSpesaTestOK() {
        assertNotNull(spesaDaoMySql);
    }

    @Test
    void creaUnaNuovaSpesa_MySqlConnectionNull_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
           new SpesaDaoMySql(null);
        });
    }

    @Test
    @Disabled
    void getAllSpeseTestOK() {
        List<Spesa> list = spesaDaoMySql.getAllSpese();
        assertEquals(1, list.size());
    }


}
