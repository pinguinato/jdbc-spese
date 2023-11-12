package it.gianotto.spese.dao;


import it.gianotto.spese.model.Spesa;
import java.util.ArrayList;
import java.util.List;

public class SpesaDaoMySql implements SpesaDao {

    @Override
    public List<Spesa> getAllSpese() {
        return new ArrayList<>();
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
