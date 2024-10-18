package ruclinic;

import util.Date;
import util.Timeslot;

/**
 * The Imaging class represents an imaging appointment in the clinic.
 * It extends the Appointment class to hold additional data specific to imaging
 * appointments,
 * including the type of imaging room (CATSCAN, ULTRASOUND, XRAY).
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Imaging extends Appointment {
    private Radiology room; // Tracks the type of imaging room (CATSCAN, ULTRASOUND, XRAY)

    /**
     * Constructs an Imaging appointment with the specified date, timeslot, patient,
     * provider, and room.
     * 
     * @param date     The date of the imaging appointment.
     * @param timeslot The timeslot for the imaging appointment.
     * @param patient  The patient's profile for the appointment (Person).
     * @param provider The provider handling the appointment (either Doctor or
     *                 Technician).
     * @param room     The imaging room for the appointment (CATSCAN, ULTRASOUND, or
     *                 XRAY).
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Provider provider, Radiology room) {
        super(date, timeslot, patient, provider); // Call the constructor of the Appointment class
        this.room = room;
    }

    /**
     * Gets the type of imaging room for this appointment.
     * 
     * @return The imaging room (CATSCAN, ULTRASOUND, or XRAY).
     */
    public Radiology getRoom() {
        return room;
    }

    /**
     * Sets the type of imaging room for this appointment.
     * 
     * @param room The new imaging room (CATSCAN, ULTRASOUND, or XRAY).
     */
    public void setRoom(Radiology room) {
        this.room = room;
    }

    /**
     * Returns a string representation of the imaging appointment, including the
     * room type.
     * 
     * @return A string representing the imaging appointment.
     */
    @Override
    public String toString() {
        return String.format("%s, Imaging Room: %s", super.toString(), room);
    }
}
