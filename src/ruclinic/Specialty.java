package ruclinic;

/**
 * Enum representing the specialties offered by providers at the clinic.
 * Each specialty has an associated charge for services.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    /**
     * Constructor for the Specialty enum.
     * 
     * @param charge The service charge associated with the specialty.
     */
    Specialty(int charge) {
        this.charge = charge;
    }

     /**
     * Gets the charge associated with the specialty.
     * 
     * @return The charge for the specialty.
     */
    public int getCharge() {
        return charge;
    }
    /**
     * Overrides the default toString method to provide a formatted string 
     * representing the specialty and its associated charge.
     * 
     * @return A string in the format "SPECIALTY_NAME - $CHARGE".
     */
    @Override
    public String toString() {
        return this.name() + " - $" + charge;
    }
    /**
     * Method to get the name of the specialty without the charge.
     * 
     * @return The name of the specialty (enum constant).
     */
    public String getNameOnly() {
        return this.name();
    }
}
