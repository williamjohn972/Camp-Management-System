package Managment.Managers.CampManagers;

import Admin.Enquiry;

import java.util.List;

import Camps.Camp;
import Users.ComitteeMember;
import Users.Student;
import Data.CampData;
import Design.Highlights;
import Managment.Managers.Manager;


public class CampManager extends Manager {

        private CampData cd;

        private static String invalidCampIndexMessage = Highlights.err("This is an invalid camp index");

        public CampManager() {
            super("Files\\Camp.txt");
            cd = (CampData) getData();
        }

/////////////////////////// Reading Camp Data
    public void viewCamps() {                                                           // GOOD                          
        cd.viewAllData();
    }

    public void viewCamps(String school) {                                              // GOOD
        cd.viewAllData(school);
    }

    public void viewMembers(Camp c) {                                                   // GOOD

        Camp camp = getCamp(c);
        List<Student> members = camp.getMembers();
        printMembers(members, false);
    }

    public void viewComitteeMembers(Camp c) {                                           // GOOD

        Camp camp = getCamp(c);
        List<Student> comitteeMembers = camp.getComitteeMembers();
        printMembers(comitteeMembers, true);
    }

    public void viewAllMembers(Camp c) {                                                // GOOD
        
        Camp camp = getCamp(c);
        List<Student> members = camp.getMembers();
        List<Student> comitteeMembers = camp.getComitteeMembers();

        System.out.println(Highlights.bold("All Members\n").indent(70));
        printMembers(comitteeMembers, true);
        System.out.println();
        printMembers(members, false);
    }

    public void viewDetails (int id)  {
        try {
            Camp camp = getCamp(id);
            camp.viewCampDetails();
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println(invalidCampIndexMessage);
        }
    }

    public void viewDetails(Camp camp) {
        camp.viewCampDetails();
    }

///////////////////////// Adding and Removing Camps
    public void removeCamp(Camp c) {                                                    // GOOD
        // cd.getData().remove(id - 1);
        getCampList().remove(c);
    }

    public void addCamp(Camp camp) {                                                    // GOOD
        // cd.getData().add(camp);
        getCampList().add(camp);
    }

///////////////////////// Registering Students for the Camp;
    public void addMember(Camp c, Student s) {                                          // GOOD
        
        if(s.isComitteeMember()) {
            ComitteeMember member = (ComitteeMember) s;

            String temp;

            try {
                temp = member.getCamp().getCampInfo().getName();
            }

            catch (NullPointerException e) {
                temp = "NO CAMP";
            }
        
            String comitteeMemberCampName = temp;
            String campName = c.getCampInfo().getName();

            if(comitteeMemberCampName.equals(campName)) {
                c.addComitteeMember(s);
                return;
            }
        }
        
        c.addMember(s);
    }

    public void removeMember(Camp c, Student s) {                                       // GOOD

        c.removeMember(s);
    }

//////////////////// Miscellanious;

    // a function to return the camp list;
    public List<Camp> getCampList() {                                                   // GOOD 
        return cd.getAllCampData();
    }

    // a function to return a specific camp;
    // index corresponds to the actaul list with the campManager
    public Camp getCamp(int id) throws IndexOutOfBoundsException {                      // GOOD
            Camp c = cd.getAllCampData().get(id-1);
            return c;
    }

    public Camp getCamp(Camp c) throws NullPointerException {
        List<Camp> campList = cd.getAllCampData();

        for(int i = 0; i <= campList.size(); ++i) {
            if(c.getCampInfo().getName().equals(campList.get(i).getCampInfo().getName()))
                return campList.get(i);
        }
        
        return null;
    }

    public int getCampID(Camp c) throws NullPointerException {                  // CHECK
        List<Camp> campList = cd.getAllCampData();

        for(int i = 0; i <= campList.size(); ++i) {
            if(c.getCampInfo().getName().equals(campList.get(i).getCampInfo().getName()))
                return i + 1;
        }

        return -1;
    }

    // a function to print all members of the camp (filters included)
    private void printMembers(List<Student> campMembers, boolean isComitteeMember) {    // GOOD

        String emptyMessage = isComitteeMember ? "No Comittee Members\n" : "No Attendies\n";

        if(campMembers.isEmpty()) {
            System.out.println(Highlights.bold(emptyMessage));
            return;
        }   

        String message = isComitteeMember ? "Camp Comittee: " : "Camp Attendees: ";
        System.out.println(Highlights.bold(message));
        for(var member : campMembers) {

            if(!isComitteeMember) {
                System.out.println(member.attendieReportView());
            }
            else {
                ComitteeMember comitteeMember = (ComitteeMember) member;
                System.out.println(comitteeMember.reportView()); // work on this tmrw;
            }
            System.out.println("-".repeat(150));
        }
    }

    public void postEnquiry(Enquiry enquiry, int campID) {                              // CHECK
        Camp c = getCamp(campID);
        c.getEnquiries().add(enquiry);
    }

    public ComitteeMember getComitteeMember(Camp camp, ComitteeMember member) {
        
        for(var mem : camp.getComitteeMembers()) {

            if(member.getUserID().equals(mem.getUserID()))
                return (ComitteeMember) mem;
        }

        return null;
    }

    public String generateReportString(List<Student> campMembers, boolean isComitteeMember) { 

        String emptyMessage = isComitteeMember ? 
            "No Comittee Members\n" : "No Attendies\n";

        if(campMembers.isEmpty()) {
            return (emptyMessage + "\n" + "-".repeat(150));
        }   

        String message = isComitteeMember ? "Camp Comittee:\n" : "Camp Attendees:\n";

        String reportString = "-".repeat(150) + "\n";

        reportString += message + "\n";

        for(var member : campMembers) {

            if(!isComitteeMember) {
                reportString += member.attendieReportString() + "\n";
            }

            else {
                ComitteeMember comitteeMember = (ComitteeMember) member;
                reportString += comitteeMember.reportString() + "\n";
            }
        }

        return reportString;
    }

    public String membersReport(Camp c) {                                                

        Camp camp = getCamp(c);
        List<Student> members = camp.getMembers();
        return generateReportString(members, false);
    }


    public String comitteeMembersReport(Camp c) {                                                

        Camp camp = getCamp(c);
        List<Student> members = camp.getComitteeMembers();
        return generateReportString(members, true);
    }


    public String allMembersReport(Camp c) {                                                

        Camp camp = getCamp(c);
        List<Student> members = camp.getMembers();
        List<Student> comiteeMembers = camp.getComitteeMembers();
        return 
        "All Members\n".indent(70) + 
        generateReportString(comiteeMembers, true) + "\n" + "-".repeat(150) + "\n" +  
        generateReportString(members, false);
    }

    public String campDetailsReport(Camp c) {
        Camp camp = getCamp(c);
        return camp.getCampInfo().reportString();
    }

    public void updateCampData(Camp camp) {
        System.out.println("Called");
        Camp actualCamp = getCamp(camp);
        camp = actualCamp;
    }
}
 


