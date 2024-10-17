package ruclinic;

import util.Date;
import util.Timeslot;

/**
 * Represents an appointment in the clinic.
 * 
 * Refactored to replace Profile with Person (as Patient and Provider) and
 * support both Doctor and Technician as providers.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Appointment implements Comparable<Appointment> {
    protected Date date; // Protected so subclasses can access it
    protected Timeslot timeslot; // Protected
    protected Person patient; // Changed from Profile to Person, Protected
    protected Provider provider; // Can be either a Doctor or Technician, Protected

    /**
     * Constructs an Appointment with the specified date, timeslot, patient, and
     * provider.
     *
     * @param date     the date of the appointment
     * @param timeslot the timeslot of the appointment
     * @param patient  the patient's profile for the appointment
     * @param provider the provider handling the appointment (either Doctor or
     *                 Technician)
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Provider provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Determines whether this appointment is equal to another object.
     * Two appointments are considered equal if their date, timeslot, patient, and
     * provider are identical.
     *
     * @param obj the object to be compared
     * @return true if this appointment is the same as the obj argument; false
     *         otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Appointment that = (Appointment) obj;
        return date.equals(that.date) &&
                timeslot.equals(that.timeslot) &&
                patient.equals(that.patient) &&
                provider.equals(that.provider); // Compare the provider as well
    }

    /**
     * Compares this appointment to another appointment to determine their ordering.
     * Appointments are ordered first by date, then by timeslot, and finally by
     * patient.
     *
     * @param other the appointment to be compared
     * @return a negative integer, zero, or a positive integer as this appointment
     *         is less than,
     *         equal to, or greater than the specified appointment
     */
    @Override
    public int compareTo(Appointment other) {
        // First compare by date
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        // Then compare by timeslot
        int timeslotComparison = this.timeslot.compareTo(other.timeslot);
        if (timeslotComparison != 0) {
            return timeslotComparison;
        }
        // Finally compare by patient profile
        return this.patient.compareTo(other.patient);
    }

    /**
     * Returns a string representation of the appointment, showing the date,
     * timeslot,
     * patient information, and provider details.
     *
     * @return a string representation of the appointment
     */
    @Override
    public String toString() {
        String specialtyInfo = "N/A"; // Default value if provider is not a Doctor

        // Check if the provider is a Doctor and get the specialty if so
        if (this.provider instanceof Doctor) {
            Doctor doctor = (Doctor) this.provider; // Cast to Doctor
            specialtyInfo = doctor.getSpecialty().getNameOnly(); // Get the name of the specialty
        }

        return String.format("%s %s %s %s %s [%s, %s, %s %s, %s]",
                this.date, // Appointment date
                this.timeslot, // Timeslot
                this.patient.getFirstName(), // Patient's first name
                this.patient.getLastName(), // Patient's last name
                this.patient.getDob(), // Patient's date of birth
                this.provider.getName(), // Provider's name
                this.provider.getLocation().getCity(), // Provider's location city
                this.provider.getLocation().getCounty(), // Provider's location county
                this.provider.getLocation().getZip(), // Provider's location ZIP code
                specialtyInfo // Provider's specialty (or "N/A" if not a Doctor)
        );
    }

    // Getter and Setter methods

    public Date getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Person getPatient() {
        return patient;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setTimeSlot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Gets the service type of the appointment if it's an imaging appointment.
     * 
     * @return the service type (XRAY, ULTRASOUND, CATSCAN) if it's an imaging
     *         appointment,
     *         or null if it's a regular appointment
     */
    public String getServiceType() {
        if (this instanceof Imaging) {
            Imaging imaging = (Imaging) this;
            return imaging.getRoom().toString();
        }
        return null;
    }

    /**
     * Gets the technician for this appointment if it's an imaging appointment.
     * 
     * @return the Technician if the provider is a Technician, null otherwise
     */
    public Technician getTechnician() {
        if (provider instanceof Technician) {
            return (Technician) provider;
        }
        return null;
    }
}