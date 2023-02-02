package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class TestDisease {
    private Disease d1, d2;

    @BeforeEach
    public void setUp() {
        d1 = new Disease("TB", LocalDate.of(2021, 8, 02));
        d2 = new Disease("Cholera", LocalDate.now());
    }

    @Test
    public void testGetName() {
        assertEquals("TB", d1.getName());
        assertEquals("Cholera", d2.getName());
    }

    @Test
    public void testGetDate() {
        assertEquals(LocalDate.of(2021, 8, 02) ,d1.getDiagnosedDate());
        assertEquals(LocalDate.now(), d2.getDiagnosedDate());
    }
}
