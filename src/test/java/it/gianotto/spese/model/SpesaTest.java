package it.gianotto.spese.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpesaTest {

    @Test
    void emptyConstructorTestOk() {
        Spesa spesa = new Spesa();
        assertNotNull(spesa);
        assertEquals(Spesa.class, spesa.getClass());
    }

}
