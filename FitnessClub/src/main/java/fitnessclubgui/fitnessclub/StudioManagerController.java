package fitnessclubgui.fitnessclub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * User Interface for software that manages a fitness club.
 * Allows members to be added/removed from the database, for them or guest to attend/be removed from classes,
 * can display the list of members in the club and show the attendees for requested classes.
 * Is a GUI based desktop program.
 * @author Ashley Berlinski, Francisco Marquez
 */
public class StudioManagerController {
    private MemberList members = new MemberList();
    private Schedule schedule = new Schedule();
    private enum TabMode {REGISTER, ATTEND, SCHEDULE}
    private final String SCHEDULE_PATH = "src/main/resources/fitnessclubgui/fitnessclub/classSchedule.txt";
    private final String MEMBER_PATH = "src/main/resources/fitnessclubgui/fitnessclub/memberList.txt";

    @FXML
    private DatePicker dp_dob, dp_dobAttend;

    @FXML
    private CheckBox cb_guest;

    @FXML
    private RadioButton rb_basic, rb_family;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField tf_fName, tf_lName, tf_fNameAttend, tf_lNameAttend;

    @FXML
    private ChoiceBox<Location> cb_homeStudio;

    @FXML
    private ChoiceBox<FitnessClass> cb_fitnessClass;

    @FXML
    private TableView<Member> tv_members, tv_attendees;
    @FXML
    private TableColumn<Member, Profile> col_memProfile, col_schProfile;
    @FXML
    private TableColumn<Member, String> col_memType, col_schMemberType, col_memStudio, col_schStudio;
    @FXML
    private TableColumn<Member, Date> col_memExpiration, col_schExpiration, col_memDob, col_schDOB;
    @FXML
    private TableColumn<Member, Double> col_memFee, col_schFee;

    @FXML
    private TableView<FitnessClass> tv_classes;
    @FXML
    private TableColumn<FitnessClass, String> col_class, col_instructor, col_location, col_time;

    /**
     * Given a text file, loads the list of members to the member's list.
     * @param memberFile the file that is attempted to be loaded.
     * @return true if the members list was successfully loaded, false if otherwise.
     */
    private boolean loadMemberFile(File memberFile) {
        try {
            members.load(memberFile);
        }
        catch (IOException exception) {
            textArea.appendText("Failed to load File: " + memberFile.getAbsolutePath() + "\n");
            return false;
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException exception) {
            textArea.appendText("File of improper format, could not load from: "
                    + memberFile.getAbsolutePath() + "\n");
            return false;
        }
        catch(NullPointerException exception) {
            textArea.appendText("Please select a file to load member list.\n");
            return false;
        }
        return true;
    }

    /**
     * Given a text file, loads the list of fitness classes to the schedule.
     * @param scheduleFile the file that is attempted to be loaded.
     * @return true if the schedule was successfully loaded, false if otherwise.
     */
    private boolean loadSchedule(File scheduleFile) {
        try {
            schedule = new Schedule();
            schedule.load(scheduleFile);
            FitnessClass [] classes = schedule.getFitnessClasses();

            ObservableList<FitnessClass> scheduleList = FXCollections.observableArrayList(classes);


            tv_classes.setItems(scheduleList);
            cb_fitnessClass.setItems(scheduleList);
        }
        catch(IOException e) {
            textArea.appendText("Could not load schedule.\n");
            return false;
        }
        catch(ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException exception) {
            textArea.appendText("Schedule data corrupted or missing, could not load.\n");
            return false;
        }
        return true;
    }

