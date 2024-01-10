package Admin;
import java.io.Serializable;

import Design.Highlights;

public class Enquiry implements Serializable {
    
    private final String question;
    private String reply = "No reply, yet";
    private boolean hasReply = false;
    private String comitteeMember;
    private String user;


    public Enquiry(String question, String user) {
        this.question = question;
        this.user = user;
    }
///////////////////////// Getters
    public String getQuestion() {return question;}
    public String getUser() {return user;}
    public String getReply() {return reply;}
    public boolean hasBeenAnswered() {return hasReply;}

//////////////////////// Setters
    public void setReply(String reply, String comitteeMember) {
        this.reply = reply;
        hasReply = true;
        this.comitteeMember = comitteeMember;
    }

    @Override
    public String toString() {

        String message = reply.equals("No reply, yet") ? "" : Highlights.title("Replied By") + comitteeMember;

        return 
        Highlights.title("Question") + question + "\n\n   " + 
        Highlights.title("Reply") + reply + "\n   " + 
        message + "\n" +
        "-".repeat(150);
    }

    public String reportView() {
        String message = reply.equals("No reply, yet") ? "" : "Replied By:" + comitteeMember;

        return 
        "Question:" + question + "\n\n" + 
        "Reply:" + reply + "\n   " + 
        message + "\n" +
        "-".repeat(150);
    }
}
