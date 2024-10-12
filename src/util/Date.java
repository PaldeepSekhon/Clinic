package util;

/**
 * Represents a date with year, month, and day fields.
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Constructs a Date object with the specified year, month, and day.
     * 
     * @param year  the year of the date
     * @param month the month of the date (1-12)
     * @param day   the day of the month (1-31 depending on the month)
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Returns a string representation of the date in the format MM/DD/YYYY.
     * 
     * @return a string representing the date
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Compares this date to another object to determine equality.
     * Two Date objects are considered equal if their year, month, and day fields
     * are identical.
     * 
     * @param obj the object to be compared with this date
     * @return true if this date is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Date date = (Date) obj;
        return year == date.year && month == date.month && day == date.day;
    }

    /**
     * Compares this date to another date.
     * Dates are compared first by year, then by month, and finally by day.
     * 
     * @param other the date to be compared with
     * @return a negative integer, zero, or a positive integer as this date is
     *         earlier than, equal to, or later than the specified date
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        } else if (this.month != other.month) {
            return this.month - other.month;
        } else {
            return this.day - other.day;
        }
    }

    /**
     * Validates the date.
     * Checks if the date has a valid year, month, and day, considering leap years
     * for February.
     * 
     * @return true if the date is valid, false otherwise
     */

    public boolean isValid() {
        if (year < 1)
            return false; // Year must be positive
        if (month < 1 || month > 12)
            return false; // Month must be between 1 and 12

        // Days in each month, with February adjusted for leap years
        int[] daysInMonth = { 0, 31, (isLeapYear() ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        // Check if the day is within the valid range for the given month
        return day >= 1 && day <= daysInMonth[month];
    }

    /**
     * Checks if the year is a leap year.
     * A leap year is divisible by 4, but not by 100, unless it is also divisible by
     * 400.
     * 
     * @return true if the year is a leap year, false otherwise
     */

    private boolean isLeapYear() {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return year % 4 == 0;
        }
    }

    /**
     * Gets the year of this date.
     * 
     * @return the year of this date
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the month of this date.
     * 
     * @return the month of this date
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the day of this date.
     * 
     * @return the day of this date
     */
    public int getDay() {
        return day;
    }

    /**
     * Main method for testing the Date class with various test cases.
     * 
     */

    public static void main(String[] args) {
        // Test Case 1: Year set to 0 (Invalid year)
        Date date1 = new Date(0, 12, 25);
        System.out.println("Test Case 1 - Date1: " + date1); // Should print "12/25/0000"
        System.out.println("Is Date1 valid? " + date1.isValid()); // Should print false

        // Test Case 2: Day exceeds the maximum for April (April 31st, invalid date)
        Date date2 = new Date(2023, 4, 31);
        System.out.println("Test Case 2 - Date2: " + date2); // Should print "4/31/2023"
        System.out.println("Is Date2 valid? " + date2.isValid()); // Should print false

        // Test Case 3: Invalid month less than 1 (Month set to 0)
        Date date3 = new Date(2021, 0, 15);
        System.out.println("Test Case 3 - Date3: " + date3); // Should print "0/15/2021"
        System.out.println("Is Date3 valid? " + date3.isValid()); // Should print false

        // Test Case 4: Day set to 0 (Invalid day)
        Date date4 = new Date(2023, 3, 0);
        System.out.println("Test Case 4 - Date4: " + date4); // Should print "3/0/2023"
        System.out.println("Is Date4 valid? " + date4.isValid()); // Should print false

        // Test Case 5: Valid leap year date (February 29th, 2020)
        Date date5 = new Date(2020, 2, 29);
        System.out.println("Test Case 5 - Date5: " + date5); // Should print "2/29/2020"
        System.out.println("Is Date5 valid? " + date5.isValid()); // Should print true

        // Test Case 6: Valid date in a common year (December 25th, 2023)
        Date date6 = new Date(2023, 12, 25);
        System.out.println("Test Case 6 - Date6: " + date6); // Should print "12/25/2023"
        System.out.println("Is Date6 valid? " + date6.isValid()); // Should print true

    }
}

