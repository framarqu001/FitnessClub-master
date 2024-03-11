package fitnessclubgui.fitnessclub;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class contains information of a fitness class
 * A fitness class contains the type of class, the instructor,
 * the location, the time, and the attending members and guests
 *
 * @author Francisco Marquez
 */
public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;

    private SimpleStringProperty classString, instructorString, studioString, timeString;
    private MemberList members;
    private MemberList guest;

    /**
     * Initalizes a Fitness Class object
     *
     * @param classInfo  The type of class being offered, can be Pilates, Spinning, or Cardio
     * @param instructor The name of the instructor, can be Jennifer, Kim, Denise, Davis, or Emma
     * @param studio     The location of the fitness class
     * @param time       The time of the fitness class, can be morning, afternoon, or night
     */
    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guest = new MemberList();
        this.classString = new SimpleStringProperty(this.classInfo.toString());
        this.instructorString = new SimpleStringProperty(this.instructor.toString());
        this.studioString = new SimpleStringProperty(this.studio.toString());
        this.timeString = new SimpleStringProperty(this.time.toString());
    }

    /**
     * Initalizes a Fitness Class object
     *
     * @param classinfo  The type of class being offered, can be Pilates, Spinning, or Cardio
     * @param instructor The name of the instructor, can be Jennifer, Kim, Denise, Davis, or Emma
     * @param studio     The location of the fitness class
     */
    public FitnessClass(Offer classinfo, Instructor instructor, Location studio) {
        this.classInfo = classinfo;
        this.instructor = instructor;
        this.studio = studio;
    }

    /**
     * Adds a member to the fitness class
     *
     * @param member The member being added to the class
     * @return True if successfully added, false otherwise
     */
    public boolean addMember(Member member) {
        return members.add(member);
    }

    /**
     * Adds a guest to the fitness class
     *
     * @param member The guest being added to the
     * @return True if successfully added, false otherwise
     */
    public boolean addGuest(Member member) {
        return guest.add(member);
    }

    /**
     * Returns the location of the fitness class.
     * @return The location of the fitness class
     */
    public Location getStudio() {
        return studio;
    }

    /**
     * Returns the list of members attending the fitness class
     * @return The list of members attending the fitness class
     */
    public MemberList getMemberList() {
        return members;
    }

    /**
     * Returns the list of guests attending the fitness class
     * @return The list of guests attending the fitness class
     */
    public MemberList getGuestList() {
        return guest;
    }

    /**
     * Returns the time of the fitness class
     * @return The time of the fitness class
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns the instructor of the fitness class
     * @return The instructor of the fitness class
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Returns the type fitness class
     * @return The type fitness class
     */
    public Offer getClassInfo() {
        return classInfo;
    }

    /**
     * Gets the string associated with classString, used for TableView.
     * @return the string associated with classString.
     */
    public String getClassString() {
        return classString.get();
    }

    /**
     * Gets simple string property object for classInfo, used for TableView.
     * @return the simpleStringProperty Object associated with classInfo.
     */
    public SimpleStringProperty classStringProperty() {
        return classString;
    }

    /**
     * Gets the string associated with instructorString, used for TableView.
     * @return the string associated with instructorString.
     */
    public String getInstructorString() {
        return instructorString.get();
    }

    /**
     * Gets simple string property object for instructor, used for TableView.
     * @return the simpleStringProperty Object associated with instructor.
     */
    public SimpleStringProperty instructorStringProperty() {
        return instructorString;
    }

    /**
     * Gets the string associated with studioString, used for TableView.
     * @return the string associated with studioString.
     */
    public String getStudioString() {
        return studioString.get();
    }

    /**
     * Gets simple string property object for studio, used for TableView.
     * @return the simpleStringProperty Object associated with studio.
     */
    public SimpleStringProperty studioStringProperty() {
        return studioString;
    }

    /**
     * Gets the string associated with timeString, used for TableView.
     * @return the string associated with timeString.
     */
    public String getTimeString() {
        return timeString.get();
    }

    /**
     * Gets simple string property object for time, used for TableView.
     * @return the simpleStringProperty Object associated with time.
     */
    public SimpleStringProperty timeStringProperty() {
        return timeString;
    }

    /**
     * Sets classString given a string, used for TableView
     * @param classString the string given.
     */
    public void setClassString(String classString) {
        this.classInfo = Offer.valueOf(classString);
        this.classString.set(classString);
    }

    /**
     * Sets instructorString given a string, used for TableView
     * @param instructorString the string given.
     */
    public void setInstructorString(String instructorString) {
        this.instructor = Instructor.valueOf(instructorString);
        this.instructorString.set(instructorString);
    }

    /**
     * Sets studioString given a string, used for TableView
     * @param studioString the string given.
     */
    public void setStudioString(String studioString) {
        this.studio = Location.valueOf(studioString);
        this.studioString.set(studioString);
    }

    /**
     * Sets timeString given a string, used for TableView
     * @param timeString the string given.
     */
    public void setTimeString(String timeString) {
        this.time = Time.setTime(timeString);
        this.timeString.set(timeString);
    }

    /**
     * Uses logic to determine if two fitness classes are equal to each other
     *
     * @param obj The fitness class being compared
     * @return True if classes are equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FitnessClass that)) {
            return false;
        }
        if (!(this.classInfo == that.classInfo)) return false;
        if (!(this.instructor == that.instructor)) return false;
        if (!(this.studio == that.studio)) return false;
        return true;
    }

    /**
     * Returns A string with the fitness class info, instructor, time, and location
     * @return A string with the fitness class info, instructor, time, and location
     */
    @Override
    public String toString() {
        return classInfo + " - " + instructor + ", " + time + ", " + studio.name();
    }

}
