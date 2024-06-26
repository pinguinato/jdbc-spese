package it.gianotto.spese.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SpesaTest {

    @Test
    void emptyConstructorTestOk() {
        Spesa spesa = new Spesa();
        assertNotNull(spesa);
        assertEquals(Spesa.class, spesa.getClass());
    }

    @Test
    void constructorTestOk() {
        Spesa spesa = new Spesa(1, "Test spesa", "test di una spesa", "Roberto Gianotto", 134.25, Date.valueOf(LocalDate.now()));
        assertNotNull(spesa);
        assertEquals(Spesa.class, spesa.getClass());
        System.out.println(spesa);
    }

    @Test
    @Disabled
    void invalidIdTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(null, "Test spesa", "test di una spesa", "Roberto Gianotto", 134.25, Date.valueOf(LocalDate.now()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(-1, "Test spesa", "test di una spesa", "Roberto Gianotto", 134.25, Date.valueOf(LocalDate.now()));
        });
    }

    @Test
    @Disabled
    void invalidTitoloTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, null, "test di una spesa", "Roberto Gianotto", 134.25, Date.valueOf(LocalDate.now()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "", "test di una spesa", "Roberto Gianotto", 134.25, Date.valueOf(LocalDate.now()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "aa", "test di una spesa", "Roberto Gianotto", 134.25, Date.valueOf(LocalDate.now()));
        });
    }

    @Test
    @Disabled
    void invalidTotaleSpesaTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "test spesa", "test di una spesa", "Roberto Gianotto", null, Date.valueOf(LocalDate.now()));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "test spesa", "test di una spesa", "Roberto Gianotto", -10.01, Date.valueOf(LocalDate.now()));
        });
    }

}
