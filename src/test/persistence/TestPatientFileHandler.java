package persistence;

import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test class for PatientFileHandler class
 */

public class TestPatientFileHandler {
    
    @Test
    public void TestReadingNonExistentFile() {
        PatientFileHandler patientFileHandler = new PatientFileHandler("./data/noSuchFile.json");
        try {
            List<Patient> result = patientFileHandler.readPatientsFromFile();
            fail("Oops!");
        } catch (IOException e) {
            // we are expecting this exception
        }
    }

    @Test
    public void TestGetFilePath() {
        PatientFileHandler patientFileHandler = new PatientFileHandler("./data/noSuchFile.json");
        assertEquals("./data/noSuchFile.json", patientFileHandler.getFilePath());
    }

    @Test
    public void TestReadPatientsFromFileEmpty() throws IOException {
        PatientFileHandler patientFileHandler = new PatientFileHandler("./data/test2.json");
        List<Patient> result = patientFileHandler.readPatientsFromFile();
        assertEquals(0, result.size());
    }

    @Test
    public void TestPatientsFromFileMultiple() {
        PatientFileHandler patientFileHandler = new PatientFileHandler("./data/patients.json");
        List<Patient> result = null;
        try {
            result = patientFileHandler.readPatientsFromFile();
        } catch (IOException e) {
            fail("Not expecting an IOException");
        }
        assertEquals(3, result.size());
    }

    @Test
    public void writePatientsToFileOnePatient() {
        PatientFileHandler patientFileHandler = new PatientFileHandler("./data/test.json");
        List<Patient> toBeInserted = new ArrayList<>();
        Patient p1 = new Patient("Hrishi Logani", 18, "M", "private", 123456);
        toBeInserted.add(p1);
        assertEquals(1, toBeInserted.size());
        try {
            patientFileHandler.writePatientsToFile(toBeInserted);
        } catch (IOException e) {
            fail("Did not expect an IO Exception");
        }
        List<Patient> result1 = null;
        try {
            result1 = patientFileHandler.readPatientsFromFile();
        } catch (IOException e) {
            fail("Did not expect an IO Exception while reading the file");
        }
        assertEquals(1, result1.size());
    }
}
