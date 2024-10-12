package util;

/**
 * Class representing time slots for the clinic's scheduling system.
 * Each timeslot represents a specific hour and minute of the day.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    /**
     * Constructs a Timeslot object with the specified hour and minute.
     * 
     * @param hour   The hour of the timeslot (24-hour format).
     * @param minute The minute of the timeslot.
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns a string representation of the timeslot in 12-hour format with AM/PM notation.
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
     * Compares this Timeslot to another Timeslot.
     * Timeslots are compared first by hour, then by minute.
     * 
     * @param other the Timeslot to compare with.
     * @return a negative integer, zero, or a positive integer as this timeslot is
     *         earlier than, equal to, or later than the specified timeslot.
     */
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return this.hour - other.hour;
        }
        return this.minute - other.minute;
    }

    /**
     * Static method to convert a string representing a slot number (e.g., "1")
     * into a predefined Timeslot object.
     * 
     * @param input The string representing the slot number (e.g., "1" for the first slot).
     * @return The corresponding Timeslot object, or null if the input is invalid.
     */
    public static Timeslot fromString(String input) {
        try {
            int slotNumber = Integer.parseInt(input);
            switch (slotNumber) {
                case 1:
                    return new Timeslot(9, 0);
                case 2:
                    return new Timeslot(10, 45);
                case 3:
                    return new Timeslot(11, 15);
                case 4:
                    return new Timeslot(13, 30);
                case 5:
                    return new Timeslot(15, 0);
                case 6:
                    return new Timeslot(16, 15);
                default:
                    return null; // Invalid slot number
            }
        } catch (NumberFormatException e) {
            return null; // Input was not a valid number
        }
    }

    /**
     * Gets the hour of this timeslot.
     * 
     * @return the hour (24-hour format).
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of this timeslot.
     * 
     * @return the minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets the hour of this timeslot.
     * 
     * @param hour the hour to set (24-hour format).
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Sets the minute of this timeslot.
     * 
     * @param minute the minute to set.
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }
}