package fitnessclubgui.fitnessclub;


/**
 * This class holds the information of a profile for a member
 * The profile class includes the first name, last name,
 * and date of birth for the member
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Initalizes a Profile object
     *
     * @param fname First name of the member
     * @param lname Last name of the member
     * @param dob Date of birth of the member
     */
    public Profile (String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Compares profile objects by comparing names lexographically
     * Names are not case-sensitive
     * DOB is used as a tie-breaker
     *
     * @param that the object to be compared.
     * @return 0 if the objects are equal
     * -1 if this.profile less than that.profile
     *  1 if this.profile greater than that.profile
     */
    @Override
    public int compareTo(Profile that) {
        if (this.lname.toLowerCase().compareTo(that.lname.toLowerCase()) > 0) return 1;
        if (this.lname.toLowerCase().compareTo(that.lname.toLowerCase()) < 0) return -1;
        if (this.fname.toLowerCase().compareTo(that.fname.toLowerCase()) > 0) return 1;
        if (this.fname.toLowerCase().compareTo(that.fname.toLowerCase()) < 0) return -1;
        if (this.dob.compareTo(that.dob) == 1) return 1;
        if (this.dob.compareTo(that.dob) == -1) return -1;
        else return 0;
    }

    /**
     * Returns the date of birth of the profile.
     * @return The date of birth of the profile
     */
    public Date getDob () {
        return dob;
    }

    /**
     * Compares two profile objects to see if equal
     * Names not case-sensitive
     *
     * @param obj The profile being compared
     * @return True if equal, false otherwise
     */
    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof Profile that)) {
            return false;
        }
        return this.compareTo(that) == 0;
    }

    /**
     * Returns a string of the profile's first and last name.
     * @return A string with the format: First name Last name
     */
    @Override
    public String toString () {
        return fname + " " + lname;
    }

    /**
     * Returns a string of the profile's first and last name as well as the DOB.
     * @return A properly formatted string with: First name last name DOB
     */
    public String toStringFormatted() {
        return fname + ":" + lname + " " + dob;
    }

    /**
     * Returns the first name of the profile.
     * @return The first name of the profile.
     */
    public String getFname () {
        return fname;
    }

    /**
     * Returns the last name of the profile.
     * @return The last name of the profile.
     */
    public String getLname () {
        return lname;
    }
}
