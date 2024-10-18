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
                Appointment current = list.get(j);
                Appointment next = list.get(j + 1);
                boolean shouldSwap = false;

                // Compare counties
                String countyCurrent = current.getProvider().getLocation().getCounty();
                String countyNext = next.getProvider().getLocation().getCounty();

                // First, compare counties
                if (countyCurrent.compareTo(countyNext) > 0) {
                    shouldSwap = true;
                } else if (countyCurrent.equals(countyNext)) {
                    // If counties are the same, compare dates
                    if (current.getDate().compareTo(next.getDate()) > 0) {
                        shouldSwap = true;
                    } else if (current.getDate().equals(next.getDate())) {
                        // If dates are the same, compare timeslots
                        if (current.getTimeslot().compareTo(next.getTimeslot()) > 0) {
                            shouldSwap = true;
                        } else if (current.getTimeslot().equals(next.getTimeslot())) {
                            // If timeslots are the same, compare first names of the providers
                            String firstNameCurrent = current.getProvider().getProfile().getFirstName();
                            String firstNameNext = next.getProvider().getProfile().getFirstName();
                            if (firstNameCurrent.compareTo(firstNameNext) > 0) {
                                shouldSwap = true;
                            }
                        }
                    }
                }

                // Perform the swap if necessary
                if (shouldSwap) {
                    list.set(j, next);
                    list.set(j + 1, current);
                }
            }
        }
    }

    public static void appointmentByDateTimeAndProvider(util.List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Appointment current = list.get(j);
                Appointment next = list.get(j + 1);
                boolean shouldSwap = false;

                // Compare dates first
                int dateComparison = current.getDate().compareTo(next.getDate());
                if (dateComparison > 0) {
                    shouldSwap = true;
                } else if (dateComparison == 0) {
                    // If dates are the same, compare timeslots
                    int timeslotComparison = current.getTimeslot().compareTo(next.getTimeslot());
                    if (timeslotComparison > 0) {
                        shouldSwap = true;
                    } else if (timeslotComparison == 0) {
                        // If timeslots are the same, compare provider's last names
                        String lastNameCurrent = current.getProvider().getProfile().getLastName();
                        String lastNameNext = next.getProvider().getProfile().getLastName();
                        int lastNameComparison = lastNameCurrent.compareTo(lastNameNext);
                        if (lastNameComparison > 0) {
                            shouldSwap = true;
                        } else if (lastNameComparison == 0) {
                            // If last names are the same, compare first names
                            String firstNameCurrent = current.getProvider().getProfile().getFirstName();
                            String firstNameNext = next.getProvider().getProfile().getFirstName();
                            if (firstNameCurrent.compareTo(firstNameNext) > 0) {
                                shouldSwap = true;
                            }
                        }
                    }
                }

                // Perform the swap if necessary
                if (shouldSwap) {
                    Appointment temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
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