    /**
     * Helper method, initializes columns in schedule tableview/choicebox and loads the contents of the
     * schedule from the resources' folder. Will print error messages to console if file could not be
     * read or located.
     */
    private void initializeSchedule() {
        col_instructor.setCellValueFactory(new PropertyValueFactory<>("instructorString"));
        col_class.setCellValueFactory(new PropertyValueFactory<>("classString"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("studioString"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        loadSchedule(new File(SCHEDULE_PATH));
    }

    /**
     * Helper method, initializes columns in members tableview and loads the contents of the
     * members list from the resources folder. Will print error messages to console if file could not be
     * read or located.
     */
    private void initializeMembersTable() {
        col_memProfile.setCellValueFactory(new PropertyValueFactory<>("profile"));
        col_memExpiration.setCellValueFactory(new PropertyValueFactory<>("expire"));
        col_memStudio.setCellValueFactory(new PropertyValueFactory<>("homeCounty"));
        col_memDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        col_memType.setCellValueFactory(cellData -> cellData.getValue().membershipClassesProperty());
        col_memFee.setCellValueFactory(new PropertyValueFactory<>("bill"));
        loadMemberFile(new File(MEMBER_PATH));
        tv_members.setItems(members.getMembers());
    }

    /**
     * Helper method, initializes columns in schedule attendees tableview.
     */
    private void initializeAttendeeTable() {
        col_schProfile.setCellValueFactory(new PropertyValueFactory<>("profile"));
        col_schExpiration.setCellValueFactory(new PropertyValueFactory<>("expire"));
        col_schStudio.setCellValueFactory(new PropertyValueFactory<>("homeCounty"));
        col_schDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        col_schMemberType.setCellValueFactory(cellData -> cellData.getValue().membershipClassesProperty());
        col_schFee.setCellValueFactory(new PropertyValueFactory<>("bill"));
    }

    /**
     * Copies the information from the textfields and tableview from the Attendance/Registration tab to the other tab.
     * @param currentTab flag used to determine which tab the user had recently input data from.
     */
    private void syncTextBoxes(TabMode currentTab) {
        if(currentTab == TabMode.REGISTER) {
            tf_fNameAttend.setText(tf_fName.getText());
            tf_lNameAttend.setText(tf_lName.getText());
            dp_dobAttend.setValue(dp_dob.getValue());
        }
        else {
            tf_fName.setText(tf_fNameAttend.getText());
            tf_lName.setText(tf_lNameAttend.getText());
            dp_dob.setValue(dp_dobAttend.getValue());
        }
    }

    /**
     * Attempts to create a profile from the textfields and date picker.
     * Will return null if there is missing data or the DOB was invalid.
     * @param mode flag used to determine which tab the user is on.
     * @return a profile if the input was valid, null otherwise.
     */
    private Profile getProfile(TabMode mode) {

        String fName = (mode == TabMode.REGISTER) ? tf_fName.getText() : tf_fNameAttend.getText().trim();
        String lName = (mode == TabMode.REGISTER) ? tf_lName.getText() : tf_lNameAttend.getText().trim();
        if(fName.trim().isEmpty() || lName.trim().isEmpty()) {
            textArea.appendText("Please enter a first and last name.\n");
            return null;
        }

        Date dob;
        try {
            dob = (mode == TabMode.REGISTER) ? Date.getDate(dp_dob.getValue().toString()) :
                    Date.getDate(dp_dobAttend.getValue().toString());
        }
        catch (NullPointerException e) {
            textArea.appendText("Please enter a date of birth.\n");
            return null;
        }

        int minYear = 1900, maxYear = Date.getTodaysDate().getYear();
        if(dob.getYear() < minYear || dob.getYear() >= maxYear) {
            textArea.appendText(dob.toString() + " is not a valid DOB.\n");
            return null;
        }

        return new Profile(fName, lName, dob);
    }

    /**
     * Returns the corresponding member from the members list given the input from the textfields and datepicker.
     * @param mode flag used to determine which tab the user is on.
     * @return the member corresponding to the information entered, null if member was not in list.
     */
    private Member getMember(TabMode mode) {
        Profile profile = getProfile(mode);
        if(profile == null)
            return null;

        Member member = members.getMember(new Member(profile));
        if(member == null) {
            textArea.appendText(profile.toStringFormatted() + " is not registered" +
                    " as a member\n");
            syncTextBoxes(mode);
            return null;
        }
        return member;
    }

    /**
     * Returns the fitness class that was selected from either the schedule tableview or choicebox.
     * @param mode flag used to determine which tab the user is on.
     * @return the fitness class the user selected, null if user has not selected any option.
     */
    private FitnessClass getFitnessClass(TabMode mode) {
        FitnessClass fitnessClass = (mode == TabMode.ATTEND) ? tv_classes.getSelectionModel().getSelectedItem() :
                cb_fitnessClass.getSelectionModel().getSelectedItem();
        if(fitnessClass == null) {
            textArea.appendText("Please select a fitness class.\n");
            return null;
        }

        return fitnessClass;
    }

    /**
     * Creates a new member given a profile, location and the current membership type radiobutton that is selected.
     * @param profile the profile of the new member.
     * @param homeStudio the home studio of the new member.
     * @return a new member corresponding to the information provided on the registration tab.
     */
    private Member createMember(Profile profile, Location homeStudio) {
        if(rb_basic.isSelected())
            return new Basic(profile, homeStudio);
        else if(rb_family.isSelected())
            return new Family(profile, homeStudio);
        else
            return new Premium(profile, homeStudio);
    }

    /**
     * Fills and initializes all choiceboxes and tableviews in the GUI.
     */
    public void initialize() {
        final int YEAR_OFFSET = 18;
        cb_homeStudio.setItems(FXCollections.observableArrayList(Location.values()));
        initializeMembersTable();
        initializeSchedule();
        initializeAttendeeTable();
        dp_dob.setValue(LocalDate.now().minusYears(YEAR_OFFSET));
        dp_dobAttend.setValue(LocalDate.now().minusYears(YEAR_OFFSET));
        tv_members.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            String fName = newV.getProfile().getFname();
            String lName = newV.getProfile().getLname();
            int day = newV.getDob().getDay();
            int month = newV.getDob().getMonth();
            int year = newV.getDob().getYear();
            dp_dobAttend.setValue(LocalDate.of(year, month, day));
            tf_fNameAttend.setText(fName);
            tf_lNameAttend.setText(lName);
            syncTextBoxes(TabMode.ATTEND);
        });
    }

    /**
     * Registers a new member to the members list.
     * Will reject invalid input, members under 18 and will not add a member if they are already registered.
     */
    @FXML
    public void register() {
        Profile profile = getProfile(TabMode.REGISTER);
        if(profile == null)
            return;

        if(!(profile.getDob().isAdult())) {
            textArea.appendText("DOB " + profile.getDob() + ": must be 18 or older to join!\n");
            return;
        }

        Location homeStudio = cb_homeStudio.getValue();
        if(homeStudio == null) {
            textArea.appendText("Please select a Home Studio.\n");
            return;
        }

        Member member = createMember(profile, homeStudio);
        if (members.add(member)) {
            textArea.appendText("Successfully Registered: \n" + member + "\n");
        }
        else
            textArea.appendText(profile.toStringFormatted() + " already registered as member.\n");
        syncTextBoxes(TabMode.REGISTER);
    }

    /**
     * Removes a member from the member list, does nothing if member was not registered.
     */
    @FXML
    public void remove() {
        Profile profile = getProfile(TabMode.REGISTER);
        if(profile == null)
            return;

        if(members.remove(new Member(profile))) {
            textArea.appendText(profile.toStringFormatted() + " unregistered as a member.\n");
            schedule.removeMemberFromAllClasses(new Member(profile));
        }
        else
            textArea.appendText(profile.toStringFormatted() + " is not registered as a member.\n");
    }

    /**
     * Checks the status of a given member's membership.
     */
    @FXML
    public void check() {
        Profile profile = getProfile(TabMode.REGISTER);
        if(profile == null)
            return;

        Member member = members.getMember(new Member(profile));
        if(member != null)
            textArea.appendText("Membership Status:\n" + member + "\n");
        else
            textArea.appendText(profile.toStringFormatted() + " is not registered as a member.\n");
        syncTextBoxes(TabMode.REGISTER);
    }

    /**
     * Prompts the user to select a text file to load new members to the current member list.
     */
    @FXML
    private void loadMembers(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File memberFile = chooser.showOpenDialog(stage);

        if(loadMemberFile(memberFile)) {
            textArea.appendText("Members loaded.\n");
        }
    }

    /**
     * Prompts user to load a new class schedule given a properly formatted text file.
     */
    @FXML
    private void loadSchedule(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File scheduleFile = chooser.showOpenDialog(stage);

        if(loadSchedule(scheduleFile)) {
            textArea.appendText("Schedule loaded.\n");
        }

        tv_attendees.setItems(FXCollections.observableArrayList());
    }

    /**
     * Records the attendance of a member/guest given their information and a selected class.
     * Will not add members/guest to list if there is a time conflict, location conflict, if they are already
     * in the class or if they have no guest passes.
     */
    @FXML
    public void attend() {
        FitnessClass fitnessClass = getFitnessClass(TabMode.ATTEND);
        if(fitnessClass == null)
            return;

        Member member = getMember(TabMode.ATTEND);
        if(member == null)
            return;

        try {
            if(cb_guest.isSelected()) {
                if(schedule.addGuestToClass(member, fitnessClass))
                    textArea.appendText(member.getProfile().toStringFormatted() +
                        " (Guest) attending " + fitnessClass + "\n");
                else
                    textArea.appendText(member.getProfile().toStringFormatted() +
                            " (Guest) is already attending " + fitnessClass + "\n");
            }
            else {
                schedule.addMemberToClass(member, fitnessClass);
                textArea.appendText(member.getProfile().toStringFormatted() + " attending " + fitnessClass + "\n");
            }
        }
        catch(IllegalArgumentException exception) {
            textArea.appendText(exception.getMessage() + "\n");
        }
        syncTextBoxes(TabMode.ATTEND);
    }

    /**
     * Drops a member/guest from the currently selected class, adds back a guest pass if applicable.
     * Will fail if member/guest is not attending the given class.
     */
    @FXML
    public void drop() {
        FitnessClass fitnessClass = getFitnessClass(TabMode.ATTEND);
        if(fitnessClass == null)
            return;

        Member member = getMember(TabMode.ATTEND);
        if(member == null)
            return;

        if (cb_guest.isSelected()) {
            if(fitnessClass.getGuestList().remove(member)) {
                textArea.appendText(member.getProfile().toStringFormatted() +
                        " (Guest) removed from " + fitnessClass + ".\n");
                member.returnGuestPass();
            }
            else
                textArea.appendText(member.getProfile().toStringFormatted() +
                        " (Guest) is not attending " + fitnessClass + ".\n");
        }
        else {
            if(fitnessClass.getMemberList().remove(member))
                textArea.appendText(member.getProfile().toStringFormatted() +
                        " removed from " + fitnessClass + ".\n");
            else
                textArea.appendText(member.getProfile().toStringFormatted() +
                        " is not attending " + fitnessClass + ".\n");
        }
    }

    /**
     * Given a selected class on the schedule choicebox, will display all the members attending that class.
     */
    @FXML
    public void displayClassMemberList() {
        FitnessClass fitnessClass = getFitnessClass(TabMode.SCHEDULE);
        if(fitnessClass == null)
            return;
        ObservableList<Member> memberList = fitnessClass.getMemberList().getMembers();
        if(memberList == null || memberList.isEmpty()) {
            textArea.appendText(fitnessClass + " has no attendees.\n");
            tv_attendees.setItems(FXCollections.observableArrayList());
        }
        else {
            tv_attendees.setItems(memberList);
            textArea.appendText(fitnessClass + " members loaded.\n");
        }
    }

    /**
     * Given a selected class on the schedule choicebox, will display all the guests attending that class.
     */
    @FXML
    public void displayClassGuestList() {
        FitnessClass fitnessClass = getFitnessClass(TabMode.SCHEDULE);
        if(fitnessClass == null)
            return;
        ObservableList<Member> guestList = fitnessClass.getGuestList().getMembers();
        if(guestList == null || guestList.isEmpty()) {
            textArea.appendText(fitnessClass + " has no guest attendees.\n");
            tv_attendees.setItems(FXCollections.observableArrayList());
        }
        else {
            tv_attendees.setItems(guestList);
            textArea.appendText(fitnessClass + " guests loaded.\n");
        }
    }
}
