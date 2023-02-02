package ui;
import model.Disease;
import model.Patient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class StartWork {
    int option = 0;
    public StartWork() throws ParseException {
        displayMenu();
    }

    Scanner sc = new Scanner(System.in);
    public void displayMenu() throws ParseException {
        System.out.println("1. To create a patient: ");
        System.out.println("2. Enter 2 to add an appointment: ");
        System.out.println("3. Enter 3 to display all scheduled appointments: ");
        System.out.println("Enter 4 to Quit the application. ");
        option  = sc.nextInt();
        sc.nextLine();
        if (option == 1) {
            addPatient();
        }
        else
            System.out.println("Fuck off");
    }

    public void addPatient() throws ParseException {
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Patient's Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient's Sex (M/F)");
        char sex = sc.next().charAt(0);
        System.out.print("Enter Patient's Insurance Details: ");
        String insurance = sc.next();
        System.out.print("Enter Patient's Personal Health Number: ");
        int PHN = sc.nextInt();
        Patient patient = new Patient(name, age, sex, insurance, PHN);
        List<Disease> diseases = addDisease();
        for (Disease d: diseases) {
            patient.addDisease(d.getName(), d.getDiagnosedDate());
        }
    }

    public List<Disease> addDisease() throws ParseException {
        List<Disease> diseases = new ArrayList<>();
        System.out.print("Enter the number of diseases: ");
        int num = sc.nextInt();
        for (int i=0; i<num; i++) {
            System.out.print("Enter the name of disease no. " + (i+1) + " :");
            String name = sc.next();
            System.out.print("Enter the date of the date (YYYY/MM/DD) when the disease was diagnosed: ");
            String date = sc.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate d = LocalDate.parse(date, formatter);
            diseases.add(new Disease(name ,d));
            System.out.println("Disease successfully added!");
        }
        System.out.println("All the diseases have been stored");
        return diseases;
    }
}
