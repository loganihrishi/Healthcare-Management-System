package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
/**

 Represents a disease with a name and a diagnosed date.
 The class provides methods for getting the name and diagnosed date of the disease, as well as a toString method
 for returning the disease in a formatted string format.
 */


public class Disease implements Writable {
    private String name;
    private LocalDate diagnosedDate;

    // REQUIRES: a valid disease name and a valid date
    // EFFECTS: constructs a new disease object using the given information
    public Disease(String name, LocalDate date) {
        this.name = name;
        this.diagnosedDate = date;
    }

    // EFFECTS: returns the name of the disease
    public String getName() {
        return name;
    }


    // EFFECTS: returns the date at which the disease was diagnosed
    public LocalDate getDiagnosedDate() {
        return diagnosedDate;
    }

    // EFFECTS: returns the disease in the proper String format
    public String toString() {
        String diagnosis = "Disease: " + name + "\n"
                +
                "Diagnosis Date: " + diagnosedDate;
        return diagnosis;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Diagnosis Date", diagnosedDate);
        return json;
    }


}

