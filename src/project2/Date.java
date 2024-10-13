package project2;
import java.util.Calendar;
/**
 * This class represents a Date with day, month, and year.
 * It implements methods to validate the date and compare dates and
 * provide a string presentation of the date.
 *
 * @author Vy Nguyen, Shahnaz Khan
 */
public class Date implements Comparable<Date> {

    // Constants for leap year calculation
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUADRICENTENNIAL = 400;

    // Instance variables for day, month, and year
    private int year;
    private int month;
    private int day;

    /**
     * Default constructor for Date class.
     * Initializes the date to January 1st, 1900.
     */
    public Date() {
        this.month = 1;
        this.day = 1;
        this.year = 1900;
    }

    /**
     * Constructor to create a Date instance.
     *
     * @param day        the day of the date
     * @param month     the month of the date
     * @param year      the year of the date
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * This constructor takes in a string input in the format MM/DD/YYYY,
     * splits and parses the input to initialize instance variables.
     * 
     * @param date the string representing the date (MM/DD/YYYY)
     */
    public Date(String date) {
        String[] parts = date.split("/");
        if (parts.length == 3) {
            // Trim spaces around each part and check if they are numeric
            this.month = Integer.parseInt(parts[0].trim());
            this.day = Integer.parseInt(parts[1].trim());
            this.year = Integer.parseInt(parts[2].trim());
        }
    }

        /**
     * Helper method: Convert this Date object to a Calandar object.
     * 
     *  @return a Calendar object representing this Date object.
     */
    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month);
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        return calendar;
    }

    /**
     * Helper method to check if a given year is a leap year.
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    private boolean isLeapYear(int year) {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUADRICENTENNIAL == 0 ;
            }
            return true;
        }
        return false;
    }

    /**
     * Helper method to get the number of days in a given month and year.
     *
     * @param month the month to check
     * @param year  the year to check
     * @return the number of days in the specified month and year
     */
    private int getDaysInMonth(int month, int year) {
        return (month == 2) ? (isLeapYear(year) ? 29 : 28) : 
               (month == 4 || month == 6 || month == 9 || month == 11) ? 30 : 31;
    }

    /**
     * Validates if the current date instance is a valid calendar date.
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        // Check for invalid year
        if ( year < 1900){
            return false;
        }

        // Check for invalid month
        if ( month <1 || month > 12) {
            return false;
        }

        // Check for invalid day
        int maxDay = getDaysInMonth(month, year);
        return day >=1 && day <= maxDay;
    }

    /**
     * Check if this Date represents today's date by comparing it to the current Calendar system.
     * 
     * @return true if the date is today, false otherwise.
     */
    public boolean isToday(){
        Calendar today = Calendar.getInstance();
        return this.year == today.get(Calendar.YEAR) 
            && this.month == today.get(Calendar.MONTH) +1
            && this.day == today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Checks if this Date represent the date before today.
     * The method compares the year, month, and day of the current date object with today's date. 
     * 
     * @return true if this Date is before today, false otherwise.
     */
    public boolean isPastDate() {
        Calendar today = Calendar.getInstance();
        
        return this.year < today.get(Calendar.YEAR) ||
            (this.year == today.get(Calendar.YEAR) && this.month < today.get(Calendar.MONTH) + 1) ||
            (this.month == (today.get(Calendar.MONTH) + 1) && this.day < today.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Checks if this Date represents a date after today's date.
     * The method compares the year, month, and day of the current date object with today's date.
     * 
     * @return true if this Date is after today, false otherwise.
     */
    public boolean isFutureDate() {
        Calendar today = Calendar.getInstance();

        return this.year > today.get(Calendar.YEAR) ||
            (this.year == today.get(Calendar.YEAR) && this.month > today.get(Calendar.MONTH) + 1) ||
            (this.month == (today.get(Calendar.MONTH) + 1) && this.day > today.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Checks if this Date is on a Weekend ( Saturday or Sunday).
     * 
     * @return true if the date is a weekend, false otherwise.
     */
    public boolean isWeekend() {
        Calendar calendar = this.toCalendar();
        
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month -1);
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     *  Checks if this Date is more than six months after today's date.
     * 
     * @return true if this date is more than six months after today's date.
     */
    public boolean isSixMonthsFromToday() {
        Calendar sixMonths = Calendar.getInstance();
        sixMonths.add(Calendar.MONTH, 6);

        return (this.year > sixMonths.get(Calendar.YEAR)) ||
            (this.year == sixMonths.get(Calendar.YEAR) && this.month > sixMonths.get(Calendar.MONTH) + 1) ||
            (this.year == sixMonths.get(Calendar.YEAR) && this.month == sixMonths.get(Calendar.MONTH + 1)
                && this.day > sixMonths.get(Calendar.DAY_OF_MONTH));
    }


    /**
     * Overrides the equals method to compare two Date objects.
     *
     * @param obj the object to compare to
     * @return true if the two Date objects represent the same date, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return this.day == date.day
                    && this.month == date.month
                    && this.year == date.year;
        }
        return false;
    }

    /**
     * Overrides the compareTo method from the Comparable interface.
     * Compares this Date object with another Date object.
     *
     * @param other the Date object to compare to
     * @return a negative integer, zero, or a positive integer as this date is
     *         less than, equal to, or greater than the specified date.
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        }
        if(this.month != other.month) {
            return Integer.compare(this.month, other.month);
        }
        return Integer.compare(this.day, other.day);
    }

    /**
     * Overrides the toString method to provide a formatted string representation
     * of the Date object in the format mm/dd/yyyy.
     *
     * @return a string representation of the Date in the format mm/dd/yyyy
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    /**
     * Testbed main method to test the Date class functionality.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create an array of test Date objects
        Date[] testDates = {
                new Date(2, 2, 800),    // Invalid: Year before 1900
                new Date(2, 29, 2003),  // Invalid: Non-leap year
                new Date(13, 15, 2020), // Invalid: Month out of range
                new Date(4, 31, 2020),  // Invalid: April has 30 days
                new Date(2, 29, 2024),  // Valid: Leap year
                new Date(7, 31, 2021),  // Valid: July has 31 days       
        };

        System.out.println("Testing isValid() method:");
        // Loop through each Date object in the array and test validity
        for (int i = 0; i < testDates.length; i++) {
            Date testDate = testDates[i];
            System.out.println("Test case " + (i + 1) + ": " + testDate.toString() + " is valid? " + testDate.isValid());
        }
    }
}


