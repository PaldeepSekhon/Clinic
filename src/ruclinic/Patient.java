package ruclinic;

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
     * Gets the total charge for all visits based on the provider's specialty charge.
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
    
            currentVisit = currentVisit.getNext();
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

    public Visit getVisits() {
        return visits;
    }

    public void setVisits(Visit visits) {
        this.visits = visits;
    }
}

