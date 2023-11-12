package it.gianotto.spese.dao;


import it.gianotto.spese.dataIntegration.MySqlConnection;
import it.gianotto.spese.exception.DatabaseException;
import it.gianotto.spese.model.Spesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpesaDaoMySql implements SpesaDao {

    private Connection connection;

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
        String sql = "SELECT id_spesa, titolo_spesa, descrizione_spesa, ammontare_spesa, autore_spesa " +
                "FROM spesa";
        try {
            // get the sql query
            PreparedStatement statement = connection.prepareStatement(sql);
            // execute query
            ResultSet rs = statement.executeQuery();

            return handleAllSpese(rs);
        } catch (SQLException sqlException) {
            String errorMessage = "Impossibile eseguire la query in getAllSpese";
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

            Spesa spesa = Spesa.builder()
                    .idSpesa(idSpesa)
                    .titoloSpesa(titoloSpesa)
                    .descrizioneSpesa(Objects.nonNull(descrizioneSpesa) ? descrizioneSpesa : null)
                    .autoreSpesa(Objects.nonNull(autoreSpesa) ? autoreSpesa : null)
                    .totaleSpesa(totaleSpesa)
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

        int generatedAutoIncrementId = handleAddNewSpesa(spesa);
        return generatedAutoIncrementId;
    }

    private int handleAddNewSpesa(Spesa spesa) {
        String sql = "INSERT INTO spesa (titolo_spesa,descrizione_spesa,ammontare_spesa,autore_spesa) " +
                "VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, spesa.getTitoloSpesa());
            preparedStatement.setString(2, spesa.getDescrizioneSpesa());
            preparedStatement.setDouble(3, spesa.getTotaleSpesa());
            preparedStatement.setString(4, spesa.getAutoreSpesa());
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
    public void deleteSpesaById(int idSpesa) { /* document why this method is empty */ }

    @Override
    public void updateSpesa(Spesa spesa) {
        // missing implementation
    }


}
