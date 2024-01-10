package Users;

import Design.Highlights;
import Camps.Camp;

public class ComitteeMember extends Student {
    
    private Camp camp;
    private int points = 0;
    private int suggestionsMade = 0;
    private int enquiriesRepliedToo = 0;
    
    private boolean hasCamp = false;

    public ComitteeMember(Student s) {
        super(s.getName(), s.getEmail(), s.getFaculty());
        setComitteeMember(true);
        setPassword(s.getPassword());
        copyRegisteredCamps(s.getMyCamps());
    }

    public Camp getCamp() {return camp;}
    public int getPoints() {return points;}

    public boolean doesHaveCamp() {return hasCamp;}

    public void setCamp(Camp camp) {
        this.camp = camp;
        hasCamp = true;
    }

    public void incrementPoints() {points++;}

    public void incrementEnquiriesAnswered() {enquiriesRepliedToo++;}

    public void incrementSuggestionsMade() {suggestionsMade++;}

    @Override
    public String toString() {
        String temp = Boolean.toString(isComitteeMember()).toUpperCase();
        String comitteeMemberStatus = Highlights.bold(temp);
        
        return 
        Highlights.highlight("Name: ") + String.format("%-15s",getName()) + 
        Highlights.highlight("Email: ") + String.format("%-15s",getEmail()) + 
        Highlights.highlight("Faculty: ") + String.format("%-15s",getFaculty()) +
        Highlights.highlight("Camp Comittee: ") + " " + 
        String.format("%-15s",comitteeMemberStatus) + "\n" +    
        Highlights.title("Points") + points + "\n" + 
        "-".repeat(150);
    }

    public void viewDetails() {

        String campName = hasCamp == false ? "No camp" : camp.getCampInfo().getName();

        String details = 
        Highlights.title("Camp") + campName + 
        Highlights.title("\tPoints") + points;

        System.out.println(details);
    }

    public String reportView() {
        return 
        super.attendieReportView() + 
        Highlights.title("Points") + points;
    }

    public String reportString() {
        return 
        super.attendieReportString() + 
        "Points: " + points;
    }

    @Override 
    public void removeCamp(Camp camp) {
        super.removeCamp(camp);

        this.camp = null; // camp members cannot become comitteeMembers in any other camp if this happens
        this.hasCamp = false;
    }
}
