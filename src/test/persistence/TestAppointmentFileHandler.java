package persistence;

import model.Appointment;
import model.Disease;
import model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * THIS IS A TEST CLASS FOR AppointmentFileHandler class
 */

public class TestAppointmentFileHandler {
    private Patient p1, p2;
    private Disease d1, d2;
    private Appointment a1, a2;
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
    public void TestReadingNonExistentFile_ExpectException() {
        AppointmentFileHandler appointmentFileHandler = new AppointmentFileHandler("./data/noSuchFile.json");
        List<Appointment> result = appointmentFileHandler.readAppointmentsFromFile();
    }

    @Test
    public void TestReadingAFile() {
        AppointmentFileHandler appointmentFileHandler = new AppointmentFileHandler("./data/appointments.json");
        List<Appointment> result = appointmentFileHandler.readAppointmentsFromFile();
        assertEquals(2, result.size());
    }

    @Test
    public void TestWritingNonExistentFile_ExpectException() {
        // testing an empty path
        AppointmentFileHandler appointmentFileHandler = new AppointmentFileHandler("");
        try {
            appointmentFileHandler.writeAppointmentsToFile(new ArrayList<>());
            fail("The test is passing");
        } catch (IOException e) {
            // pass, we are expecting the exception
        }
    }

    @Test
    public void TestWriteAppointmentsToFile() {
       try {
           AppointmentFileHandler appointmentFileHandler =
                   new AppointmentFileHandler("./data/AppointmentsTest.json");
           List<Appointment> toBeInserted = new ArrayList<>();
           toBeInserted.addAll(Arrays.asList(a1, a2));
           assertEquals(2, toBeInserted.size());
           appointmentFileHandler.writeAppointmentsToFile(toBeInserted);
           // reading the data from the file
           List<Appointment> result = appointmentFileHandler.readAppointmentsFromFile();
           // should be two items in the list
           assertEquals(2, result.size());
       } catch (IOException e) {
           fail("Did not expect the IO Exception");
       }
    }
}
