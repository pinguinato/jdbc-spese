package it.gianotto.spese.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SpesaDTO {
    private Integer idSpesa;
    private String titoloSpesa;
    private String descrizioneSpesa;
    private String autoreSpesa;
    private Double totaleSpesa;
    private LocalDateTime dataSpesa;
}
