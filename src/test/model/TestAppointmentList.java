package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestAppointmentList {

    LocalDate date1, date2;
    LocalTime time1, time2;
    Patient p1, p2;
    Appointment a1, a2;
    private AppointmentList LOA1, LOA2, LOA3;

    @BeforeEach
    public void setUp() {
        date1 = LocalDate.of(2023, 01, 27);
        date2 = LocalDate.of(2023, 01,26);
        time1 = LocalTime.of(12, 00);
        time2 = LocalTime.of(12, 15);
        p1 = new Patient("Gregor", 69, 'M', "lung cancer",
                1234567);
        p2 = new Patient("Kemi", 55, 'F', "Breast Cancer",
                1234568);
        a1 = new Appointment(date1, time1, p1);
        a2 = new Appointment(date2, time2, p2);
        LOA1 = new AppointmentList();
        LOA2 = new AppointmentList();
        LOA3 = new AppointmentList();
        LOA2.addAppointment(a1);
        LOA2.addAppointment(a2);
    }

    @Test
    public void testGetAppointments() {
        ArrayList<Appointment> loa = new ArrayList<>();
        assertEquals(loa, LOA1.getAppointments());
        loa.add(a1);
        loa.add(a2);
        assertEquals(loa, LOA2.getAppointments());
    }

    @Test
    public void testAddAppointment() {
        assertEquals(0, LOA1.getAppointments().size());
        LOA1.addAppointment(a1);
        assertEquals(1, LOA1.getAppointments().size());
        LOA1.addAppointment(a2);
        assertEquals(2, LOA1.getAppointments().size());
    }

    @Test
    public void testRemoveAppointment() {
        assertEquals(0, LOA1.getAppointments().size());
        assertEquals(2, LOA2.getAppointments().size());
        LOA2.removeAppointment(a1);
        assertEquals(1, LOA2.getAppointments().size());
        LOA2.removeAppointment(a2);
        assertEquals(0, LOA2.getAppointments().size());
    }

    @Test
    public void testAppointmentWithPHN() {
        assertEquals("No appointment found with given PHN", LOA1.findAppointmentWithPHN(1234567));
        LOA1.addAppointment(a1);
        assertEquals(a1.toString(), LOA1.findAppointmentWithPHN(1234567));
        LOA1.addAppointment(a2);
        assertEquals("No appointment found with given PHN", LOA1.findAppointmentWithPHN(2345678));
        assertEquals(a2.toString(), LOA1.findAppointmentWithPHN(1234568));
    }

    @Test
    public void testRescheduleAppointment() {
        AppointmentList appointmentList = new AppointmentList();
        Patient p = new Patient("John Smith", 55, 'M', "Flu",  9876543);
        Appointment a = new Appointment(LocalDate.of(2022, 1, 1),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(a);

        // Test successful rescheduling
        appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(10, 0));
        assertEquals(LocalDate.of(2022, 1, 2), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());

        // Test rescheduling to an unavailable time
        appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(18, 0));
        assertEquals(LocalDate.of(2022, 1, 2), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());

        // Test rescheduling to an unavailable date and time
        Appointment b = new Appointment(LocalDate.of(2022, 1, 2),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(b);
        appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(10, 0));
        assertEquals(LocalDate.of(2022, 1, 2), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());

        // Test rescheduling a non-existent appointment
        Appointment c = new Appointment(LocalDate.of(2022, 1, 3),
                LocalTime.of(10, 0), p);
        appointmentList.rescheduleAppointment(c, LocalDate.of(2022, 1, 4),
                LocalTime.of(10, 0));
        assertEquals(LocalDate.of(2022, 1, 3), c.getDate());
        assertEquals(LocalTime.of(10,0), c.getTime());

    }
}