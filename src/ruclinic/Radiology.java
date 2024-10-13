package ruclinic;

/**
 * The Radiology enum represents three types of imaging services provided in the clinic.
 * This enum includes the following services:
 * - CATSCAN
 * - ULTRASOUND
 * - XRAY
 * 
 * The enum simply categorizes these services and does not associate prices.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public enum Radiology {
    CATSCAN,
    ULTRASOUND,
    XRAY;

    /**
     * Returns a string representation of the radiology service, using the service name.
     * 
     * @return A string representing the radiology service.
     */
    @Override
    public String toString() {
        return this.name();
    }
}
