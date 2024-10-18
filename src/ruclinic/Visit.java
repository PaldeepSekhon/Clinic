package ruclinic;

/**
 * Class representing a visit to the clinic.
 * Each visit is associated with an appointment and can link to the next visit,
 * forming a linked list of visits for the patient.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Visit {
    private Appointment appointment; // A reference to the associated appointment
    private Visit next; // A reference to the next visit in the list

    /**
     * Constructor for the Visit class.
     * Initializes the visit with an appointment and sets the next visit to null.
     * 
     * @param appointment The appointment associated with the visit.
     */
    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null;
    }

    /**
     * Gets the appointment associated with this visit.
     * 
     * @return The appointment for this visit.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Gets the next visit in the linked list of visits.
     * 
     * @return The next visit in the list, or null if there is no next visit.
     */
    public Visit getNext() {
        return next;
    }

    /**
     * Sets the next visit in the linked list of visits.
     * 
     * @param next The next visit to link to this one.
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * Gets the specialty of the provider associated with this visit's appointment.
     * 
     * @return The specialty of the provider if they are a doctor, or null if no
     *         provider or not a doctor.
     */
    public Specialty getSpecialty() {
        // Check if the provider is a Doctor
        if (appointment != null && appointment.getProvider() instanceof Doctor) {
            return ((Doctor) appointment.getProvider()).getSpecialty(); // Cast to Doctor to get the specialty
        }
        return null; // Return null if not a Doctor or no provider assigned
    }

    /**
     * Returns a string representation of the visit, using the associated
     * appointment's toString method.
     * 
     * @return A string representing this visit.
     */
    @Override
    public String toString() {
        return appointment.toString();
    }
}
