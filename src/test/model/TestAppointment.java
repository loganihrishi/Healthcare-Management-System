package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;

// THIS IS A TEST CLASS FOR APPOINTMENT
public class TestAppointment {
    private Appointment a1, a2;
    private Patient p1, p2;
    private Disease d1, d2;

    @BeforeEach
    public void setUp() {
        p1 = new Patient("Gregor", 55, "M", "BC MSP", 1234567);
        p2 = new Patient("Kemi", 46, "F", "N/A" , 1234568);
        d1 = new Disease("TB", LocalDate.of(2021, 8, 02));
        d2 = new Disease("Cholera", LocalDate.now());
        a1 = new Appointment(LocalDate.of(2023, 01, 31), LocalTime.of(12,0),
                p1);
        a2 = new Appointment(LocalDate.of(2023, 01, 15), LocalTime.of(15,0),
                p2);
    }

    @Test
    public void TestGetDate() {
        assertEquals(LocalDate.of(2023, 01, 31), a1.getDate());
        assertEquals(LocalDate.of(2023, 01, 15), a2.getDate());
    }

    @Test
    public void TestGetTime() {
        assertEquals(LocalTime.of(12,00), a1.getTime());
        assertEquals(LocalTime.of(15,0), a2.getTime());
    }

    @Test
    public void TestSetDate()
    {
        a1.setDate(LocalDate.now());
        assertEquals(LocalDate.now(), a1.getDate());
        a2.setDate(LocalDate.of(2022, 01, 15));
        assertEquals(LocalDate.of(2022, 01, 15), a2.getDate());
    }
    @Test
    public void TestGetPatient() {
        assertEquals(p1, a1.getPatient());
        assertEquals(p2, a2.getPatient());
    }

    @Test
    public void TestSetTime() {
        a1.setTime(LocalTime.of(18, 00));
        assertEquals(LocalTime.of(18, 0), a1.getTime());
        a2.setTime(LocalTime.of(16, 0));
        assertEquals(LocalTime.of(16, 0), a2.getTime());
    }

    @Test
    public void TestToString() {
        assertEquals("Patient: \n" +
                "Name: Gregor\n" +
                "Age: 55\n" +
                "Sex: M\n" +
                "Insurance: BC MSP\n" +
                "PHN: 1234567\n" +
                "Diseases: \n" +
                "  Date:2023-01-31 Time:12:00", a1.toString());
        assertEquals("Patient: \n" +
                "Name: Kemi\n" +
                "Age: 46\n" +
                "Sex: F\n" +
                "Insurance: N/A\n" +
                "PHN: 1234568\n" +
                "Diseases: \n" +
                "  Date:2023-01-15 Time:15:00", a2.toString());
    }

    @Test
    public void TestOverlap() {
        // neither same time nor same date
        assertFalse(a1.overlap(a2));
        a1.setTime(LocalTime.of(15,0));
        // same time but different dates
        assertFalse(a2.overlap(a1));
        a2.setDate(LocalDate.of(2023, 1 ,31));
        a1.setTime(LocalTime.of(12,0));
        // same date but different time
        assertFalse(a2.overlap(a1));
        // same time and date
        a2.setTime(LocalTime.of(12,0));
        assertTrue(a1.overlap(a2));
    }

    @Test
    public void TestToJson() {
        Patient patient = new Patient("Gregor", 55, "M", "BC MSP", 1234567);
        Appointment appointment = new Appointment(LocalDate.of(2023, 2, 26),
                LocalTime.of(14, 30), patient);
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("Time", "14:30");
        expectedJson.put("Date", "2023-02-26");
        expectedJson.put("Patient", patient.toJson());
        JSONObject actualJson = appointment.toJson();
        assertEquals(expectedJson.toString(), actualJson.toString());
    }
}
