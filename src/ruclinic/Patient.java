package ruclinic;

import util.Date;

/**
 * The Patient class represents a patient in the clinic, containing a profile
 * and a linked list of completed visits.
 * This class inherits from Person.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Patient extends Person {
    private Visit visits; // A linked list of visits (completed appointments)

    /**
     * Constructs a Patient with a specified profile.
     * 
     * @param profile The profile of the patient (inherited from Person).
     */
    public Patient(Profile profile) {
        super(profile); // Call to Person constructor
        this.visits = null;
    }

    // Add methods to retrieve patient details (first name, last name, date of
    // birth) from profile

    /**
     * Gets the patient's first name from their profile.
     * 
     * @return The first name of the patient.
     */
    public String getFirstName() {
        return profile.getFirstName(); // Assuming Profile class has getFirstName()
    }

    /**
     * Gets the patient's last name from their profile.
     * 
     * @return The last name of the patient.
     */
    public String getLastName() {
        return profile.getLastName(); // Assuming Profile class has getLastName()
    }

    /**
     * Gets the patient's date of birth from their profile.
     * 
     * @return The date of birth of the patient.
     */
    @Override
    public Date getDob() {
        return profile.getDob(); // Assuming Profile class has getDob() (returns Date)
    }

    /**
     * Adds a visit to the linked list of visits. If the visit already exists, it
     * will not be added.
     * 
     * @param visit The visit to be added to the list.
     */
    public void addVisit(Visit visit) {
        if (visits == null) {
            visits = visit;
        } else {
            Visit current = visits;
            while (current.getNext() != null) {
                if (current.getAppointment().equals(visit.getAppointment())) {
                    return; // Duplicate visit, do not add
                }
                current = current.getNext();
            }
            if (!current.getAppointment().equals(visit.getAppointment())) {
                current.setNext(visit);
            }
        }
    }

    /**
     * Removes a visit corresponding to a canceled appointment from the linked list.
     * 
     * @param appointment The appointment to be removed.
     */
    public void removeVisit(Appointment appointment) {
        Visit current = visits;
        Visit previous = null;

        while (current != null) {
            if (current.getAppointment().equals(appointment)) {
                if (previous == null) {
                    visits = current.getNext(); // Remove first visit
                } else {
                    previous.setNext(current.getNext()); // Remove current visit
                }
                current = null; // Nullify to help garbage collection
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }

    /**
     * Gets the total charge for all visits based on the provider's specialty or
     * rate.
     * It handles both doctors (using specialty charge) and technicians (using rate
     * per visit).
     * 
     * @return The total charge for the patient's visits.
     */
    public int charge() {
        int totalCharge = 0;
        Visit currentVisit = visits;

        while (currentVisit != null) {
            Provider provider = currentVisit.getAppointment().getProvider();

            // Check if the provider is a Doctor
            if (provider instanceof Doctor) {
                Doctor doctor = (Doctor) provider; // Cast to Doctor to access getSpecialty()
                totalCharge += doctor.getSpecialty().getCharge(); // Get the charge from the doctor's specialty
            }
            // Handle Technician case
            else if (provider instanceof Technician) {
                Technician technician = (Technician) provider; // Cast to Technician to access getRatePerVisit()
                totalCharge += technician.getRatePerVisit(); // Add the rate per visit for the technician
            }

            currentVisit = currentVisit.getNext(); // Move to the next visit in the list
        }

        return totalCharge;
    }

    /**
     * Returns a string representation of the patient's profile.
     * 
     * @return A string containing the patient's profile.
     */
    @Override
    public String toString() {
        return super.toString(); // Call to Person's toString() for profile info
    }

    // Getter and Setter for visits

    /**
     * Gets the list of visits for the patient.
     * 
     * @return The head of the linked list of visits.
     */
    public Visit getVisits() {
        return visits;
    }

    /**
     * Sets the linked list of visits for the patient.
     * 
     * @param visits The linked list of visits to set.
     */
    public void setVisits(Visit visits) {
        this.visits = visits;
    }
}
