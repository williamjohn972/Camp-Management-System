package Users;
import java.util.List;
import java.util.LinkedList;

import Camps.Camp;
import Design.Highlights;

public class Student extends User {

    private boolean comitteeMember = false; // students are not comittee members by default;
    // note that students can only be a comittee member in one camp;

    private List<Camp> myCamps = new LinkedList<>();

    public Student(String name, String email, String faculty) {
        super(name, email, faculty);
    }

    public Student(String[] credentials) {
        super(credentials);
    }

///////////////// Getters;
    public boolean isComitteeMember() {return comitteeMember;}
    public List<Camp> getMyCamps() {return myCamps;} 

//////////////// Setters;
    public void setComitteeMember(boolean flag) {comitteeMember = flag;}

    protected void copyRegisteredCamps(List<Camp> camps) {
        myCamps = new LinkedList<>(camps);
    }

///////////////// What can a Student do ?
    public void registerForCamp(Camp camp) {
        myCamps.add(camp);
    }

    public void viewRegisteredCamps() {
        myCamps.forEach((camp) -> System.out.println(camp.toString()));
    }

    public void removeCamp(Camp camp) {   // This can be made better (repeated in Staff class);
        String campName = camp.getCampInfo().getName();

        int i = 0;

        for(i = 0; i < myCamps.size(); ++i) {
            
            if(myCamps.get(i).getCampInfo().getName().equals(campName)) {
                break;
            }
        }
        
        myCamps.remove(i);
    }

/////////////// Miscellaneous

    @Override
    public String toString() {
        String temp = Boolean.toString(isComitteeMember()).toUpperCase();
        String comitteeMemberStatus = Highlights.bold(temp);

        return
        super.toString() + 
        Highlights.highlight("Camp Comittee: ") + " " + 
        String.format("%-30s",comitteeMemberStatus) + "\n" + 
        "-".repeat(150);
    }

    public String attendieReportView() {
        return super.toString();
    }

    public String attendieReportString() {
        return super.reportString();
    }
}
