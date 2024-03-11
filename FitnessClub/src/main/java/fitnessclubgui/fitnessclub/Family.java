package fitnessclubgui.fitnessclub;

/**
 *  Family is an extension of the Member class and holds information of a family membership
 *  Uses logic to determine the dues, expiration date, number of guests, and other benefits of a family membership
 *
 * @author Francisco Marquez, Ryan Colling
 */
public class Family extends Member {
    private static int MEMBERSHIP_LENGTH = 3;
    private boolean guest;

    /**
     * Initalizes a Family Member object
     *
     * @param profile The first name, last name, and date of birth of the member
     * @param homeStudio The home studio of the member
     */
    public Family(Profile profile, Location homeStudio) {
        super(profile, homeStudio);
        setExpire(MEMBERSHIP_LENGTH);
        guest = false;
        String memberInfo = "(Family)" + " guest-pass remaining: " +
                (membershipStatus() ? (guest ? "0" : "1") : "Not eligible");
        setMembershipClasses(memberInfo);

    }

    /**
     * Initalizes a Family Member object
     *
     * @param profile The first name, last name, and date of birth of the member
     * @param expire The date that the membership expires
     * @param homeStudio The home studio of the member
     */
    public Family(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.guest = false;
        String memberInfo = "(Family)" + " guest-pass remaining: " +
                (membershipStatus() ? (guest ? "0" : "1") : "Not eligible");
        setMembershipClasses(memberInfo);
    }


    /**
     * Gives a family membership guest pass to a guest
     */
    @Override
    public void takeGuestPass(){
        guest = true;
        String memberInfo = "(Family)" + " guest-pass remaining: " +
                (membershipStatus() ? (guest ? "0" : "1") : "Not eligible");
        setMembershipClasses(memberInfo);
    }

    /**
     * @return True if a family membership has an available guest pass, false otherwise
     */
    @Override
    public boolean hasGuestPass(){
        return !guest;
    }

    /**
     * Returns the guest pass to the member
     */
    @Override
    public void returnGuestPass(){
        guest = false;
        String memberInfo = "(Family)" + " guest-pass remaining: " +
                (membershipStatus() ? (guest ? "0" : "1") : "Not eligible");
        setMembershipClasses(memberInfo);
    }

    /**
     * @return The billed amount charged to the member
     */
    @Override
    public double getBill () {
        return 149.97;  //billed every 3 months 49.99 a month
    }

    /**
     * @return A string with member information, membership type, and number of classes attended
     */
    @Override
    public String toString() {
        return super.toString() + " (Family)" + " guest-pass remaining: " +
                (membershipStatus() ? (guest ? "0" : "1") : "Not eligible");
    }


}
