package util;

/**
 * Enum representing time slots for the clinic's scheduling system.
 * Each timeslot represents a specific hour and minute of the day.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public enum Timeslot {

    SLOT1(9, 0),
    SLOT2(10, 45),
    SLOT3(11, 15),
    SLOT4(13, 30),
    SLOT5(15, 0),
    SLOT6(16, 15);

    private final int hour;
    private final int minute;

    /**
     * Constructor for the Timeslot enum.
     * 
     * @param hour   The hour of the timeslot (24-hour format).
     * @param minute The minute of the timeslot.
     */
    Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
    /**
     * Overrides the default toString method to provide a formatted string 
     * representing the timeslot in 12-hour format with AM/PM notation.
     * 
     * @return A string in the format "H:MM AM/PM".
     */
    @Override
    public String toString() {
        return String.format("%d:%02d %s",
                (hour == 12 || hour == 0) ? 12 : hour % 12, // No leading zero for hours
                minute, // Keep leading zero for minutes
                (hour >= 12) ? "PM" : "AM"); // AM/PM indicator
    }

      /**
     * Static method that converts a string input (representing a slot number) 
     * into a Timeslot enum constant.
     * 
     * @param input The string representing the slot number (e.g., "1" for SLOT1).
     * @return The corresponding Timeslot enum, or null if the input is invalid.
     */
    public static Timeslot fromString(String input) {
        try {
            int slotNumber = Integer.parseInt(input);
            if (slotNumber < 1 || slotNumber > Timeslot.values().length) {
                return null; // Invalid slot number
            }
            return Timeslot.values()[slotNumber - 1]; // Adjust for zero-based index
        } catch (NumberFormatException e) {
            return null; // Input was not a valid number
        }
    }
}

