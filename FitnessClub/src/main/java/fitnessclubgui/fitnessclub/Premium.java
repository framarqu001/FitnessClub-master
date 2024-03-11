package fitnessclubgui.fitnessclub;

/**
 * Premium is an extension of the Member class and holds information of a premium membership
 * Uses logic to determine the dues, expiration date, number of guests and other benefits of a premium membership
 *
 * @author Francisco Marquez, Ryan Colling
 */
public class Premium extends Member {
    private static final int MAX_GUEST_PASS = 3;
    private static final int MEMBERSHIP_LENGTH = 13; // This is equal to 12 months.
    private int guestPass;

    /**
     * Initalizes a Premium Member object
     *
     * @param profile The first name, last name, and date of birth of the member
     * @param homeStudio The home studio of the member
     */
    public Premium(Profile profile, Location homeStudio) {
        super(profile, homeStudio);
        guestPass = MAX_GUEST_PASS;
        setExpire(MEMBERSHIP_LENGTH);
        String memberInfo = "(Premium) guest-pass remaining: " +
                (membershipStatus() ? (guestPass) : "Not elgible");
        setMembershipClasses(memberInfo);
    }

    /**
     * Initalizes a Premium Member object
     *
     * @param profile The first name, last name, and date of birth of the member
     * @param expire The date that the membership expires
     * @param homeStudio The home studio of the member
     */
    public Premium(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.guestPass = MAX_GUEST_PASS;
        String memberInfo = "(Premium) guest-pass remaining: " +
                (membershipStatus() ? (guestPass) : "Not elgible");
        setMembershipClasses(memberInfo);

    }



    /**
     * @return True if a premium member has available guest passes, false otherwise
     */
    @Override
    public boolean hasGuestPass(){
        return (guestPass > 0);
    }

    /**
     * Gives a premium membership guest pass to a guest
     */
    @Override
    public void takeGuestPass() {
        guestPass--;
        String memberInfo = "(Premium) guest-pass remaining: " +
                (membershipStatus() ? (guestPass) : "Not elgible");
        setMembershipClasses(memberInfo);
    }

    /**
     * Returns the guest pass to the member
     */
    @Override
    public void returnGuestPass() {
        guestPass++;
        String memberInfo = "(Premium) guest-pass remaining: " +
                (membershipStatus() ? (guestPass) : "Not elgible");
        setMembershipClasses(memberInfo);
    }

    /**
     * @return The billed amount charged to the member
     */
    @Override
    public double getBill () {
        return 659.89;
    } //billed yearly with 1 free month at 59.99 a month

    /**
     * @return A string with member information, membership type, and number of classes attended
     */
    @Override
    public String toString() {
        return super.toString() + " (Premium) guest-pass remaining: " +
                (membershipStatus() ? (guestPass) : "Not elgible");
    }


}