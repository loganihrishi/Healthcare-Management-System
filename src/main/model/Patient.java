package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String name;
    private int age;
    private List<Disease> diseases;
    private char sex;
    private String insurance;
    public int PHN;

    public Patient(String name, int age, char sex, String insurance, int PHN){
        this.name=name;
        this.age=age;
        this.sex = sex;
        this.insurance = insurance;
        this.PHN = PHN;
        this.diseases = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getInsurance() {
        return insurance;
    }

    public char getSex() {
        return sex;
    }

    public int getPHN() {
        return PHN;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void addDisease(String diseaseName, LocalDate diagnosisDate) {
        diseases.add(new Disease(diseaseName, diagnosisDate));
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public String DisplayDiseases() {
        String result = "";
        for (Disease d: diseases) {
            result += d.getName() + " " + d.getDiagnosedDate() + "\n";
        }
        return result;
    }

    public String toString() {
        return "Patient: " + "\n" +
                "Name: " + name + "\n" +
                "Age: " + age + "\n" +
                "Sex: " + sex + "\n" +
                "Insurance: " + insurance + "\n" +
                "PHN: " + PHN + "\n" +
                "Diseases: " + "\n" +
                DisplayDiseases();
    }
}
