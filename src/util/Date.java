package util;

import java.util.Calendar;

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

    // Constructor
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Date date = (Date) obj;
        return year == date.year && month == date.month && day == date.day;
    }

    @Override
    public int compareTo(Date other) {
        if (this.year != other.year)
            return this.year - other.year;
        if (this.month != other.month)
            return this.month - other.month;
        return this.day - other.day;
    }

    public boolean isValid() {
        if (year < 1 || month < 1 || month > 12 || day < 1) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(year, month - 1, day); // Adjust for 0-based months

        try {
            calendar.getTime(); // Will throw an exception if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWeekend() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // Adjust for 0-based months
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    public boolean isWithinSixMonths() {
        Calendar sixMonthsFromNow = Calendar.getInstance();
        sixMonthsFromNow.add(Calendar.MONTH, 6);
        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.set(year, month - 1, day); // Adjust for 0-based months

        return appointmentDate.before(sixMonthsFromNow);
    }

    // Additional getter methods
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
