package it.gianotto.spese.dao;


import it.gianotto.spese.dataIntegration.MySqlConnection;
import it.gianotto.spese.exception.DatabaseException;
import it.gianotto.spese.model.Spesa;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SpesaDaoMySql implements SpesaDao {

    private final Connection connection;

    public SpesaDaoMySql(MySqlConnection mySqlConnection) {
        if (Objects.isNull(mySqlConnection)) {
            String errorMessage = "MySqlConnection non può essere nullo";
            throw new IllegalArgumentException(errorMessage);
        }
        this.connection = mySqlConnection.getConnection();
        if (Objects.isNull(connection)) {
            String errorMessage = "Impossibile connettersi al database";
            throw new DatabaseException(errorMessage);
        }
    }

    @Override
    public List<Spesa> getAllSpese() {
        String sql = "SELECT id_spesa, titolo_spesa, descrizione_spesa, ammontare_spesa, autore_spesa, data_spesa " +
                "FROM spesa";
        try {
            // get the sql query
            PreparedStatement statement = connection.prepareStatement(sql);
            // execute query
            ResultSet rs = statement.executeQuery();

            return handleAllSpese(rs);
        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile eseguire la query in getAllSpese: " + sqlException.getMessage();
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    // metodo di supporto per costruire la lista delle spese
    private List<Spesa> handleAllSpese(ResultSet rs) throws SQLException {
        List<Spesa> spesaList = new ArrayList<>();

        while(rs.next()) {

            int idSpesa = rs.getInt("id_spesa");
            String titoloSpesa = rs.getString("titolo_spesa");
            String descrizioneSpesa = rs.getString("descrizione_spesa");
            Double totaleSpesa = rs.getDouble("ammontare_spesa");
            String autoreSpesa = rs.getString("autore_spesa");
            // get date of spesa
            Date dataSpesa = Objects.nonNull(rs.getDate("data_spesa")) ? rs.getDate("data_spesa") : null;

            Spesa spesa = Spesa.builder()
                    .idSpesa(idSpesa)
                    .titoloSpesa(titoloSpesa)
                    .descrizioneSpesa(Objects.nonNull(descrizioneSpesa) ? descrizioneSpesa : null)
                    .autoreSpesa(Objects.nonNull(autoreSpesa) ? autoreSpesa : null)
                    .totaleSpesa(totaleSpesa)
                    .dataSpesa(dataSpesa)
                    .build();
            // add Spesa to list
            spesaList.add(spesa);
        }

        return spesaList;
    }


    @Override
    public Spesa getSpesaDetails(int idSpesa) {
        if (idSpesa < 0) {
            throw new IllegalArgumentException("L'ID di spesa non può essere un numero negativo.");
        }
        String sql = "SELECT id_spesa, titolo_spesa, descrizione_spesa, ammontare_spesa, autore_spesa " +
                "FROM spesa WHERE id_spesa = ?";
        try {
            // get the sql query
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSpesa);
            // execute query
            ResultSet rs = statement.executeQuery();
            Spesa spesaDetails = handleSingleSpesa(rs);

            if (Objects.isNull(spesaDetails)) {
                throw new IllegalArgumentException("Non esiste una spesa che abbia questo ID nel database");
            }

            return spesaDetails;
        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile eseguire la query in getSpesaDetails";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    private Spesa handleSingleSpesa(ResultSet rs) throws SQLException {
        Spesa spesa = null;
        if (rs.next()) {
            // recupera i dati di una singola spesa
            int idSpesa = rs.getInt("id_spesa");
            String titoloSpesa = rs.getString("titolo_spesa");
            String descrizioneSpesa = rs.getString("descrizione_spesa");
            Double totaleSpesa = rs.getDouble("ammontare_spesa");
            String autoreSpesa = rs.getString("autore_spesa");

            spesa = Spesa.builder()
                    .idSpesa(idSpesa)
                    .titoloSpesa(titoloSpesa)
                    .descrizioneSpesa(Objects.nonNull(descrizioneSpesa) ? descrizioneSpesa : null)
                    .autoreSpesa(Objects.nonNull(autoreSpesa) ? autoreSpesa : null)
                    .totaleSpesa(totaleSpesa)
                    .build();
        }
        return spesa;
    }

    @Override
    public int addNewSpesa(Spesa spesa) {
        if (Objects.isNull(spesa)) {
            throw new IllegalArgumentException("La spesa da inserire non può essere nulla.");
        }

        return handleAddNewSpesa(spesa);
    }

    private int handleAddNewSpesa(Spesa spesa) {
        String sql = "INSERT INTO spesa (titolo_spesa,descrizione_spesa,ammontare_spesa,autore_spesa,data_spesa) " +
                "VALUES (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, spesa.getTitoloSpesa());
            preparedStatement.setString(2, spesa.getDescrizioneSpesa());
            preparedStatement.setDouble(3, spesa.getTotaleSpesa());
            preparedStatement.setString(4, spesa.getAutoreSpesa());
            preparedStatement.setObject(5, LocalDateTime.now());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedAutoIncrementID = -1;
            while(rs.next()) {
                generatedAutoIncrementID = rs.getInt(1);
                System.out.println("generatedAutoIncrementId: " + generatedAutoIncrementID);
            }
            return generatedAutoIncrementID;
        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile eseguire la query di inserimento in addNewSpesa";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    @Override
    public void deleteSpesaById(int idSpesa) {
        // controllo se un id ha un formato valido
        if (idSpesa < 0) {
            throw new IllegalArgumentException("L'id di una spesa non può essere negativo.");
        }

        String sql = "DELETE FROM spesa WHERE id_spesa = ? ";

        try {
            int affectedRows;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSpesa);
            affectedRows = statement.executeUpdate();
            // verifico se vado ad eliminare una spesa che esiste veramente oppure no
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Non esiste un account con questo ID");
            }
        } catch (SQLException sqlException) {
            // questo nel caso ci sia un vero e proprio errore MySQL
            String errorMessage = "Impossibile eseguire la delete di una spesa in deleteSpesaById";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    @Override
    public void updateSpesa(Spesa spesa) {
        if (Objects.isNull(spesa)) {
            throw new IllegalArgumentException("La spesa non può essare nulla.");
        }
        // aggiornamento di una spesa nel database
        handleUpdateSpesa(spesa);
    }

    private void handleUpdateSpesa(Spesa spesa) {
        String sql = "UPDATE spesa SET titolo_spesa = ?, descrizione_spesa = ?, ammontare_spesa = ?, autore_spesa = ? WHERE id_spesa = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, spesa.getTitoloSpesa());
            preparedStatement.setString(2, spesa.getDescrizioneSpesa());
            preparedStatement.setDouble(3, spesa.getTotaleSpesa());
            preparedStatement.setString(4, spesa.getAutoreSpesa());
            preparedStatement.setInt(5, spesa.getIdSpesa());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            // questo nel caso ci sia un vero e proprio errore MySQL
            String errorMessage = "Impossibile eseguire l'aggiornamento di una spesa in handleUpdateSpesa";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    public void resettaAutoIncrement() {
        String sql = "ALTER TABLE spesa AUTO_INCREMENT = 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile eseguire il reset dell'auto increment a 1";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    @Override
    public Double getTotalSpese() {

        String sql = "SELECT SUM(s.ammontare_spesa) AS total FROM spese.spesa s";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            return handleGetTotalSpese(rs);

        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile eseguire la query in getTotalSpese";
            sqlException.printStackTrace();
            throw new DatabaseException(errorMessage);
        }
    }

    private Double handleGetTotalSpese(ResultSet rs) throws SQLException {
        Double total = null;

        while(rs.next()) {
            total = rs.getDouble("total");
        }

        return total;
    }
}
