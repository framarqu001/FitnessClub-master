package fitnessclubgui.fitnessclub;

/**
 * Time class represents the different times a fitness class can occur
 * Morning, Afternoon, or Evening
 *
 * @author Francisco Marquez
 */
public enum Time {
    MORNING (9, 30),
    AFTERNOON (14, 0),
    EVENING (18, 30);

    final int startHour;
    final int minutes;

    /**
     * Initalizes a time
     *
     * @param startHour The hour time the class starts
     * @param minutes The minute time the class starts
     */
    Time (int startHour, int minutes) {
        this.startHour = startHour;
        this.minutes = minutes;
    }

    /**
     * Takes string inputs and sets them to Time variable
     *
     * @param input The time being inputted
     * @return The time from the input
     */
    static public Time setTime(String input) {
        if (input.toLowerCase().equals("morning")) return MORNING;
        if (input.toLowerCase().equals("afternoon")) return AFTERNOON;
        if (input.toLowerCase().equals("evening")) return EVENING;
        return null;
    }

    /**
     * Returns a string of the format HH:MM for the corresponding time.
     * @return A string with the proper hour and minutes format
     */
    @Override
    public String toString() {
        return startHour + ":" + ((minutes == 0) ? "00": "30");
    }

}