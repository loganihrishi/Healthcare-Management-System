package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * THIS IS A TEST CLASS FOR APPOINTMENTLIST
 */

public class TestAppointmentList {

    private LocalDate date1, date2;
    private LocalTime time1, time2;
    private Patient p1, p2;
    private Appointment a1, a2;
    private AppointmentList LOA1, LOA2, LOA3, appointmentList;

    @BeforeEach
    public void setUp() {
        date1 = LocalDate.of(2023, 01, 27);
        date2 = LocalDate.of(2023, 01,26);
        time1 = LocalTime.of(12, 00);
        time2 = LocalTime.of(12, 15);
        p1 = new Patient("Gregor", 69, "M", "lung cancer",
                1234567);
        p2 = new Patient("Kemi", 55, "F", "Breast Cancer",
                1234568);
        a1 = new Appointment(date1, time1, p1);
        a2 = new Appointment(date2, time2, p2);
        LOA1 = new AppointmentList();
        LOA2 = new AppointmentList();
        LOA3 = new AppointmentList();
        LOA2.addAppointment(a1);
        LOA2.addAppointment(a2);
        appointmentList = new AppointmentList();
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
        Patient p = new Patient("John Smith", 55, "M", "Flu",  9876543);
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
        Patient p = new Patient("John Smith", 55, "M", "Flu",  9876543);
        Appointment a = new Appointment(LocalDate.of(2022, 1, 1),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(a); // Test rescheduling to an unavailable time
        boolean result = appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(18, 0));
        assertFalse(result);
        assertEquals(LocalDate.of(2022, 1, 1), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());
    }

    @Test
    public void testRescheduleAppointment_InvalidTime() {
        AppointmentList appointmentList = new AppointmentList();
        Patient p = new Patient("John Smith", 55, "M", "Flu",  9876543);
        Appointment a = new Appointment(LocalDate.of(2022, 1, 1),
                LocalTime.of(10, 0), p);
        appointmentList.addAppointment(a); // Test rescheduling to an invalid time
        boolean result = appointmentList.rescheduleAppointment(a, LocalDate.of(2022, 1, 2),
                LocalTime.of(7, 0));
        assertFalse(result);
        assertEquals(LocalDate.of(2022, 1, 1), a.getDate());
        assertEquals(LocalTime.of(10, 0), a.getTime());
    }

    @Test
    public void testRescheduleAppointment_TimeAndDateTaken() {
        AppointmentList appointmentList = new AppointmentList();
        // adding two appointments
        appointmentList.addAppointment(a1);
        appointmentList.addAppointment(a2);
        // trying to reschedule appointment
        boolean success = appointmentList.rescheduleAppointment(a1, a2.getDate(), a2.getTime());
        assertFalse(success);
        // it will fail because a1 and a2's dates and times are clashing
    }

    @Test
    public void  testRescheduleAppointment_SameDateDifferentTime() {
        // test to check when time is taken but date is not

        AppointmentList appointmentList = new AppointmentList();
        // adding two appointments
        appointmentList.addAppointment(a1);
        appointmentList.addAppointment(a2);
        // trying to reschedule the appointment
        boolean success = appointmentList.rescheduleAppointment(a1, a2.getDate(), LocalTime.of(12,30));
        assertTrue(success);
    }

    @Test
    public void testRescheduleAppointment_SameTimeDifferentDate() {

        AppointmentList appointmentList = new AppointmentList();
        // adding two appointments
        appointmentList.addAppointment(a1);
        appointmentList.addAppointment(a2);
        // trying to reschedule the appointment
        boolean success = appointmentList.rescheduleAppointment(a1,
                LocalDate.of(2023, 02,25), a2.getTime());
        assertTrue(success);
    }

    @Test
    public void testDisplayGivenDate_NoAppointmentFound() {
        assertEquals("No appointment found for the given date", LOA3.displayGivenDate(date1));
    }

