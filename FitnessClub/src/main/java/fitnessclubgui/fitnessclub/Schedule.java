package fitnessclubgui.fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Schedule holds an array of fitness classes.
 * Members and their guest can be added and removed from the
 * schedule of individual fitness classes.
 */
public class Schedule {
    static final int INITIAL_CAPACITY = 15;
    private FitnessClass [] classes;
    private int numClasses;

    /**
     * Initializes an empty schedule.
     */
    public Schedule() {
        this.classes = new FitnessClass[INITIAL_CAPACITY];
        numClasses = 0;
    }


    /**
     * Adds a member to a fitness class
     * Fails if time conflict,
     * Member type does not have access,
     * Member is already in the class.
     *
     * @param member the member to be added
     * @param fitnessClass The classes to be added to
     * @return True if successfully adds member.
     */
    public boolean addMemberToClass(Member member, FitnessClass fitnessClass){

        if (member instanceof Basic && (fitnessClass.getStudio() != member.getHomeStudio())) {
            throw new IllegalArgumentException(member.getMembersProfile() + " is attending a class at " +
                    fitnessClass.getStudio().name() + " - [BASIC] home studio at " + member.getHomeStudio().name() +
                    ". Could not add to class.");
        }

        MemberList classMembers = fitnessClass.getMemberList();
        if (classMembers.contains(member)){
            throw new IllegalArgumentException(member.getMembersProfile() + " is already in the class");
        }

        Time classTime = fitnessClass.getTime();
        for (int i = 0; i < classes.length; i++) {
            FitnessClass currentClass = classes[i];
            MemberList  currentClassMemberList = currentClass.getMemberList();
            if (currentClassMemberList.contains(member) && currentClass.getTime() == classTime) {
                throw new IllegalArgumentException("Time conflict - " +  member.getMembersProfile() + " is in another class" +
                        " held at " + currentClass.getTime() + " - " + fitnessClass);
            }
        }
        if (member instanceof Basic){
            ((Basic) member).incrementNumClasses();
        }
        fitnessClass.addMember(member);

        return true;
    }

    /**
     * Adds A member's guest to a fitness class
     * Fails if member has no guest pass,
     * Membership type does not allow guest pass.
     *
     * @param member the member whose guest is to be added
     * @param fitnessClass The class to be added to.
     * @return True if successfully adds member.
     */
    public boolean addGuestToClass(Member member, FitnessClass fitnessClass) {

        if (member instanceof Basic) {
            throw new IllegalArgumentException(member.getMembersProfile() + " [Basic] - no guest pass.");
        }
        if (!(member.hasGuestPass())){
            throw new IllegalArgumentException(member.getMembersProfile() + " guest pass is not available.");
        }
        if (member.getHomeStudio() != fitnessClass.getStudio()){
            throw new IllegalArgumentException(member.getMembersProfile() + " (guest) is attending a class at " +
                    fitnessClass.getStudio().name() + " - home studio at " + member.getHomeStudio().name() +
                    ". Could not add to class.");
        }
        MemberList classMembers = fitnessClass.getGuestList();
        if (classMembers.contains(member)){
            return false;
        }

        member.takeGuestPass();
        fitnessClass.addGuest(member);
        return true;
    }

    /**
     * Removes member from a fitness class on the schedule.
     *
     * @param member Member to be removed.
     * @param fitnessClass Class to be removed from.
     * @return true if successfully removed.
     */
    public boolean removeMemberFromSchedule(Member member, FitnessClass fitnessClass) {
        if (!fitnessClass.getMemberList().remove(member)){
            return false;
        }
        return true;
    }

    /**
     * Removes a member's guest from a fitness class on the schedule.
     *
     * @param guest Member's guest to be removed.
     * @param fitnessClass Class to be removed from.
     * @return true if successfully removed.
     */
    public boolean removeGuestFromSchedule(Member guest, FitnessClass fitnessClass) {
        if (!fitnessClass.getGuestList().remove(guest)){
            return false;
        }
        guest.returnGuestPass();
        return true;
    }

    /**
     * Given a member, removes them and their guests from all fitness classes in the schedule.
     * @param member the member that is being removed from all classes.
     */
    public void removeMemberFromAllClasses(Member member) {
        for (FitnessClass fitnessClass: classes) {
            fitnessClass.getMemberList().remove(member);
            fitnessClass.getGuestList().remove(member);
        }
    }

    /**
     * Gets a class on the schedule.
     * @param fitnessClass Fitness class to be searched for.
     * @return Fitness class if found, null otherwise.
     */
    public FitnessClass getFitnessClass(FitnessClass fitnessClass) {
        int found = find(fitnessClass);
        if (found == -1) return null;
        return classes[found];
    }

    /**
     * Gets the array of classes from the schedule
     * @return the list on classes on the schedule
     */
    public FitnessClass [] getFitnessClasses() {
        return classes;
    }

    /**
     * Helper method to find the index of a fitness class
     * @param fitnessClass Fitness class to be searched fr.
     * @return Index of class if found, -1 otherise.
     */
    private int find(FitnessClass fitnessClass) { //profiles ID members
        final int NOT_FOUND = -1;
        for (int i = 0; i < classes.length; i++) {
            if (classes[i].equals(fitnessClass)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Loads a schedule of fitness classes from a file.
     * The schedule is then printed out before Studio Manger runs.
     * @param file Text file containg schedule
     * @throws IOException Exception to handled by studio manager if file cannot be opened.
     */
    public void load(File file) throws IOException {
        final int OFFER = 0;
        final int INSTRUCTOR = 1;
        final int TIME = 2;
        final int LOCATION = 3;
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokenArr = line.split(" ");

            if(tokenArr.length <= LOCATION)
                break;

            Offer classInfo = Offer.valueOf(tokenArr[OFFER].toUpperCase());
            Instructor instructor = Instructor.valueOf(tokenArr[INSTRUCTOR].toUpperCase());
            Location location  = Location.valueOf(tokenArr[LOCATION].toUpperCase());
            Time time = Time.setTime(tokenArr[TIME]);
            FitnessClass newClass = new FitnessClass(classInfo, instructor, location, time);
            classes[numClasses++] = newClass;
        }
    }

}
