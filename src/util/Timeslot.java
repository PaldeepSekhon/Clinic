package util;

public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d %s",
                (hour == 12 || hour == 0) ? 12 : hour % 12, // No leading zero for hours
                minute, // Keep leading zero for minutes
                (hour >= 12) ? "PM" : "AM"); // AM/PM indicator
    }

    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return this.hour - other.hour;
        }
        return this.minute - other.minute;
    }

    public static Timeslot fromString(String input) {
        // Implement conversion logic to map input string to Timeslot
        int slotNumber = Integer.parseInt(input);
        switch (slotNumber) {
            case 1:
                return new Timeslot(9, 0);
            case 2:
                return new Timeslot(9, 30);
            case 3:
                return new Timeslot(10, 0);
            case 4:
                return new Timeslot(10, 30);
            case 5:
                return new Timeslot(11, 0);
            case 6:
                return new Timeslot(11, 30);
            case 7:
                return new Timeslot(14, 0);
            case 8:
                return new Timeslot(14, 30);
            case 9:
                return new Timeslot(15, 0);
            case 10:
                return new Timeslot(15, 30);
            case 11:
                return new Timeslot(16, 0);
            case 12:
                return new Timeslot(16, 30);
            default:
                return null; // Invalid slot number
        }
    }

    public int getHour() {
        return hour;
    }

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