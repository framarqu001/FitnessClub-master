package fitnessclubgui.fitnessclub;

/**
 * Basic is an extension of the Member class and holds information of a basic membership
 * Uses logic to determine the dues, expiration date, and other limitations of a basic membership
 *
 * @author Francisco Marquez, Ryan Colling
 */
public class Basic extends Member {
    private static int MEMBERSHIP_LENGTH = 1;
    private int numClasses;


    /**
     * Initializes a Basic Member object
     *
     * @param profile The first name, last name, and date of birth of the member
     * @param homeStudio The home studio of the member
     */
    public Basic(Profile profile, Location homeStudio) {
        super(profile, homeStudio);
        setExpire(MEMBERSHIP_LENGTH);
        setMembershipClasses("(Basic)" + " number of classes attended: " + numClasses);
        numClasses = 0;
    }

    /**
     * Initializes a Basic Member object
     *
     * @param profile The first name, last name, and date of birth of the member
     * @param expire The date that the membership expires
     * @param homeStudio The home studio of the member
     */
    public Basic(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.numClasses = 0;
        setMembershipClasses("(Basic)" + " number of classes attended: " + numClasses);
    }


    /**
     * Increments the number of classes a member has attended
     */
    public void incrementNumClasses() {
        numClasses += 1;
        setMembershipClasses("(Basic)" + " number of classes attended: " + numClasses);
    }

    /**
     * Uses logic to determine the billed amount of a basic membership
     * Additional charge to bill if more than 4 classes are attended
     * @return The billed amount charged to the member
     */
    @Override
    public double getBill () {
        if (numClasses > 4) {
            return 39.99 + (10 * (numClasses - 4));
        }
        return 39.99;
    } //billed monthly at 39.99

    /**
     * @return A string with member information, membership type, and number of classes attended
     */
    @Override
    public String toString() {
        return super.toString() + " (Basic)" + " number of classes attended: " + numClasses;
    }



}
