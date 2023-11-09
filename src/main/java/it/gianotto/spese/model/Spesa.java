package it.gianotto.spese.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spesa {
    private Integer idSpesa;
    private String titoloSpesa;
    private String descrizioneSpesa;
    private String autoreSpesa;
    private Double totaleSpesa;
}
