package ruclinic;

/**
 * Enum representing the location details for the clinic's providers.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    private final String county;
    private final String zip;

    /**
     * Constructor for the Location enum.
     * 
     * @param county The county of the location.
     * @param zip    The zip code of the location.
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Gets the city, which is the name of the enum constant.
     * 
     * @return The name of the city (enum constant).
     */
    public String getCity() {
        return this.name();
    }

    /**
     * Gets the county of the location.
     * 
     * @return The county of the location.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gets the zip code of the location.
     * 
     * @return The zip code of the location.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Provides a string representation of the location, including city, county, and
     * zip.
     * 
     * @return A formatted string representing the location details.
     */
    @Override
    public String toString() {
        return String.format("%s, %s %s", this.name(), county, zip); // Output: CITY, COUNTY ZIP
    }
}
