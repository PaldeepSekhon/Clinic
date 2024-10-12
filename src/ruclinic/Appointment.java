package ruclinic;

/**
 * Represents an appointment in the clinic.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */

public class Appointment implements Comparable<Appointment> {
    private Date date;
    private Timeslot timeslot;
    private Profile patient;
    private Provider provider;

    /**
     * Constructs an Appointment with the specified date, timeslot, patient, and
     * provider.
     *
     * @param date     the date of the appointment
     * @param timeslot the timeslot of the appointment
     * @param patient  the patient's profile for the appointment
     * @param provider the provider handling the appointment
     */
    public Appointment(Date date, Timeslot timeslot, Profile patient, Provider provider) {
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
        return String.format("%s %s %s %s %s [%s, %s, %s %s, %s]",
                this.date, // Appointment date
                this.timeslot, // Timeslot
                this.patient.getFname(), // Patient's first name
                this.patient.getLname(), // Patient's last name
                this.patient.getDob(), // Patient's date of birth
                this.provider.name(), // Provider's name
                this.provider.getLocation().getCity(), // Provider's location city
                this.provider.getLocation().getCounty(), // Provider's location county
                this.provider.getLocation().getZip(), // Provider's location ZIP code
                this.provider.getSpecialty().getNameOnly() // Provider's specialty (without price)
        );
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the date of the appointment
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the timeslot of the appointment.
     *
     * @return the timeslot of the appointment
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Gets the patient profile associated with the appointment.
     *
     * @return the patient's profile
     */
    public Profile getPatient() {
        return patient;
    }

    /**
     * Gets the provider handling the appointment.
     *
     * @return the provider of the appointment
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Sets the timeslot of the appointment, allowing the appointment to be
     * rescheduled.
     *
     * @param timeslot the new timeslot for the appointment
     */
    public void setTimeSlot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Sets the provider of the appointment, allowing the provider to be updated if
     * necessary.
     *
     * @param provider the new provider for the appointment
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
