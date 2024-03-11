package fitnessclubgui.fitnessclub;


import java.util.Calendar;

/**
 * The {@code Date} class represents a date to be used In Collection Manager.
 * The Class contains logic to determine if created date is a valid Calendar date.
 *
 * @author Francisco Marquez, Ryan Colling, Ashley Berlinski
 */
public class Date implements Comparable<Date> {

    /**
     * This enum represents the valid months and days for the Date Class.
     * Each month has a month value and the amount of days associated with that month.
     */
    private enum Month {
        JANUARY(1, 31), FEBRUARY(2, 28), MARCH(3, 31),
        APRIL(4, 30), MAY(5, 31), JUN(6, 30),
        JULY(7, 31), AUGUST(8, 31), SEPTEMBER(9, 30),
        OCTOBER(10, 31), NOVEMBER(11, 30), DECEMBER(12, 31),
        LEAP_FEBRUARY(2, 29);

        private final int monthNum;
        private final int monthDays;

        /**
         * Constructs an enum Month
         *
         * @param monthNum  the value associated with a month
         * @param monthDays the amount of days in a month
         */
        Month(int monthNum, int monthDays) {
            this.monthNum = monthNum;
            this.monthDays = monthDays;
        }

        /**
         * Returns the month's number. Ranging from 1-12
         * @return the month's number. Ranging from 1-12
         */
        public int getMonth() {
            return monthNum;
        }

        /**
         * Returns the days in a month.
         * @return the days in a month.
         */
        public int getDays() {
            return monthDays;
        }

        /**
         * Gets the number of days in a given month.
         * @param month the month that is given
         * @return the number of days in that month, otherwise 0 if outside the range of (1-12)
         */
        private static int getMonthDays(int month) {
            for (Month element: Month.values()) {
                if (element.getMonth() == month) {
                    return element.getDays();
                }
            }
            return 0;
        }
    }

    private final int day;
    private final int month;
    private final int year;

    /**
     * Initializes a Date object.
     * Parses token and stores the month, day, and year.
     *
     * @param token A string in the format of "mm/dd/yyyy
     */
    public Date (String token) {
        String[] dateToken = token.split("/");
        this.month = Integer.parseInt(dateToken[0]);
        this.day = Integer.parseInt(dateToken[1]);
        this.year = Integer.parseInt(dateToken[2]);
    }

