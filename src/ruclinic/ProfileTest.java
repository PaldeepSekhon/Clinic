package ruclinic;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    Profile profile1 = new Profile("John", "Doe", new Date(1990, 5, 15));
    Profile profile2 = new Profile("Jane", "Smith", new Date(1992, 7, 10));
    Profile profile3 = new Profile("Alice", "Johnson", new Date(1988, 3, 25));
    Profile profile4 = new Profile("John", "Doe", new Date(1990, 5, 15)); // Same as profile1

    @Test
    public void testCompareToNegative() {

        // profile1 < profile2
        assertEquals(-1, profile1.compareTo(profile3), "Profile1 should be less than Profile3.");
        assertEquals(-1, profile2.compareTo(profile3), "Profile2 should be less than Profile3.");
        assertEquals(-1, profile3.compareTo(profile4), "Profile3 should be less than Profile4.");
    }
        @Test
    public void testCompareToPositive() {
        assertEquals(1, profile2.compareTo(profile1), "Profile2 should be greater than Profile1.");
        assertEquals(1, profile3.compareTo(profile1), "Profile3 should be greater than Profile1.");
        assertEquals(1, profile2.compareTo(profile4), "Profile2 should be greater than Profile4.");
    }

    @Test
    public void testCompareToZero(){
        assertEquals(0, profile1.compareTo(profile4), "Profile1 should be equal to Profile4.");
    }

}