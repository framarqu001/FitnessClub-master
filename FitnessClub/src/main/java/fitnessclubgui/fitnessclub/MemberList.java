package fitnessclubgui.fitnessclub;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The MemberList class represents a list of members in an array
 * The class updates and changes the array of members as needed
 *
 * @author Francisco Marquez,
 */
public class MemberList {

    private ObservableList<Member> members;
    private int size;

    /**
     * Initalizes a MemberList with an initial capacity of 4
     */
    public MemberList() {
        this.members = FXCollections.observableArrayList();
        this.size = 0;
    }

    /**
     * Returns the list of members as an observable list.
     * @return the list of members as an observable list.
     */
    public ObservableList<Member> getMembers () {
        return members;
    }

    /**
     * Searches the member array to see if a specific member is in the array
     *
     * @param member The member being searched for
     * @return True if member is found, false otherwise
     */
    public boolean contains(Member member) {
        return members.contains(member);
    }

    /**
     * Adds a member to the end of the list
     *
     * @param member The member being added
     * @return True if successfully added, false otherwise
     */
    public boolean add(Member member) {
        if (contains(member)) return false;
        if (!member.getMembersProfile().getDob().isValid()) return false;
        members.add(member);
        size++;
        return true;
    }

    /**
     * Removes a member from the list
     *
     * @param member The member being removed
     * @return True if successfully removed, false otherwise
     */
    public boolean remove(Member member) {
        if (!contains(member)) return false;
        members.remove(member);
        size--;
        return true;
    }

    /**
     * Determines if members list has no elements.
     * @return True if list is empty, false otherwise
     */
    public boolean isEmpty() {
        return members.isEmpty();
    }

    /**
     * Given a member, returns a corresponding member in the list if one exists.
     * @param member The member being searched for
     * @return The found member in the list
     */
    public Member getMember(Member member) {
        if (!contains(member)) return null;
        int found = members.indexOf(member);
        return members.get(found);
    }

    /**
     * Reads and breaks down the information in the given text file
     *
     * @param file The text file being read
     * @throws IOException Signals that an I/O exception has occurred
     */
    public String load(File file) throws IOException {//from the text file throws to STUDIO MANAGER
        final int MEMBERSHIP = 0;
        final int FNAME = 1;
        final int LNAME = 2;
        final int DOB = 3;
        final int EXPIRE = 4;
        final int COUNTY = 5;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokenArr = line.split(" ");
            Date dob = new Date(tokenArr[DOB]);
            Profile profile = new Profile(tokenArr[FNAME], tokenArr[LNAME], dob);
            Location county = Location.valueOf(tokenArr[COUNTY]);
            Date expire = new Date(tokenArr[EXPIRE]);
            switch (tokenArr[MEMBERSHIP]) {
                case "B" -> add(new Basic(profile, expire, county));
                case "F" -> add(new Family(profile, expire, county));
                case "P" -> add(new Premium(profile, expire, county));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("list of members loaded\n");
        for (int i = 0; i < size; i++) {
            sb.append(members.get(i) + "\n\n");
        }
        sb.append("Fitness classes loaded\n\n");
        return sb.toString();
    }
}
