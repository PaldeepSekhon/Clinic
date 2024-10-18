package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ruclinic.Profile;
import ruclinic.Provider;
import ruclinic.Doctor;
import ruclinic.Location;
import ruclinic.Specialty;
import ruclinic.Technician;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    private List<Provider> providerList;
    @BeforeEach
    void setUp() {
        providerList = new util.List<>();
    }

    @Test
    void add() {
        Doctor doctor = new Doctor(new Profile("John", "Doe", new Date(1980, 5, 20)),
                Location.EDISON, Specialty.FAMILY, "123456789");
        Technician technician = new Technician(new Profile("Alice", "Smith", new Date(1990, 10, 12)),
                Location.PISCATAWAY, 150);

        // Add a Doctor to the list
        providerList.add(doctor);
        assertTrue(providerList.contains(doctor), "Doctor should be added to the list.");


        // Add a Technician to the list
        providerList.add(technician);
        assertTrue(providerList.contains(technician), "Technician should be added to the list.");
    }

    @Test
    void remove() {
        Doctor doctor = new Doctor(new Profile("John", "Doe", new Date(1980, 5, 20)),
                Location.EDISON, Specialty.FAMILY, "123456789");
        Technician technician = new Technician(new Profile("Alice", "Smith", new Date(1990, 10, 12)),
                Location.PISCATAWAY, 150);

        // Add both to the list
        providerList.add(doctor);
        providerList.add(technician);

        // Remove Doctor
        providerList.remove(doctor);
        assertFalse(providerList.contains(doctor), "Doctor should be removed from the list.");

        // Remove Technician
        providerList.remove(technician);
        assertFalse(providerList.contains(technician), "Technician should be removed from the list.");
    }
}