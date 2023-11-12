package it.gianotto.spese.dataIntegration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class MySqlConnectionTest {

    private MySqlConnection mySqlConnection;

    @BeforeEach
    void setUp() throws Exception {
        mySqlConnection = new MySqlConnection();
    }

    @AfterEach
    void tearDown() throws Exception {
        mySqlConnection.closeConnection();
    }

    @Test
    void createMySqlConnectionTestOk() {
        assertNotNull(mySqlConnection);
    }

    @Test
    void getMySqlConnectionTestOk() {
        Connection connection = mySqlConnection.getConnection();
        assertNotNull(connection);
    }

    @Test
    void closeConnectionTestOk() {
        // apro una connessione
        Connection connection = mySqlConnection.getConnection();
        assertNotNull(connection);
        mySqlConnection.closeConnection();
        // verifico che sia NULL il connection status e vuol dire che la connessione è chiusa
        assertNull(mySqlConnection.getConnectionStatus());
    }

    @Test
    void connectionStatusTestOk() {
        // apro una connessione
        Connection connection = mySqlConnection.getConnection();
        assertNotNull(connection);
        assertNotNull(mySqlConnection.getConnectionStatus());
    }

    @Test
    void getConnectionStatusTest_ConnectionIsClosed() {
        Connection connection = mySqlConnection.getConnection();
        mySqlConnection.closeConnection();
        assertNotNull(connection);
        assertNull(mySqlConnection.getConnectionStatus());
    }

    @Test
    void getTablesFromDatabaseTestOk() throws SQLException {
        Connection connection = mySqlConnection.getConnection();
        List<String> tables = new ArrayList<>(List.of("spesa"));
        boolean allTables = true;

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tablesFromDatabase = metaData.getTables(null, null, "sample_table", null);

        while(tablesFromDatabase.next()) {
            System.out.println(tablesFromDatabase.getString(1));
            if (!tables.contains(tablesFromDatabase.getString(1))) {
                allTables = false;
            }
        }

        assertTrue(allTables);
    }
}
