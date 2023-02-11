package model;

import java.time.LocalDate;

public class Disease {
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
}

