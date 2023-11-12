package it.gianotto.spese.dao;

import it.gianotto.spese.model.Spesa;
import java.util.List;

interface SpesaDao {

    List<Spesa> getAllSpese();

    Spesa getSpesaDetails(int idSpesa);

    int addNewSpesa(Spesa spesa);

    void deleteSpesaById(int idSpesa);

    void updateSpesa(Spesa spesa);
}
