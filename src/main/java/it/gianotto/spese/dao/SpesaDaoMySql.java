package it.gianotto.spese.dao;


import it.gianotto.spese.dataIntegration.MySqlConnection;
import it.gianotto.spese.exception.DatabaseException;
import it.gianotto.spese.model.Spesa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public int addNewSpesa(Spesa spesa) {
        return 1;
    }

    @Override
    public void deleteSpesaById(int idSpesa) { /* document why this method is empty */ }

    @Override
    public void updateSpesa(Spesa spesa) {
        // missing implementation
    }


}
