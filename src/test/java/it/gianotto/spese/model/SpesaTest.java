package it.gianotto.spese.model;

import org.junit.jupiter.api.Test;
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
        Spesa spesa = new Spesa(1, "Test spesa", "test di una spesa", "Roberto Gianotto", 134.25);
        assertNotNull(spesa);
        assertEquals(Spesa.class, spesa.getClass());
        System.out.println(spesa);
    }

    @Test
    void invalidIdTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(null, "Test spesa", "test di una spesa", "Roberto Gianotto", 134.25);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(-1, "Test spesa", "test di una spesa", "Roberto Gianotto", 134.25);
        });
    }

    @Test
    void invalidTitoloTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, null, "test di una spesa", "Roberto Gianotto", 134.25);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "", "test di una spesa", "Roberto Gianotto", 134.25);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "aa", "test di una spesa", "Roberto Gianotto", 134.25);
        });
    }

    @Test
    void invalidTotaleSpesaTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "test spesa", "test di una spesa", "Roberto Gianotto", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Spesa(1111, "test spesa", "test di una spesa", "Roberto Gianotto", -10.01);
        });
    }

}
