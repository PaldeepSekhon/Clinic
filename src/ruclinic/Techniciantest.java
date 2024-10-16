package ruclinic;

import util.List;

/**
 * The Technician class represents a technician in the clinic.
 * It extends the Provider class and includes a rate per visit.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Techniciantest extends Provider {
    private int ratePerVisit; // The technician's charging rate per visit
    private List<Radiology> handledServices;

    /**
     * Constructs a Technician with a specified profile, location, and rate per
     * visit.
     * 
     * @param profile      The profile of the technician (inherited from Person).
     * @param location     The location where the technician works.
     * @param ratePerVisit The technician's charging rate per visit.
     */
    public Techniciantest(Profile profile, Location location, int ratePerVisit, List<Radiology> handledServices) {
        super(profile, location); // Call to Provider constructor
        this.ratePerVisit = ratePerVisit;
        this.handledServices = handledServices;
    }

    public boolean canHandle(Radiology serviceType) {
        for (Radiology service : handledServices) {
            if (service.equals(serviceType)) {
                return true; // Technician can handle this service
            }
        }
        return false; // Technician cannot handle this service
    }

    public void addHandledService(Radiology service) {
        handledServices.add(service);
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
     * Returns a string representation of the technician, including profile,
     * location, and rate per visit.
     * 
     * @return A string containing the technician's details.
     */
    @Override
    public String toString() {
        return String.format("%s [Rate per Visit: $%d]",
                super.toString(),
                ratePerVisit); // Include the rate in the string representation
    }
}
