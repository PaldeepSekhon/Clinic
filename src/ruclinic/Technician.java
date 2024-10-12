package ruclinic;

/**
 * The Technician class represents a technician in the clinic.
 * It extends the Provider class and includes a rate per visit.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Technician extends Provider {
    private int ratePerVisit; // The technician's charging rate per visit

    /**
     * Constructs a Technician with a specified profile, location, and rate per visit.
     * 
     * @param profile      The profile of the technician (inherited from Person).
     * @param location     The location where the technician works.
     * @param ratePerVisit The technician's charging rate per visit.
     */
    public Technician(Profile profile, Location location, int ratePerVisit) {
        super(profile, location); // Call to Provider constructor
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Implements the abstract rate() method from the Provider class.
     * Returns the technician's charging rate per visit.
     * 
     * @return The technician's rate per visit.
     */
    @Override
    public int rate() {
        return ratePerVisit;
    }

    /**
     * Gets the rate per visit for the technician.
     * 
     * @return The rate per visit.
     */
    public int getRatePerVisit() {
        return ratePerVisit;
    }

    /**
     * Returns a string representation of the technician, including profile, location, and rate per visit.
     * 
     * @return A string containing the technician's details.
     */
    @Override
    public String toString() {
        return String.format("%s, Rate per Visit: $%d", 
            super.toString(), // Call to Provider's toString() for profile and location info
            ratePerVisit);
    }
}