    @Test
    public void testDisplayGivenDate_OneAppointmentFound() {
        assertEquals("Patient: \n" +
                "Name: Gregor\n" +
                "Age: 69\n" +
                "Sex: M\n" +
                "Insurance: lung cancer\n" +
                "PHN: 1234567\n" +
                "Diseases: \n" +
                "  Date:2023-01-27 Time:12:00", LOA2.displayGivenDate(date1));
    }

    @Test
    public void testDisplayGivenDate_TwoAppointmentsFound() {
        Patient p3 = new Patient("Hrishi", 18, "M", "private", 5961);
        Appointment a3 = new Appointment(LocalDate.of(2023,01,27), LocalTime.of(12,30), p3);
        LOA2.addAppointment(a3);
        assertEquals("Patient: \n" +
                "Name: Gregor\n" +
                "Age: 69\n" +
                "Sex: M\n" +
                "Insurance: lung cancer\n" +
                "PHN: 1234567\n" +
                "Diseases: \n" +
                "  Date:2023-01-27 Time:12:00Patient: \n" +
                "Name: Hrishi\n" +
                "Age: 18\n" +
                "Sex: M\n" +
                "Insurance: private\n" +
                "PHN: 5961\n" +
                "Diseases: \n" +
                "  Date:2023-01-27 Time:12:30", LOA2.displayGivenDate(LocalDate.of(2023,01,27)));
    }

    @Test
    void testAddAll() {
        // Test adding an empty list
        List<Appointment> emptyList = new ArrayList<>();
        AppointmentList appointmentList = new AppointmentList();
        appointmentList.addAll(emptyList);
        assertTrue(appointmentList.getAppointments().isEmpty());

        // Test adding a single appointment
        Patient patient = new Patient("John Smith", 18, "M", "private" ,595959);
        LocalDate date = LocalDate.of(2022, 3, 1);
        LocalTime time = LocalTime.of(10, 0);
        Appointment appointment1 = new Appointment(date, time, patient);
        List<Appointment> singleList = new ArrayList<>();
        singleList.add(appointment1);
        appointmentList.addAll(singleList);
        assertEquals(1, appointmentList.getAppointments().size());
        assertTrue(appointmentList.getAppointments().contains(appointment1));

        // Test adding multiple appointments, some of which are duplicates
        Patient patient2 = new Patient("Jane Doe", 19, "M", "government", 595960);
        LocalDate date2 = LocalDate.of(2022, 3, 2);
        LocalTime time2 = LocalTime.of(11, 0);
        Appointment appointment2 = new Appointment(date2, time2, patient2);
        LocalDate date3 = LocalDate.of(2022, 3, 3);
        LocalTime time3 = LocalTime.of(12, 0);
        Appointment appointment3 = new Appointment(date3, time3, patient);
        List<Appointment> multipleList = new ArrayList<>();
        multipleList.add(appointment1);
        multipleList.add(appointment2);
        multipleList.add(appointment3);
        appointmentList.addAll(multipleList);
        assertEquals(3, appointmentList.getAppointments().size());
        assertTrue(appointmentList.getAppointments().contains(appointment1));
        assertTrue(appointmentList.getAppointments().contains(appointment2));
        assertTrue(appointmentList.getAppointments().contains(appointment3));
    }

    @Test
    public void testToJson() {
        AppointmentList test = new AppointmentList();
        test.addAppointment(a1);
        JSONObject actualJson = test.toJson();
        String result = "{\"Appointments\":[{\"Patient\":" +
                "{\"Name: \":\"Gregor\",\"Insurance\":\"lung cancer\",\"Sex\":\"" +
                "M\",\"PHN\":1234567,\"Age\":69,\"Diseases\":[]},\"Time\":\"12:00\",\"Date\":\"2023-01-27\"}]}";
        assertEquals(result, actualJson.toString());
    }

    @Test
    public void testToJsonEmptyAppointmentList() {
        // Generate JSON object from empty appointment list
        JSONObject json = appointmentList.toJson();

        // Verify JSON object contains expected values
        assertTrue(json.has("Appointments"));
        JSONArray appointmentsJsonArray = json.getJSONArray("Appointments");
        assertEquals(0, appointmentsJsonArray.length());
    }
}
