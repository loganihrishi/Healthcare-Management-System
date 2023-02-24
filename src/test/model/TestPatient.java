package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// THIS IS A TEST CLASS FOR PATIENT
public class TestPatient {
    private Patient p1, p2;
    private Disease d1, d2;

    @BeforeEach
    public void testPatient() {
        p1 = new Patient("Gregor", 55, "M", "BC MSP", 1234567);
        p2 = new Patient("Kemi", 46, "F", "N/A" , 1234568);
        d1 = new Disease("TB", LocalDate.of(2021, 8, 02));
        d2 = new Disease("Cholera", LocalDate.now());
    }

    @Test
    public void testGetName() {
        assertEquals("Gregor", p1.getName());
        assertEquals("Kemi", p2.getName());
    }

    @Test
    public void testGetAge() {
        assertEquals(55, p1.getAge());
        assertEquals(46, p2.getAge());
    }

    @Test
    public void testGetSex() {
        assertEquals("M", p1.getSex());
        assertEquals("F", p2.getSex());
    }

    @Test
    public void testGetInsurance() {
        assertEquals("BC MSP", p1.getInsurance());
        assertEquals("N/A", p2.getInsurance());
    }

    @Test
    public void testSetInsurance() {
        p1.setInsurance("TATA HEALTH");
        assertEquals("TATA HEALTH", p1.getInsurance());
        p2.setInsurance("TATA INSURANCE");
        assertEquals("TATA INSURANCE", p2.getInsurance());
    }

    @Test
    public void testAddDiseases() {
        assertEquals(0, p1.getDiseases().size());
        p1.addDisease(d1.getName(), d1.getDiagnosedDate());
        assertEquals(1, p1.getDiseases().size());
        p1.addDisease(d2.getName(), d2.getDiagnosedDate());
        assertEquals(2, p1.getDiseases().size());
    }

    @Test
    public void TestDisplayDiseases() {
        assertEquals("", p1.displayDiseases());
        p1.addDisease(d1.getName(), d1.getDiagnosedDate());
        assertEquals("1. TB, 2021-08-02\n", p1.displayDiseases());
        p1.addDisease(d2.getName(), d2.getDiagnosedDate());
        assertEquals("1. TB, 2021-08-02\n" +
                "2. Cholera, " + LocalDate.now() + "\n", p1.displayDiseases());
    }

    @Test
    public void TestToString() {
        assertEquals("Patient: \n" +
                "Name: Gregor\n" +
                "Age: 55\n" +
                "Sex: M\n" +
                "Insurance: BC MSP\n" +
                "PHN: 1234567\n" +
                "Diseases: \n", p1.toString());
        p1.addDisease(d1.getName(), d1.getDiagnosedDate());
        p2.addDisease(d1.getName(), d1.getDiagnosedDate());
        p2.addDisease(d2.getName(), d2.getDiagnosedDate());
        assertEquals("Patient: \n" +
                "Name: Gregor\n" +
                "Age: 55\n" +
                "Sex: M\n" +
                "Insurance: BC MSP\n" +
                "PHN: 1234567\n" +
                "Diseases: \n" +
                "1. TB, 2021-08-02\n", p1.toString());
        assertEquals("Patient: \n" +
                "Name: Kemi\n" +
                "Age: 46\n" +
                "Sex: F\n" +
                "Insurance: N/A\n" +
                "PHN: 1234568\n" +
                "Diseases: \n" +
                "1. TB, 2021-08-02\n" +
                "2. Cholera, " + LocalDate.now() + "\n", p2.toString());
    }

    @Test
    public void TestGetDiseases() {
        List<Disease> diseases = new ArrayList<>();
        assertEquals(0, p1.getDiseases().size());
        p1.addDisease(d1.getName(), d1.getDiagnosedDate());
        diseases.add(d1);
        assertEquals(1, p1.getDiseases().size());
        diseases.add(d2);
        p1.addDisease(d2.getName(), d2.getDiagnosedDate());
        assertEquals(2, p1.getDiseases().size());
    }

    @Test
    public void TestSetDiseases_Empty(){
        List<Disease> diseases = new ArrayList<>();
        p1.setDiseases(diseases);
        assertEquals(0, p1.getDiseases().size());
        assertFalse(p1.getDiseases().contains(d1));
        assertFalse(p1.getDiseases().contains(d2));
    }

    @Test
    public void TestSetDiseases_One() {
        List<Disease> diseases = new ArrayList<>();
        diseases.add(d1);
        p1.setDiseases(diseases);
        assertEquals(1, p1.getDiseases().size());
        assertTrue(p1.getDiseases().contains(d1));
    }

    @Test
    public void TestSetDiseases_Multiple() {
        List<Disease> diseases = new ArrayList<>();
        diseases.add(d1);
        diseases.add(d2);
        p1.setDiseases(diseases);
        assertEquals(2, p1.getDiseases().size());
        assertTrue(p1.getDiseases().contains(d1));
        assertTrue(p1.getDiseases().contains(d2));
    }
}
