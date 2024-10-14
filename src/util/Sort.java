package util;

import ruclinic.Appointment;
import ruclinic.Provider;
import ruclinic.Doctor;

public class Sort {

    /**
     * Sorts the given list of Appointment objects in place based on the provided
     * key.
     * 
     * @param list the list of appointments to sort.
     * @param key  the key to sort by ('d' for date, 't' for timeslot, 'p' for
     *             patient).
     */
    public static void appointment(util.List<Appointment> list, char key) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                boolean shouldSwap = false;
                Appointment current = list.get(j);
                Appointment next = list.get(j + 1);

                switch (key) {
                    case 'd': // Sort by date
                        if (current.getDate().compareTo(next.getDate()) > 0) {
                            shouldSwap = true;
                        }
                        break;
                    case 't': // Sort by timeslot
                        if (current.getDate().equals(next.getDate()) &&
                                current.getTimeslot().compareTo(next.getTimeslot()) > 0) {
                            shouldSwap = true;
                        }
                        break;
                    case 'p': // Sort by patient's name
                        String currentPatient = current.getPatient().getLastName() + " "
                                + current.getPatient().getFirstName();
                        String nextPatient = next.getPatient().getLastName() + " " + next.getPatient().getFirstName();
                        if (currentPatient.compareTo(nextPatient) > 0) {
                            shouldSwap = true;
                        }
                        break;
                    default:
                        System.out.println("Invalid key for sorting appointments.");
                        return;
                }

                if (shouldSwap) {
                    // Swap the appointments in place
                    list.set(j, next);
                    list.set(j + 1, current);
                }
            }
        }
    }

    /**
     * Sorts the given list of Appointment objects by county, then by date and
     * timeslot.
     * 
     * @param list the list of appointments to sort.
     */
    /**
     * Sorts the given list of Appointment objects by county, then by date and
     * timeslot.
     * 
     * @param list the list of appointments to sort.
     */
    public static void appointmentByCounty(util.List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                boolean shouldSwap = false;
                Appointment current = list.get(j);
                Appointment next = list.get(j + 1);

                // Check if the current provider is a Doctor
                if (current.getProvider() instanceof Doctor && next.getProvider() instanceof Doctor) {
                    String countyCurrent = ((Doctor) current.getProvider()).getLocation().getCounty();
                    String countyNext = ((Doctor) next.getProvider()).getLocation().getCounty();

                    // First, compare counties
                    if (countyCurrent.compareTo(countyNext) > 0) {
                        shouldSwap = true;
                    } else if (countyCurrent.equals(countyNext)) {
                        // If counties are the same, compare by date
                        if (current.getDate().compareTo(next.getDate()) > 0) {
                            shouldSwap = true;
                        } else if (current.getDate().equals(next.getDate())) {
                            // If dates are the same, compare by timeslot
                            if (current.getTimeslot().compareTo(next.getTimeslot()) > 0) {
                                shouldSwap = true;
                            }
                        }
                    }
                } else {
                    // If the provider is not a Doctor, you can decide how to handle it
                    // For example, you might want to just skip this comparison
                    continue;
                }

                if (shouldSwap) {
                    // Swap the appointments in place
                    list.set(j, next);
                    list.set(j + 1, current);
                }
            }
        }
    }

    /**
     * Sorts the given list of Provider objects in place.
     * This method modifies the original list.
     * 
     * @param list the list of providers to sort.
     */
    public static void provider(util.List<Provider> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                // Sort providers by last name
                if (list.get(j).getProfile().getLastName().compareTo(list.get(j + 1).getProfile().getLastName()) > 0) {
                    // Swap the providers in place
                    Provider temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}
