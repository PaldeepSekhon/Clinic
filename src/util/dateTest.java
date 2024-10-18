package util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class dateTest {

    @org.junit.jupiter.api.Test
    @Test
    public void testIsValid() {
        // Valid Dates
        Date validDate1 = new Date(2024, 10, 15); // October 15, 2024
        Date validDate2 = new Date(2022, 2, 28);  // February 28, 2022 (non-leap year)

        assertTrue(validDate1.isValid(), "Valid date (October 15, 2024) should return true.");
        assertTrue(validDate2.isValid(), "Valid date (February 28, 2022) should return true.");

        // Invalid Dates
        Date invalidDate1 = new Date(2024, 2, 30); // February 30 does not exist
        Date invalidDate2 = new Date(2023, 13, 15); // Invalid month (13)
        Date invalidDate3 = new Date(2023, 0, 10);  // Invalid month (0)
        Date invalidDate4 = new Date(2024, 6, 31);  // June 31 does not exist

        assertFalse(invalidDate1.isValid(), "Invalid date (February 30, 2024) should return false.");
        assertFalse(invalidDate2.isValid(), "Invalid month (13) should return false.");
        assertFalse(invalidDate3.isValid(), "Invalid month (0) should return false.");
        assertFalse(invalidDate4.isValid(), "Invalid day (June 31) should return false.");
    }
}
