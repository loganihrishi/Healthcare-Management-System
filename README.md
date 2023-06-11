# Healthcare Management System 

## About the Application 


**What is the purpose of the application?** 

- The application will keep track of patient information and show the schedule of appointments for the healthcare facility.
- The healthcare staff members have the capability to make changes to appointments, such as canceling or rescheduling 
 them, through the use of the application. 
- This system is particularly beneficial for healthcare institutions such as hospitals or 
  clinics that have a high patient volume. 

**Who will use it?**
- This application can be utilized by various healthcare providers, 
  such as small clinics or any other firms that require appointment-based scheduling.


## User Stories

**As a user, I will have the capability to:**

- Add patient details
- Schedule a new appointment and add it to the existing list
- Change the date and time of an existing appointment to the another day
- Remove an appointment that has been scheduled
- Search the appointment details using the patient's Personal Health Number (PHN)
- Display all the appointments for a given date
- Save the entire state of Patients and Appointments to the file 
- Load the saved state of Patients and Appointments from the last time it was run 
  and resume where it was left off

## Sample Logging of Events
~~~
  Sun Apr 09 18:42:57 PDT 2023 
  Retrieved Appointment Details For PHN: 595961
  Sun Apr 09 18:43:41 PDT 2023 
  Failed to retrieve appointment details for PHN: 696969
  Sun Apr 09 18:43:41 PDT 2023 
  Appointment Added for PHN: 696969
  Sun Apr 09 18:43:52 PDT 2023 
  Retrieved Appointment Details For PHN: 696969
  Sun Apr 09 18:44:02 PDT 2023 
  Retrieved Appointment Details For PHN: 696969
  Sun Apr 09 18:44:02 PDT 2023 
  Appointment Cancelled for PHN: 696969
  Sun Apr 09 18:44:12 PDT 2023 
  Failed to retrieve appointment details for PHN: 696969
~~~

## UML Class Diagram 
<img height="475" src="UML_Design_Diagram.png" width="800"/>
