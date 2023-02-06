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
    public void TestResetAppointment() {
        assertEquals(0, LOA1.getAppointments().size());
        LOA1.addAppointment(a1);
        assertEquals(1, LOA1.getAppointments().size());
        LOA1.resetAppointment();
        assertEquals(0, LOA1.getAppointments().size());
        LOA1.addAppointment(a1);
        LOA1.addAppointment(a2);
        assertEquals(2, LOA1.getAppointments().size());
        LOA1.resetAppointment();
        assertEquals(0, LOA1.getAppointments().size());
    }

    @Test
    public void testAppointmentWithPHNToString() {
        assertEquals("No appointment found with given PHN", LOA1.findAppointmentToString(1234567));
        LOA1.addAppointment(a1);
        assertEquals(a1.toString(), LOA1.findAppointmentToString(1234567));
        LOA1.addAppointment(a2);
        assertEquals("No appointment found with given PHN", LOA1.findAppointmentToString(2345678));
        assertEquals(a2.toString(), LOA1.findAppointmentToString(1234568));
    }

    @Test public void testFindAppointmentWithPHN() {
        assertEquals(null, LOA1.findAppointment(1234567));
        LOA1.addAppointment(a1);
        assertNotNull(LOA1.findAppointment(1234567));
        LOA1.addAppointment(a2);
        assertNull(LOA1.findAppointment(2345678));
        assertNotNull(LOA1.findAppointment(1234568));
    }

    @Test
    public void testRescheduleAppointment_SuccessfulRescheduling() {
        AppointmentList appointmentList = new AppointmentList();
        Patient p = new Patient("John Smith", 55, 'M', "Flu",  9876543);
        Appointment a = new Appointment(LocalDate.of(2022, 1, 1),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(a); // Test successful rescheduling

        boolean success = appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(10, 0));
        assertTrue(success);

        Appointment rescheduledAppointment = appointmentList.findAppointment(9876543);
        assertEquals(LocalDate.of(2022, 1, 2), rescheduledAppointment.getDate());
        assertEquals(LocalTime.of(10, 0), rescheduledAppointment.getTime());
    }

    @Test
    public void testRescheduleAppointment_UnavailableTime() {
        AppointmentList appointmentList = new AppointmentList();
        Patient p = new Patient("John Smith", 55, 'M', "Flu",  9876543);
        Appointment a = new Appointment(LocalDate.of(2022, 1, 1),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(a); // Test rescheduling to an unavailable time
        appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(18, 0));
        assertEquals(LocalDate.of(2022, 1, 1), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());
    }

    @Test
    public void testRescheduleAppointment_InvalidTime() {
        AppointmentList appointmentList = new AppointmentList();
        Patient p = new Patient("John Smith", 55, 'M', "Flu",  9876543);
        Appointment a = new Appointment(LocalDate.of(2022, 1, 1),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(a); // Test rescheduling to an invalid time
        appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(7, 0));
        assertEquals(LocalDate.of(2022, 1, 1), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());
    }
}
