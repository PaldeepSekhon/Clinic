package ruclinic;

/**
 * The Person class is the superclass of the Patient and Provider classes.
 * It contains common attributes such as profile information.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Person implements Comparable<Person> {
    protected Profile profile; // Common profile for both Patient and Provider

    /**
     * Constructor to initialize a Person with a profile.
     * 
     * @param profile The profile of the person.
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Gets the profile of the person.
     * 
     * @return The profile of the person.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Compares this person to another person by their profile.
     * 
     * @param other The other person to compare against.
     * @return A negative integer, zero, or a positive integer as this person is
     *         less than, equal to, or greater than the other person.
     */
    @Override
    public int compareTo(Person other) {
        return this.profile.compareTo(other.profile); // Assuming Profile has a compareTo method
    }

    /**
     * Checks if two Person objects are equal based on their profile.
     * 
     * @param obj The object to compare against.
     * @return true if the profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Person person = (Person) obj;
        return profile.equals(person.profile);
    }

    /**
     * Returns a string representation of the person's profile.
     * 
     * @return A string containing the person's profile information.
     */
    @Override
    public String toString() {
        return profile.toString();
    }
}
