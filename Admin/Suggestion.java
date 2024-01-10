package Admin;

import java.io.Serializable;

import Design.Highlights;
import Users.ComitteeMember;

public class Suggestion implements Serializable {
    
    private ComitteeMember comitteeMember; // the name of the comittee Member;
    private String suggestion;
    private boolean accepted = false;

    public Suggestion(String suggestion, ComitteeMember comitteeMember) {
        this.suggestion = suggestion;
        this.comitteeMember = comitteeMember;
    }

    public ComitteeMember getComitteeMember() {return comitteeMember;}
    public String getSuggestion() {return suggestion;}
    public boolean isAccepted() {return accepted;}

    public void accept() {accepted = true;}

    @Override
    public String toString() {

        String status = accepted ? Highlights.highlight("Accepted") : Highlights.err("Pending");

        return 
        Highlights.title(comitteeMember.getName()) + suggestion + "\n   " + 
        Highlights.highlight("Status: ") + status + "\n" + 
        "-".repeat(150);
    }
}
