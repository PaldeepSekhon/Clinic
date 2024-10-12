package ruclinic;

/**
 * The Provider class represents a healthcare provider in the clinic.
 * It is an abstract class that extends Person and includes a location.
 * Subclasses such as Doctor and Technician will implement the rate method.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public abstract class Provider extends Person {
    private Location location; // Location where the provider works

    /**
     * Constructs a Provider with a specified profile and location.
     * 
     * @param profile  The profile of the provider (inherited from Person).
     * @param location The location where the provider practices.
     */
    public Provider(Profile profile, Location location) {
        super(profile); // Call to Person constructor
        this.location = location;
    }

    /**
     * Abstract method that must be implemented by subclasses to return the 
     * provider's charging rate per visit.
     * 
     * @return The provider's charging rate per visit.
     */
    public abstract int rate();

    /**
     * Gets the location of the provider.
     * 
     * @return The location where the provider practices.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the provider.
     * 
     * @param location The new location for the provider.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns a string representation of the provider, including their profile
     * and location.
     * 
     * @return A string containing the provider's details.
     */
    @Override
    public String toString() {
        return String.format("%s [%s]", 
            super.toString(), // Call to Person's toString() for profile info
            location);
    }
}
