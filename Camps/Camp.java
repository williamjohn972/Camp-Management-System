package Camps;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import Users.Student;
import Users.Staff;
import Admin.Enquiry;
import Admin.Suggestion;
import Design.Highlights;

public class Camp implements Serializable {
    
    private CampInfo campInfo;
    private List<Student> members = new LinkedList<>();
    private List<Student> comitteeMembers = new LinkedList<>(); 
    private List<Enquiry> enquiries = new LinkedList<>();
    private List<Suggestion> suggestions = new LinkedList<>();
    private boolean visible = true; // every camp is visible to all students by default;

    private List<String> betrayers = new ArrayList<>();

//////////////////////// Constructor
    
    public Camp(String name, LocalDate startDate, LocalDate endDate, LocalDate registrationClosingDate,
                    String school, String location, String description,
                    int slots, int comitteeSlots, 
                    Staff staffIncharge) 
                {
                    campInfo =  new CampInfo(name, startDate, endDate, registrationClosingDate, 
                                            school, location, description, 
                                            slots, comitteeSlots, staffIncharge);
                }

    public Camp(String[] campInfo, Staff s) {
        this.campInfo = new CampInfo(campInfo, s);
    }

///////////////////////// Getters
    public CampInfo getCampInfo() {return campInfo;}
    public List<Student> getMembers() {return members;}
    public List<Student> getComitteeMembers() {return comitteeMembers;}
    public boolean isVisible() {return visible;}
    public List<Enquiry> getEnquiries() {return enquiries;}
    public List<Suggestion> getSuggestions() {return suggestions;}

    public List<String> getBetrayers() {return betrayers;}

///////////////////////// Setters
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addEnquiry(Enquiry e) {
        enquiries.add(e);
    }

    public void addSuggestions(Suggestion s) {
        suggestions.add(s);
    }

///////////////////////////// What can each camp do ?
    public void addMember(Student s) {
        members.add(s);
        campInfo.incrementOccupiedSlots();
    }

    public void addComitteeMember(Student s) {
        comitteeMembers.add(s);
        campInfo.incrementOccupiedComitteeSlots();
        campInfo.incrementOccupiedSlots();
    }

    public void removeMember(Student s) {
        int i = 0;

        for(i = 0; i < members.size(); ++i) {
            if(members.get(i).getUserID().equals(s.getUserID()))
                break;
        }

        members.remove(i);
        campInfo.decrementOccupiedSlots();
    }

    public void removeComitteeMember(Student s) {
        int i = 0;

        for(i = 0; i < comitteeMembers.size(); ++i) {
            if(comitteeMembers.get(i).getUserID().equals(s.getUserID()))
                break;
        }

        betrayers.add(comitteeMembers.remove(i).getName());
        campInfo.decrementOccupiedComitteeSlots();
        campInfo.decrementOccupiedSlots();
    }

///////////////////////////// Miscellanious;
    public void viewCampDetails() {System.out.println(campInfo.toString());}

    @Override 
    public String toString() {
        return 
        String.format("%-45s", campInfo.getName()) + 
        Highlights.title("Camp Adventure Span") + String.format("%-45s", campInfo.viewCampDuration()) + 
        Highlights.title("Register By") + campInfo.viewRegistrationClosingDate(); 
    }  

    public String briefView() {
        return 
        Highlights.title("Name") + String.format("%-45s", campInfo.getName()) + 
        Highlights.title("Camp Adventure Span") + String.format("%-45s", campInfo.viewCampDuration());
    }

    public String briefView2() {
            return
            Highlights.title("Name") + String.format("%-45s", campInfo.getName()) + 
            Highlights.title("Camp Adventure Span") + String.format("%-45s", campInfo.viewCampDuration());   
    }
}
