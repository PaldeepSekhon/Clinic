package ruclinic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import util.CircularLinkedList;
import util.Date;
import util.Sort;
import util.TechnicianSchedule;
import util.Timeslot;


/**
 * The ClinicManager class is responsible for managing the clinic's operations,
 * processing commands, and handling appointments, providers, and billing.
 * 
 * Commands include:
 * - D (Office appointment)
 * - T (Imaging appointment)
 * - C (Cancel appointment)
 * - R (Reschedule appointment)
 * - PA (List providers)
 * - PO (List office appointments)
 * - PI (List imaging appointments)
 * - PC (Display credits)
 * - PS (Display billing statements)
 * - Q (Quit the program)
 * 
 * @author Your Name
 */
public class ClinicManager {
    private util.List<Appointment> appointments; // List to hold all appointments
    private util.List<Provider> providers; // Single list for all providers
    private CircularLinkedList technicianList;
    private HashSet<TechnicianSchedule> technicianBookings = new HashSet<>();

    // Constructor
    public ClinicManager() {
        this.appointments = new util.List<>(); // Custom List for appointments
        this.providers = new util.List<>(); // Single Custom List for all providers
        this.technicianList = new CircularLinkedList();

        loadProviders(); // Load providers from file on startup
        // technicianList = new List<>(); // Initialize the technician list
        // initializeTechnicians(); // Add technicians when the clinic manager is
        // created
    }

