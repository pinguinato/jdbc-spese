package it.gianotto.spese.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
public class Spesa {
    private Integer idSpesa;
    private String titoloSpesa;
    private String descrizioneSpesa;
    private String autoreSpesa;
    private Double totaleSpesa;

    public Spesa() {
    }

    public Spesa(Integer idSpesa, String titoloSpesa, String descrizioneSpesa, String autoreSpesa, Double totaleSpesa) {

        //validazioniOggettoSpesa(idSpesa, titoloSpesa, totaleSpesa);

        this.idSpesa = idSpesa;
        this.titoloSpesa = titoloSpesa;
        this.descrizioneSpesa = descrizioneSpesa;
        this.autoreSpesa = autoreSpesa;
        this.totaleSpesa = totaleSpesa;
    }

    @Override
    public String toString() {
        return "{id:" + idSpesa + ", titolo:" + titoloSpesa + ", descrizione:" + descrizioneSpesa + ", autore:" + autoreSpesa + ", totale in euro:" + totaleSpesa+"}";
    }

    private void validazioniOggettoSpesa(Integer idSpesa, String titoloSpesa, Double totaleSpesa) {
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
        // il totale di una spesa non può essere NULL
        if (Objects.isNull(totaleSpesa)) {
            throw new IllegalArgumentException("Il totale di una spesa non può essere nullo.");
        }
        // il totale di una spesa non deve essere mai un numero negativo
        if (totaleSpesa < 0) {
            throw new IllegalArgumentException("Il totale di una spesa non può essere un numero negativo.");
        }
    }
}
