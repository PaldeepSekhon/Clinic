package ruclinic;

/**
 * The Doctor class represents a doctor in the clinic. It extends the Provider
 * class
 * and includes a specialty and NPI (National Provider Identification).
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Doctor extends Provider {
    private Specialty specialty; // The doctor's specialty, which affects the rate per visit
    private String npi; // National Provider Identification number

    /**
     * Constructs a Doctor with a specified profile, location, specialty, and NPI.
     * 
     * @param profile   The profile of the doctor (inherited from Person).
     * @param location  The location where the doctor practices.
     * @param specialty The specialty of the doctor.
     * @param npi       The doctor's National Provider Identification number.
     */
    public Doctor(Profile profile, Location location, Specialty specialty, String npi) {
        super(profile, location); // Call to Provider constructor
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Gets the specialty of the doctor.
     * 
     * @return The doctor's specialty.
     */
    public Specialty getSpecialty() {
        return specialty;
    }

    /**
     * Gets the National Provider Identification (NPI) number.
     * 
     * @return The doctor's NPI.
     */
    public String getNpi() {
        return npi;
    }

    /**
     * Implements the abstract rate() method from the Provider class.
     * Returns the rate per visit based on the doctor's specialty.
     * 
     * @return The doctor's charging rate per visit.
     */
    @Override
    public int rate() {
        return specialty.getCharge(); // Assuming Specialty has a getCharge() method
    }

    /**
     * Returns a string representation of the doctor, including profile, location,
     * specialty, and NPI.
     * 
     * @return A string containing the doctor's details.
     */
    @Override
    public String toString() {
        return String.format("%s [%s, NPI: %s]",
                super.toString(),
                specialty.getNameOnly(),
                npi);
    }
}