    /**
     * Method to load the providers from the file
     */
    private void loadProviders() {
        try {
            File file = new File("providers.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty())
                    continue; // Ignore empty lines

                String[] tokens = line.split("\\s+"); // Split by whitespace

                if (tokens[0].equalsIgnoreCase("D")) {
                    // Doctor
                    String firstName = tokens[1];
                    String lastName = tokens[2];
                    String dob = tokens[3]; // format: MM/DD/YYYY
                    Location location = Location.valueOf(tokens[4].toUpperCase()); // Using the enum
                    Specialty specialty = Specialty.valueOf(tokens[5].toUpperCase()); // Get specialty enum
                    String npi = tokens[6];

                    // Parse the date from the string
                    String[] dobParts = dob.split("/");
                    int month = Integer.parseInt(dobParts[0]);
                    int day = Integer.parseInt(dobParts[1]);
                    int year = Integer.parseInt(dobParts[2]);

                    Profile profile = new Profile(firstName, lastName, new Date(year, month, day));
                    // Create Doctor instance using the Specialty enum
                    Provider doctor = new Doctor(profile, location, specialty, npi);
                    providers.add(doctor); // Add to the list of providers

                } else if (tokens[0].equalsIgnoreCase("T")) {
                    // Technician
                    String firstName = tokens[1];
                    String lastName = tokens[2];
                    String dob = tokens[3]; // format: MM/DD/YYYY
                    Location location = Location.valueOf(tokens[4].toUpperCase()); // Using the enum
                    int ratePerVisit = Integer.parseInt(tokens[5]);

                    // Parse the date from the string
                    String[] dobParts = dob.split("/");
                    int month = Integer.parseInt(dobParts[0]);
                    int day = Integer.parseInt(dobParts[1]);
                    int year = Integer.parseInt(dobParts[2]);

                    Profile profile = new Profile(firstName, lastName, new Date(year, month, day));
                    Technician technician = new Technician(profile, location, ratePerVisit); // Create Technician
                                                                                             // instance
                    providers.add(technician); // Add to the list of providers
                    technicianList.addTechnician(technician);
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: providers.txt not found.");
        } catch (Exception e) {
            System.out.println("Error loading providers: " + e.getMessage());
        }
    }

    /**
     * Runs the clinic manager, continuously processing user commands until the "Q"
     * command is entered.
     * The method reads input commands from the user, determines the appropriate
     * action,
     * and invokes methods to handle scheduling, cancellation, and other operations.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Providers loaded to the list okok.");
        listProviders(); // Display providers at startup
        System.out.println("Rotation list for the technicians.");
        technicianList.printTechnicianList();
        System.out.printf("%nClinic Manager is running...%n%n%n");

        boolean running = true;
        while (running) {
            String command = scanner.nextLine().trim();
            if (command.isEmpty())
                continue;

            // Process the command
            switch (command.split(",")[0]) { // Use the original command without case conversion
                case "D": // Schedule office appointment
                    processOfficeAppointment(command);
                    break;
                case "T": // Schedule imaging appointment
                    processImagingAppointment(command);
                    break;
                case "C": // Cancel appointment
                    processCancelAppointment(command);
                    break;
                case "R": // Reschedule appointment
                    processRescheduleAppointment(command);
                    break;
                case "PA": // List providers by profile
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        listProviders(); // Providers should be displayed
                    }
                    break;
                case "PP": // List appointments sorted by patient
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        Sort.appointment(appointments, 'p'); // Sort by patient
                        System.out.println("Listing appointments sorted by patient...");
                        for (Appointment appointment : appointments) {
                            System.out.println(appointment);
                        }
                    }
                    break;
                case "PL": // List appointments sorted by county, then date and time
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        Sort.appointmentByCounty(appointments); // Sort by county
                        System.out.println("Listing appointments sorted by county...");
                        for (Appointment appointment : appointments) {
                            System.out.println(appointment);
                        }
                    }
                    break;
                case "PO": // List office appointments
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        listOfficeAppointments();
                    }
                    break;
                case "PI": // List imaging appointments
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        listImagingAppointments();
                    }
                    break;
                case "PC": // Display credit amounts
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        displayCredits();
                    }
                    break;
                case "PS": // Display billing statements
                    if (appointments.isEmpty()) {
                        System.out.println("Schedule calendar is empty.");
                    } else {
                        displayBillingStatements();
                    }
                    break;
                case "Q": // Quit the program
                    running = false;
                    System.out.println("Clinic Manager terminated.");
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
        scanner.close();
    }

    // Command Handlers

    private void processOfficeAppointment(String command) {

        try {
            String[] tokens = command.split(",");
            if (tokens.length < 7) {
                System.out.println("Missing data tokens.");
                return;
            }

            String dateStr = tokens[1];
            String timeslotStr = tokens[2];
            String firstName = tokens[3];
            String lastName = tokens[4];
            String dobStr = tokens[5];
            String npi = tokens[6];

            // Parse the appointment date
            String[] dateParts = dateStr.split("/");
            Date appointmentDate = new Date(
                    Integer.parseInt(dateParts[2]), // year
                    Integer.parseInt(dateParts[0]), // month
                    Integer.parseInt(dateParts[1]) // day
            );

            // Validate appointment date
            if (!appointmentDate.isValid()) {
                System.out.println("Appointment date: " + appointmentDate + " is not a valid calendar date.");
                return;
            }

            // Check if the appointment is today or before today
            Date today = new Date(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            if (appointmentDate.compareTo(today) <= 0) {
                System.out.println("Appointment date: " + appointmentDate + " is today or a date before today.");
                return;
            }

            // Check if the appointment is on a weekend
            if (appointmentDate.isWeekend()) {
                System.out.println("Appointment date: " + appointmentDate + " is Saturday or Sunday.");
                return;
            }

            // Check if the appointment is within six months
            if (!appointmentDate.isWithinSixMonths()) {
                System.out.println("Appointment date: " + appointmentDate + " is not within six months.");
                return;
            }

            // Validate timeslot
            Timeslot appointmentTimeslot = Timeslot.fromString(timeslotStr);
            if (appointmentTimeslot == null) {
                System.out.println(timeslotStr + " is not a valid time slot.");
                return;
            }

            // Parse and validate patient's date of birth
            String[] dobParts = dobStr.split("/");
            Date dobDate = new Date(
                    Integer.parseInt(dobParts[2]), // year
                    Integer.parseInt(dobParts[0]), // month
                    Integer.parseInt(dobParts[1]) // day
            );
            if (!dobDate.isValid()) {
                System.out.println("Patient dob: " + dobDate + " is not a valid calendar date.");
                return;
            }
            if (dobDate.compareTo(today) >= 0) {
                System.out.println("Patient dob: " + dobDate + " is today or a day after today.");
                return;
            }

            // Validate NPI and find provider
            Provider provider = findProviderByNPI(npi);
            if (provider == null) {
                System.out.println(npi + " - provider doesn't exist.");
                return;
            }

            // Create patient object
            Profile profile = new Profile(firstName, lastName, dobDate);
            Person patient = new Person(profile);

            // Check for existing appointment for the same patient at the same date and
            // timeslot
            for (Appointment appt : appointments) {
                if (appt.getPatient().getProfile().equals(profile) &&
                        appt.getDate().equals(appointmentDate) &&
                        appt.getTimeslot().equals(appointmentTimeslot)) {
                    System.out.println(firstName + " " + lastName + " " + dobStr
                            + " has an existing appointment at the same time slot.");
                    return;
                }
            }

            // Check if the provider is available at the specified timeslot
            if (!isProviderAvailable(provider, appointmentDate, appointmentTimeslot)) {
                Doctor doctor = (Doctor) provider;
                System.out.println(String.format("[%s %s %s, %s, %s %s][%s, #%s] is not available at slot %s.",
                        doctor.getProfile().getFirstName(),
                        doctor.getProfile().getLastName(),
                        doctor.getProfile().getDob(),
                        doctor.getLocation().getCity(),
                        doctor.getLocation().getCounty(),
                        doctor.getLocation().getZip(),
                        doctor.getSpecialty().getNameOnly(),
                        doctor.getNpi(),
                        timeslotStr));
                return;
            }

            // Create the new Appointment object
            Appointment newAppointment = new Appointment(appointmentDate, appointmentTimeslot, patient, provider);
            appointments.add(newAppointment);

            // Print the appointment details
            Doctor doctor = (Doctor) provider;
            System.out.printf("%s %s %s %s %s [%s %s %s, %s, %s %s][%s, #%s] booked.%n",
                    appointmentDate,
                    appointmentTimeslot,
                    firstName,
                    lastName,
                    dobStr,
                    doctor.getProfile().getFirstName(),
                    doctor.getProfile().getLastName(),
                    doctor.getProfile().getDob(),
                    doctor.getLocation().getCity(),
                    doctor.getLocation().getCounty(),
                    doctor.getLocation().getZip(),
                    doctor.getSpecialty().getNameOnly(),
                    doctor.getNpi());

        } catch (Exception e) {
            System.out.println("Error processing the office appointment: " + e.getMessage());
        }
    }

    private boolean isProviderAvailable(Provider provider, Date date, Timeslot timeslot) {
        for (Appointment appointment : appointments) {
            if (appointment.getProvider().equals(provider) &&
                    appointment.getDate().equals(date) &&
                    appointment.getTimeslot().equals(timeslot)) {
                return false; // Provider has an existing appointment at that date and timeslot
            }
        }
        return true; // Provider is available
    }

    private boolean isValidNPI(String npi) {
        // Check if the NPI is numeric
        if (!npi.matches("\\d+")) {
            return false;
        }

        // Check if the provider with the given NPI exists
        return findProviderByNPI(npi) != null;
    }

    // Here's the corrected processImagingAppointment method with proper date
    // comparison logic:
    private void processImagingAppointment(String command) {
        try {
            String[] tokens = command.split(",");
            if (tokens.length < 7) {
                System.out.println("Missing data tokens.");
                return;
            }

            String dateStr = tokens[1];
            String timeslotStr = tokens[2];
            String firstName = tokens[3];
            String lastName = tokens[4];
            String dobStr = tokens[5];
            String imagingService = tokens[6];

            // Parse the appointment date
            String[] dateParts = dateStr.split("/");
            Date appointmentDate = new Date(
                    Integer.parseInt(dateParts[2]), // year
                    Integer.parseInt(dateParts[0]), // month
                    Integer.parseInt(dateParts[1]) // day
            );

            // Validate appointment date
            if (!appointmentDate.isValid()) {
                System.out.println("Appointment date: " + appointmentDate + " is not a valid calendar date.");
                return;
            }

            // Check if the appointment is today or before today
            Date today = new Date(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH) + 1,
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            if (appointmentDate.compareTo(today) <= 0) {
                System.out.println("Appointment date: " + appointmentDate + " is today or a date before today.");
                return;
            }

            // Check if the appointment is on a weekend
            if (appointmentDate.isWeekend()) {
                System.out.println("Appointment date: " + appointmentDate + " is Saturday or Sunday.");
                return;
            }

            // Check if the appointment is within six months
            if (!appointmentDate.isWithinSixMonths()) {
                System.out.println("Appointment date: " + appointmentDate + " is not within six months.");
                return;
            }

            // Validate timeslot
            Timeslot timeslotObj = Timeslot.fromString(timeslotStr);
            if (timeslotObj == null) {
                System.out.println(timeslotStr + " is not a valid time slot.");
                return;
            }

            // Validate imaging service
            if (!isValidImagingService(imagingService)) {
                System.out.println(imagingService + " - imaging service not provided.");
                return;
            }

            // Parse and validate patient's DOB
            String[] dobParts = dobStr.split("/");
            Date dobDate = new Date(
                    Integer.parseInt(dobParts[2]), // year
                    Integer.parseInt(dobParts[0]), // month
                    Integer.parseInt(dobParts[1]) // day
            );

            String dobValidationResult = isValidDateOfBirth(dobDate);
            if (dobValidationResult != null) {
                System.out.println(dobValidationResult);
                return;
            }

            // Check for existing appointments at the same time
            Profile patientProfile = new Profile(firstName, lastName, dobDate);
            for (Appointment appt : appointments) {
                if (appt.getPatient().getProfile().equals(patientProfile) &&
                        appt.getDate().equals(appointmentDate) &&
                        appt.getTimeslot().equals(timeslotObj)) {
                    System.out.println(firstName + " " + lastName + " " + dobStr +
                            " has an existing appointment at the same time slot.");
                    return;
                }
            }

            // Assign technician using rotation
            Technician technician = assignTechnicianForService(imagingService, appointmentDate, timeslotObj);
            if (technician == null) {
                System.out.printf("Cannot find an available technician at all locations for %s at slot %s.%n",
                        imagingService.toUpperCase(),
                        timeslotObj.toString());
                return;
            }

            // Create and add the appointment
            Patient patient = new Patient(patientProfile);
            Radiology room = Radiology.valueOf(imagingService.toUpperCase());
            Imaging imagingAppointment = new Imaging(appointmentDate, timeslotObj, patient, technician, room);
            appointments.add(imagingAppointment);
            technicianList.addAvailableRoom(imagingService, technician.getLocation().getCity(), timeslotObj);

            // Print confirmation
            System.out.printf("%s %s %s %s %s [%s %s %s, %s, %s %s][rate: $%.2f][%s] booked.%n",
                    appointmentDate,
                    timeslotObj,
                    firstName,
                    lastName,
                    dobStr,
                    technician.getProfile().getFirstName(),
                    technician.getProfile().getLastName(),
                    technician.getProfile().getDob().toString(),
                    technician.getLocation().getCity(),
                    technician.getLocation().getCounty(),
                    technician.getLocation().getZip(),
                    (double) technician.rate(),
                    imagingService.toUpperCase());

        } catch (Exception e) {
            System.out.println("Error processing the imaging appointment: " + e.getMessage());
        }
    }

    private String isValidDateOfBirth(Date dob) {
        // Check if the date is valid
        if (!dob.isValid()) {
            return "Patient dob: " + dob.toString() + " is not a valid calendar date.";
        }

        // Get today's date
        Calendar today = Calendar.getInstance();
        Calendar dobCal = Calendar.getInstance();
        dobCal.set(dob.getYear(), dob.getMonth() - 1, dob.getDay()); // Month is 0-based in Calendar

        // Check if the date is today or in the future
        if (dobCal.compareTo(today) >= 0) {
            return "Patient dob: " + dob.toString() + " is today or a date after today.";
        }

        return null; // No issues found
    }

    private void processCancelAppointment(String command) {
        try {
            String[] tokens = command.split(",");
            if (tokens.length < 6) {
                System.out.println("Missing data tokens.");
                return;
            }

            String date = tokens[1];
            String timeslot = tokens[2];
            String firstName = tokens[3];
            String lastName = tokens[4];
            String dob = tokens[5];

            // Find the appointment and cancel it
            boolean found = false;
            for (Appointment appt : appointments) {
                if (appt.getPatient().getFirstName().equalsIgnoreCase(firstName)
                        && appt.getPatient().getLastName().equalsIgnoreCase(lastName)
                        && appt.getDate().equals(date)
                        && appt.getTimeslot().equals(timeslot)) {
                    appointments.remove(appt);
                    System.out.println(date + " " + timeslot + ":00 " + firstName + " " + lastName
                            + " - appointment has been canceled.");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println(
                        date + " " + timeslot + ":00 " + firstName + " " + lastName + " - appointment does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error processing the cancellation.");
        }
    }

    private void processRescheduleAppointment(String command) {
        try {
            String[] tokens = command.split(",");
            if (tokens.length < 7) {
                System.out.println("Missing data tokens.");
                return;
            }

            String date = tokens[1];
            String timeslot = tokens[2];
            String firstName = tokens[3];
            String lastName = tokens[4];
            String dob = tokens[5];
            String newTimeslot = tokens[6];

            // Find the appointment and reschedule it
            boolean found = false;
            for (Appointment appt : appointments) {
                if (appt.getPatient().getFirstName().equalsIgnoreCase(firstName)
                        && appt.getPatient().getLastName().equalsIgnoreCase(lastName)
                        && appt.getDate().equals(date)
                        && appt.getTimeslot().equals(timeslot)) {
                    appt.setTimeSlot(new Timeslot(Integer.parseInt(newTimeslot), 0)); // Reschedule
                    System.out.println(
                            "Rescheduled to " + date + " " + newTimeslot + ":00 " + firstName + " " + lastName);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println(date + " " + timeslot + ":00 " + firstName + " " + lastName + " does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error processing the rescheduling.");
        }
    }

    private void listProviders() {
        Sort.provider(providers); // Sort by provider profile

        for (Provider provider : providers) {
            String providerDetails = String.format("[%s %s %s, %s, %s %s]",
                    provider.getProfile().getFirstName(),
                    provider.getProfile().getLastName(),
                    provider.getProfile().getDob(),
                    provider.getLocation().getCity(),
                    provider.getLocation().getCounty(),
                    provider.getLocation().getZip());

            if (provider instanceof Doctor) {
                Doctor doctor = (Doctor) provider;
                // Display doctor's specialty and NPI, using the testing methods
                providerDetails += String.format("[%s, #%s]",
                        doctor.getSpecialty().getNameOnly(),
                        doctor.getNpi());
            } else if (provider instanceof Technician) {
                Technician technician = (Technician) provider;
                // Display technician's rate per visit formatted to two decimal places
                providerDetails += String.format("[rate: $%.2f]", (double) technician.rate());
            }

            System.out.println(providerDetails);

        }
        System.out.println();

    }

    private void displayTechnicianRotation() {
        System.out.println("Rotation list for the technicians.");

        if (technicianList.size() == 0) {
            System.out.println("No technicians available.");
            return;
        }

        // Get the first technician from the list
        Technician firstTechnician = technicianList.getFirstTechnician(); // Start with the first technician (head)
        Technician currentTechnician = firstTechnician;

        // Debug: Ensure we have the correct first technician
        System.out.println("First Technician: " + firstTechnician.getProfile().getFirstName() + " "
                + firstTechnician.getProfile().getLastName());

        // StringBuilder to accumulate the output
        StringBuilder rotationList = new StringBuilder();

        // Traverse the circular list
        do {
            // Append the technician details to the rotation list
            rotationList.append(String.format("%s (%s)",
                    currentTechnician.getProfile().getFirstName() + " " + currentTechnician.getProfile().getLastName(),
                    currentTechnician.getLocation().getCity()));
            rotationList.append(" --> ");

            // Debug: Print each technician as we visit them
            System.out.println("Visiting: " + currentTechnician.getProfile().getFirstName() + " "
                    + currentTechnician.getProfile().getLastName());

            // Get the next technician in the rotation
            currentTechnician = technicianList.getNextTechnician();

        } while (currentTechnician != firstTechnician); // Continue until we loop back to the first technician

        // Remove the last arrow
        if (rotationList.length() > 0) {
            rotationList.setLength(rotationList.length() - 5); // Remove last " --> "
        }

        // Print the final rotation list
        System.out.println(rotationList.toString());
        System.out.println();
    }

    private void listOfficeAppointments() {
        System.out.println();
        System.out.println("** List of office appointments ordered by county/date/time.");

        // Sort appointments by county, date, and timeslot
        Sort.appointmentByCounty(appointments);

        // Display sorted appointments
        for (Appointment appointment : appointments) {
            if (!(appointment instanceof Imaging)) { // Only list office appointments
                System.out.println(appointment);
            }
        }
        System.out.println("** end of list");

    }

    private void listImagingAppointments() {
        System.out.println(); // This creates an empty line
        System.out.println("** List of radiology appointments ordered by county/date/time.");

        Sort.appointmentByCounty(appointments); // Sort by county, then date and time

        for (Appointment appointment : appointments) {
            if (appointment instanceof Imaging) { // Only display imaging appointments
                System.out.println(appointment);
            }
        }
        System.out.println("** end of list");
    }

    private void displayCredits() {
        System.out.println("** Credit amount ordered by provider. **");

        // Sort providers by profile
        Sort.provider(providers);

        for (int i = 0; i < providers.size(); i++) {
            Provider provider = providers.get(i);
            String creditDetails;

            // Determine if the provider is a Doctor or Technician
            if (provider instanceof Doctor) {
                creditDetails = String.format("%s [%s] [credit amount: $%.2f]",
                        provider.getProfile().getFirstName() + " " + provider.getProfile().getLastName(),
                        provider.getProfile().getDob(),
                        (double) provider.rate()); // Cast to double for formatting
            } else if (provider instanceof Technician) {
                creditDetails = String.format("%s [%s] [credit amount: $%.2f]",
                        provider.getProfile().getFirstName() + " " + provider.getProfile().getLastName(),
                        provider.getProfile().getDob(),
                        (double) provider.rate()); // Cast to double for formatting
            } else {
                continue; // Skip any other type of provider not handled
            }

            // Print the formatted output with the index
            System.out.printf("(%d) %s%n", (i + 1), creditDetails);
        }

        System.out.println("** end of list **");
    }

    private void displayBillingStatements() {
            System.out.println("** Billing Statements for all Patients **");

    // HashMap to store total billing amounts for each patient
    HashMap<String, Double> patientBills = new HashMap<>();

    // Iterate over all appointments (assuming they are all completed)
    for (Appointment appointment : appointments) {
        Person patient = appointment.getPatient();
        String patientName = patient.getProfile().getFirstName() + " " + patient.getProfile().getLastName();

        // Determine the charge based on the provider (doctor or technician)
        Provider provider = appointment.getProvider();
        double charge = 0;

        if (provider instanceof Doctor) {
            Doctor doctor = (Doctor) provider;
            charge = doctor.rate(); // Charge based on the doctor's specialty rate
        } else if (provider instanceof Technician) {
            Technician technician = (Technician) provider;
            charge = technician.getRatePerVisit(); // Charge based on the technician's rate
        }

        // Add the charge to the patient's total bill
        patientBills.put(patientName, patientBills.getOrDefault(patientName, 0.0) + charge);
    }

    // Display billing statements
    for (Map.Entry<String, Double> entry : patientBills.entrySet()) {
        String patientName = entry.getKey();
        double totalBill = entry.getValue();
        System.out.printf("%s: Total Bill = $%.2f%n", patientName, totalBill);
    }

    // Clear all appointments as they are now billed
    //appointments = null;

    System.out.println("** End of Billing Statements **");


    }

    // Helper Methods

    private boolean isValidDate(String date) {
        // Implement date validation logic (not today, not before today, valid format,
        // etc.)
        return true; // Placeholder
    }

    private boolean isValidTimeslot(String timeslot) {
        try {
            int ts = Integer.parseInt(timeslot);
            return ts >= 1 && ts <= 12; // Valid timeslots are 1-12
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidImagingService(String service) {
        return service.equalsIgnoreCase("xray") || service.equalsIgnoreCase("catscan")
                || service.equalsIgnoreCase("ultrasound");
    }

    private Provider findProviderByNPI(String npi) {
        for (Provider provider : providers) {
            if (provider instanceof Doctor) {
                Doctor doctor = (Doctor) provider;
                if (doctor.getNpi().equals(npi)) {
                    return doctor;
                }
            }
        }
        return null;
    }

    // The problem is in the loop termination logic. Here's the fixed version with
    // comments explaining the issues:
    private Technician lastAssignedTechnician = null; 

    private Technician assignTechnicianForService(String imagingService, Date appointmentDate, Timeslot timeslotObj) {
         if (technicianList.isEmpty()) {
        return null;
    }

    System.out.println("Starting technician rotation for " + imagingService + " at " + timeslotObj);

    Technician startingTech;
    if (lastAssignedTechnician == null) {
        startingTech = technicianList.getFirstTechnician();
    } else {
        startingTech = technicianList.getNextTechnician(); // Start from the technician after the last assigned one
    }
    Technician currentTech = startingTech;

    do {
        System.out.println("Checking technician: " + currentTech.getProfile().getFirstName() +
                           " " + currentTech.getProfile().getLastName());

        if (isTechnicianAvailable(currentTech, appointmentDate, timeslotObj)) {
            String location = currentTech.getLocation().getCity();
            if (technicianList.isRoomAvailable(imagingService, location, timeslotObj)) {
                System.out.println("Assigned technician: " + currentTech.getProfile().getFirstName() +
                                   " " + currentTech.getProfile().getLastName() + " for " + imagingService);
                bookTechnician(currentTech, appointmentDate, timeslotObj);
                lastAssignedTechnician = currentTech; // Update the last assigned technician
                return currentTech;
            }
        } else {
            System.out.println("Technician " + currentTech.getProfile().getFirstName() +
                               " " + currentTech.getProfile().getLastName() + " is not available at " + timeslotObj);
        }

        currentTech = technicianList.getNextTechnician();
    } while (currentTech != startingTech);

    System.out.println("Cannot find an available technician at all locations for " + imagingService.toUpperCase() + " at slot " + timeslotObj);
    return null;
    }    

    private boolean isTechnicianAvailable(Technician technician, Date appointmentDate, Timeslot timeslot) {
        TechnicianSchedule technicianSchedule = new TechnicianSchedule(technician.getProfile().getFirstName(), appointmentDate, timeslot);

        if (technicianBookings.contains(technicianSchedule)) {
            System.out.println("Technician " + technician.getProfile().getFirstName() + " "
                    + technician.getProfile().getLastName() + " is already booked at " + timeslot + " on " + appointmentDate);
            return false;
        }
    
        return true;
    }
    
    public void bookTechnician(Technician technician, Date appointmentDate, Timeslot timeslot) {
        TechnicianSchedule technicianSchedule = new TechnicianSchedule(technician.getProfile().getFirstName(), appointmentDate, timeslot);
        technicianBookings.add(technicianSchedule);
        
    }

    

    // Method to initialize and add technicians
    /*
     * private void initializeTechnicians() {
     * // Create radiology services for each technician
     * Date dobJenny = new Date(1991, 8, 9);
     * Date dobFrank = new Date(1999, 6, 24);
     * Date dobBen = new Date(1987, 9, 28);
     * 
     * 
     * List<Radiology> jennyServices = new List<>();
     * jennyServices.add(Radiology.XRAY);
     * jennyServices.add(Radiology.ULTRASOUND);
     * 
     * List<Radiology> frankServices = new List<>();
     * frankServices.add(Radiology.XRAY);
     * frankServices.add(Radiology.CATSCAN);
     * 
     * List<Radiology> benServices = new List<>();
     * benServices.add(Radiology.ULTRASOUND);
     * 
     * // Create Technician objects
     * Technician jennyPatel = new Technician(new Profile("Jenny",
     * "Patel",dobJenny),
     * Location.BRIDGEWATER, 125, jennyServices);
     * 
     * Technician frankLin = new Technician(new Profile("Frank", "Lin",dobFrank),
     * Location.PISCATAWAY, 120, frankServices);
     * 
     * Technician benJerry = new Technician(new Profile("Ben", "Jerry", dobBen),
     * Location.PISCATAWAY, 150, benServices);
     * 
     * // Add the technicians to the technician list
     * technicianList.add(jennyPatel);
     * technicianList.add(frankLin);
     * technicianList.add(benJerry);
     * }
     */
}
