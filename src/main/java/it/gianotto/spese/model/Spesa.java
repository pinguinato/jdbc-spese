package it.gianotto.spese.model;

import lombok.Data;
import java.util.Objects;

@Data
public class Spesa {
    private Integer idSpesa;
    private String titoloSpesa;
    private String descrizioneSpesa;
    private String autoreSpesa;
    private Double totaleSpesa;

    public Spesa() {
        // costruttore vuoto}
    }

    public Spesa(Integer idSpesa, String titoloSpesa, String descrizioneSpesa, String autoreSpesa, Double totaleSpesa) {

        validazioniOggettoSpesa(idSpesa, titoloSpesa, descrizioneSpesa, autoreSpesa, totaleSpesa);

        this.idSpesa = idSpesa;
        this.titoloSpesa = titoloSpesa;
        this.descrizioneSpesa = descrizioneSpesa;
        this.autoreSpesa = autoreSpesa;
        this.totaleSpesa = totaleSpesa;
    }

    private void validazioniOggettoSpesa(Integer idSpesa, String titoloSpesa, String descrizioneSpesa, String autoreSpesa, Double totaleSpesa) {
        // l'id di una spesa non deve essere nullo
        if (Objects.isNull(idSpesa)) {
            throw new IllegalArgumentException("L'ID di una spesa non può essere nullo.");
        }
        // l'id di una spesa non può essere un numero minore di 0
        if (idSpesa < 0) {
            throw new IllegalArgumentException("L'ID di una spesa non può essere un numero negativo.");
        }
        // il titolo di una spesa non deve essere NULL, o stringa vuota
        if (Objects.isNull(titoloSpesa) || titoloSpesa.isBlank()) {
            throw new IllegalArgumentException("Il titolo di una spesa non può essere nullo oppure vuoto.");
        }
        // il titolo di una spesa deve avere una lunghezza accettabile minima di caratteri stringa
        if (titoloSpesa.length() < 3) {
            throw new IllegalArgumentException("Il titolo di una spesa non può avere meno di 3 carsatteri.");
        }
    }
}