    /**
     * Initializes a Date Object.
     *
     * @param year  int representing year
     * @param month int representing month
     * @param day   int representing day
     */
    public Date (int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * One parameter constructor for Date.
     * Takes in a Calendar object to use for the date.
     * @param cal the calendar used to create date.
     */
    public Date(Calendar cal) {
        this.month = cal.get(Calendar.MONTH) + 1;
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.year = cal.get(Calendar.YEAR);
    }

    /**
     * Constructor used to set expiration date.
     * If member enrolls on the last day of month we set the month to last day of
     * following month. MONTH_WRAP_VALUE is used to set the correct month after it goes
     * out-side of the range 12.
     *
     * @param membershipLength The length of a membership in months.
     */
    public Date(int membershipLength){
        final int MONTH_WRAP_VALUE = 13;
        Date today = Date.todayDate();
        int startMonth = today.getMonth() + membershipLength;
        int year = today.getYear();
        int day = today.getDay();

        int expireMonth = (startMonth) % MONTH_WRAP_VALUE;
        int expireYear = (startMonth) / MONTH_WRAP_VALUE;

        expireYear = year + expireYear;
        this.month = expireMonth;
        this.year = expireYear;
        if (day == Month.getMonthDays(startMonth)) {
            if (month == Month.FEBRUARY.getMonth()) {
                if (leapYear()) {
                    this.day = Month.LEAP_FEBRUARY.getDays();
                } else {
                    this.day = Month.FEBRUARY.getDays();
                }
            } else {
                this.day = Month.getMonthDays(month);
            }
        }else{
            this.day = day;
        }
    }

    /**
     * Static method that returns a Date object with today's Date.
     * Used to determine a members expiration date.
     *
     * @return returns a Date object with today's Date.
     */
    public static Date todayDate() {
        final int MONTH_OFFSET = 1; // Calender indexes month by 0
        Calendar calendar = Calendar.getInstance();
        int calYear = calendar.get(Calendar.YEAR);
        int calMonth = calendar.get(Calendar.MONTH) + MONTH_OFFSET;
        int calDay = calendar.get(Calendar.DAY_OF_MONTH);
        Date todayDate = new Date(calMonth, calDay, calYear);
        return todayDate;
    }

    /**
     * Checks if the Date object contains a valid date.
     * To be a valid date it must not be before the year 1900.
     * Must be date that exist on the Calendar.
     * Cannot be today or a future day.
     *
     * @return true if valid, false otherwise.
     */
    public boolean isValid() {
        final int MIN_YEAR = 1900;

        if (year < MIN_YEAR) {
            return false;
        }
        if (!validateMonthDay()) {
            return false;
        }

        Date todayDate = Date.todayDate();
        final int MIN_AGE = 18;
        final int MONTH_OFFSET = 1;
        int calYear = todayDate.getYear();
        int calMonth = todayDate.getMonth() + MONTH_OFFSET;

        if (compareTo(todayDate) > -1) {
            return false;
        }

        if ((calYear - getYear() < MIN_AGE) || (calYear - getYear() == MIN_AGE && calMonth < getMonth())){
            return false;
        }

        return true;
    }

    /**
     * Helper methods that checks if theMonth and Day of the Date object is a valid calendar day.
     *
     * @return true if month and day are valid values, false otherwise
     */
    private boolean validateMonthDay() {
        Month enumMonth = null;
        for (Month element : Month.values()) {
            if (element.getMonth() == month) {
                enumMonth = element;
                break;
            }
        }
        if (enumMonth == null) return false;
        if (enumMonth == Month.FEBRUARY) {
            if (leapYear()) {
                enumMonth = Month.LEAP_FEBRUARY;
            }
        }
        return day <= enumMonth.getDays() && day > 0; // day has to be between a months range

    }
    /**
     * Checks if the Date object's year is considered a leap year.
     *
     * @return true if leap year, false otherwise.
     */
    private boolean leapYear() {
        final int QUADRENNIAL = 4;
        final int CENTENNIAL = 100;
        final int QUATERCENTENNIAL = 400;
        if (year % QUADRENNIAL == 0) {
            return year % CENTENNIAL != 0 || year % QUATERCENTENNIAL == 0;
        }
        return false;
    }

    /**
     * Checks if a person with this date as a DOB would be an adult.
     * @return true if DOB would make the person 18 years or older, false if otherwise.
     */
    public boolean isAdult() {
        int adult = 18;
        Date today = Date.getTodaysDate();
        int age = today.getYear() - this.getYear();

        if (age == adult) { //Check months if difference of years is 18
            return (this.getMonth() <= today.getMonth());
        }
        return (age > adult);
    }

    /**
     * Returns the month of the date
     * @return the month of the date
     */
    public int getMonth() {
        return month;
    }

    /**
     * Return the year of the date
     * @return the year of the date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the day of the date
     * @return the day of the date
     */
    public int getDay () {
        return day;
    }

    /**
     * Compares Date objects based on date order.
     * Dates that come before other dates are consider less than.
     *
     * @param that the date object to be compared to.
     * @return 0 if the items are equal.
     * -1 if this.date less than that.date.
     * 1 if this.date greater than that.date
     */
    @Override
    public int compareTo(Date that) {
        int less = -1, greater = 1, equal = 0;
        if (this.year < that.year) return less;
        if (this.year > that.year) return greater;
        if (this.month < that.month) return less;
        if (this.month > that.month) return greater;
        if (this.day < that.day) return less;
        if (this.day > that.day) return greater;
        return equal;
    }

    /**
     * Returns a string in the format of mm/dd/year
     * @return A string in the format of mm/dd/year
     */
    @Override
    public String toString() {
        return (month + "/" + day + "/" + year);
    }

    /**
     * Returns a date object that corresponds to today's date.
     * @return a date whose value is today's date.
     */
    public static Date getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        return new Date(cal);
    }

    /**
     * Creates a date given a string in format YYYY-MM-DD.
     * @param date the date formatted as YYYY-MM-DD.
     * @return a date corresponding to the string given, null if string wasn't in proper format.
     */
    public static Date getDate(String date) {
        String [] splitDate = date.split("-");
        int month, day, year;
        int monthIndex = 1, dayIndex = 2, yearIndex = 0;

        try {
            month = Integer.parseInt(splitDate[monthIndex]);
            day = Integer.parseInt(splitDate[dayIndex]);
            year = Integer.parseInt(splitDate[yearIndex]);
        }
        catch(NumberFormatException e) {
            return null;
        }

        return new Date(month, day, year);
    }

}
