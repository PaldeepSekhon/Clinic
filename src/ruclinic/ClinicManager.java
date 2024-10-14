package ruclinic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;
import util.Timeslot;
import util.Date;
import util.Sort;

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

    // Constructor
    public ClinicManager() {
        this.appointments = new util.List<>(); // Custom List for appointments
        this.providers = new util.List<>(); // Single Custom List for all providers

        loadProviders(); // Load providers from file on startup
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
                    Provider technician = new Technician(profile, location, ratePerVisit); // Create Technician instance
                    providers.add(technician); // Add to the list of providers
                }
            }

            scanner.close();
            System.out.println("Providers loaded to the list.");

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
        listProviders(); // Display providers at startup
        displayTechnicianRotation();
        System.out.println("Clinic Manager is running...");
        System.out.println();
        System.out.println();

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

    private void processImagingAppointment(String command) {
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
            String imagingService = tokens[6];

            // Parse the appointment date into a Date object
            String[] dateParts = date.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            Date appointmentDate = new Date(year, month, day); // Create Date object for appointment date

            // Validate appointment date using the isValid method
            if (!appointmentDate.isValid()) {
                System.out
                        .println("Appointment date: " + appointmentDate.toString() + " is not a valid calendar date.");
                return;
            }

            // Check if the date is today or before today
            Calendar appointmentCal = Calendar.getInstance();
            appointmentCal.set(year, month - 1, day); // Month is 0-based in Calendar
            Calendar today = Calendar.getInstance();
            if (appointmentCal.compareTo(today) <= 0) {
                System.out.println(
                        "Appointment date: " + appointmentDate.toString() + " is today or a date before today.");
                return;
            }

            // Check if it's a weekend
            int dayOfWeek = appointmentCal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                System.out.println("Appointment date: " + appointmentDate.toString() + " is Saturday or Sunday.");
                return;
            }

            // Validate timeslot
            if (!isValidTimeslot(timeslot)) {
                System.out.println(timeslot + " is not a valid time slot.");
                return;
            }

            // Validate imaging service
            if (!isValidImagingService(imagingService)) {
                System.out.println(imagingService + " - imaging service not provided.");
                return;
            }

            // Check if an appointment exists for the patient at the same time
            for (Appointment appt : appointments) {
                if (appt.getPatient().getFirstName().equalsIgnoreCase(firstName)
                        && appt.getPatient().getLastName().equalsIgnoreCase(lastName)
                        && appt.getDate().equals(appointmentDate) // Use the Date object for comparison
                        && appt.getTimeslot().equals(timeslot)) {
                    System.out.println(
                            firstName + " " + lastName + " has an existing appointment at the same time slot.");
                    return;
                }
            }

            // Assign the next available technician from the providers list
            Technician technician = assignTechnician();
            if (technician == null) {
                System.out.println("Cannot find an available technician.");
                return;
            }

            // Parse the date of birth
            String[] dobParts = dob.split("/");
            int dobMonth = Integer.parseInt(dobParts[0]);
            int dobDay = Integer.parseInt(dobParts[1]);
            int dobYear = Integer.parseInt(dobParts[2]);
            Date dobDate = new Date(dobYear, dobMonth, dobDay); // Create Date object for DOB

            // Validate patient's date of birth
            String dobValidationResult = isValidDateOfBirth(dobDate);
            if (dobValidationResult != null) {
                System.out.println(dobValidationResult);
                return;
            }

            Profile profile = new Profile(firstName, lastName, dobDate); // Construct the Profile object
            Patient patient = new Patient(profile); // Create a Patient object

            // Create the Imaging appointment
            Radiology room = Radiology.valueOf(imagingService.toUpperCase());
            Imaging imagingAppointment = new Imaging(appointmentDate, new Timeslot(Integer.parseInt(timeslot), 0),
                    patient, technician, room);
            appointments.add(imagingAppointment);
            System.out.println(date + " " + timeslot + ":00 " + firstName + " " + lastName + " [" + technician.getName()
                    + "] booked.");

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
                // Display technician's rate per visit, using the testing methods
                providerDetails += String.format("[rate: $%s]",
                        technician.rate()); // Assuming getRate() method returns the rate as a string
            }

            System.out.println(providerDetails);

        }
        System.out.println();

    }

    private void displayTechnicianRotation() {
        System.out.println("Rotation list for the technicians.");

        StringBuilder rotationList = new StringBuilder();
        Sort.provider(providers);

        for (Provider provider : providers) {
            if (provider instanceof Technician) { // Check if the provider is a Technician
                Technician technician = (Technician) provider;
                rotationList.append(String.format("%s (%s)",
                        technician.getProfile().getFirstName() + " " + technician.getProfile().getLastName(),
                        technician.getLocation().getCity()));

                rotationList.append(" --> "); // Add arrow for rotation
            }
        }

        // Remove the last arrow if there were any technicians
        if (rotationList.length() > 0) {
            rotationList.setLength(rotationList.length() - 5); // Remove last " --> "
        }

        System.out.println(rotationList.toString());
        System.out.println();

    }

    private void listOfficeAppointments() {
        System.out.println("Listing office appointments...");

        // Sort appointments by county, date, and timeslot
        Sort.appointmentByCounty(appointments);

        // Display sorted appointments
        for (Appointment appointment : appointments) {
            if (!(appointment instanceof Imaging)) { // Only list office appointments
                System.out.println(appointment);
            }
        }
    }

    private void listImagingAppointments() {
        System.out.println("Listing imaging appointments...");

        Sort.appointmentByCounty(appointments); // Sort by county, then date and time

        for (Appointment appointment : appointments) {
            if (appointment instanceof Imaging) { // Only display imaging appointments
                System.out.println(appointment);
            }
        }
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
        System.out.println("Displaying billing statements...");
        // Logic to aggregate billing data by patient can be implemented here
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

    private Technician assignTechnician() {
        // Assign a technician from the providers list
        for (Provider provider : providers) {
            if (provider instanceof Technician) {
                return (Technician) provider; // Return the first available technician
            }
        }
        return null; // No technician available
    }
}
