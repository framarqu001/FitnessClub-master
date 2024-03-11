package fitnessclubgui.fitnessclub;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class contains information shared among all members of a Gym.
 * A member contains a profile, expiration date, and home studio. This class
 * is meant to be extended by specific member types Basic, Family, Premium.
 *
 * @author Francisco Marquez, Ryan Colling
 */
public class Member implements Comparable<Member> {
    public static final int MONTH_WRAP_VALUE = 13;

    private final Profile profile;
    private Date expire;
    private final Location homeStudio;
    private StringProperty membershipClasses = new SimpleStringProperty();

    /**
     * Initializes a Member object.
     * Classes that extend member can set their own expiration dates
     *
     * @param profile    Profile that holds a members person information
     * @param homeStudio Location that a memwer registers at
     */
    public Member(Profile profile, Location homeStudio) {
        this.profile = profile;
        this.expire = null; // change this
        this.homeStudio = homeStudio;
    }

    /**
     * This constructor is used to load members from file with set expiration date
     *
     * @param profile    Profile that holds a members person information
     * @param expire     Expiration date of a member's membership
     * @param homeStudio Location that a member registers at
     */
    public Member(Profile profile, Date expire, Location homeStudio) {
        this.profile = profile;
        this.expire = expire; // change this
        this.homeStudio = homeStudio;
    }

    /**
     * This constructor is used for quick comparisons of members within the member database
     *
     * @param profile Profile that holds a members person information
     */
    public Member(Profile profile) {
        this.profile = profile;
        this.expire = null; // change this
        this.homeStudio = null;
    }

    /**
     * Determines if a member's membership is active.
     *
     * @return True if active, false otherwise.
     */
    public boolean membershipStatus() {
        Date today = Date.todayDate();
        return expire.compareTo(today) != -1;
    }

    /**
     * Sets expiration date for member objects.
     *
     * @param membershipLength length in months of a membership
     */
    public void setExpire(int membershipLength) {
        this.expire = new Date(membershipLength);
    }

    /**
     * Overrides equal method. Two methods are equal if their profile are the same
     *
     * @param obj A member object to be compared to
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Member that)) {
            return false;
        }
        return (profile.compareTo(that.profile) == 0);
    }

    /**
     * Format: Fname LName Membership expired dd/mm/yyyy Home studio: homestudio
     *
     * @return A formatted String:
     */
    @Override
    public String toString() {

        String membership = membershipStatus() ? ", Membership expires " : ", Memerbship expired ";
        return profile.toStringFormatted() + membership + expire + " Home Studio: " +
                homeStudio;
    }

    /**
     * Compares Member objects by comparing their profiles. Not case-sensitive
     * Profiles compares names lexicographically.
     *
     * @param that the object to be compared.
     * @return 0 if items are equal.
     * -1 if this.profile < that.profile.
     * 1 if this.profile > that.profile.
     */
    @Override
    public int compareTo(Member that) {
        if (this.profile.compareTo(that.profile) == -1) return -1;
        if (this.profile.compareTo(that.profile) == 1) return 1;
        return 0;
    }

    /**
     * Decrements a Member's guest passes.
     * Method is meant to be overridden by subclasses
     */
    public void takeGuestPass() {

    }

    /**
     * Increments a Member's guest passes.
     * Method is meant to be overridden by subclasses.
     */
    public void returnGuestPass() {

    }

    /**
     * Determines if a member has a guest pass available.
     *
     * @return True if available, false otherwise.
     */
    public boolean hasGuestPass() {
        return false;
    }

    /**
     * Determines how much to bill a member for upcoming billing cycle.
     * Method is meant to be overridden by subclasses
     *
     * @return Amount owed next billing cylce.
     */
    public double getBill () {
        return 0.0;
    }

    /**
     * Returns the Members home studio.
     * @return The Members home studio
     */
    public Location getHomeStudio() {
        return homeStudio;
    }

    /**
     * Returns the Members home studio's county in a String.
     * @return The Members home studio's county in a String
     */
    public String getHomeCounty() {
        return homeStudio.getCountyString();
    }

    /**
     * Returns the Members profile.
     * @return The Members profile.
     */
    public Profile getMembersProfile() {
        return profile;
    }

    /**
     * Returns the Members profile.
     * @return The Members profile.
     */
    public Profile getProfile () {
        return profile;
    }

    /**
     * Returns the Date object of a Member's expiration date
     * @return The Date object of a Member's expiration date
     */
    public Date getExpire () {
        return expire;
    }

    /**
     * Returns a string that contains membership type information.
     * @return String that contains membership type information.
     */
    public String getMembershipClasses () {
        return membershipClasses.get();
    }

    /**
     * Return a string of the membershipClass Property used for table views.
     * @return String Property used for table views
     */
    public StringProperty membershipClassesProperty () {
        return membershipClasses;
    }

    /**
     * Sets the value of the membershipClass StringProperty
     * @param membershipClasses Sets the value of the StringProperty
     */
    public void setMembershipClasses (String membershipClasses) {
        this.membershipClasses.set(membershipClasses);
    }

    /**
     * Returns The Members profile date of birth.
     * @return The Members profile date of birth.
     */
    public Date getDob(){
        return profile.getDob();
    }


}
