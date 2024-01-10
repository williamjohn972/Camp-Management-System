package Managment;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import Admin.*;
import Camps.Comparators.*;
import Camps.*;
import Users.*;

import Managment.Managers.CampManagers.*;
import Managment.Managers.UserManagers.*;
import Menus.Menu;
import Messages.Message;
import Design.Highlights;


public class Reception {
    
    private StudentManager sm = new StudentManager();
    private StaffManager stm = new StaffManager(); 
    private CampManager cm = new CampManager();

    private boolean isStaff = false;
    private boolean isComiteeMember = false;
    private boolean campsUpdated = false;

    private String ID;

    // this acts as a cache;
    // helps update camp data on users side;
    // helps optimise our application;
    private Map<Integer,Camp> campMap = new HashMap<>();

    LocalDate todaysDate = LocalDate.now();

    // a function that ensures all the data is saved when the application closes;
    public void saveChanges() {                                                             // GOOD
        cm.saveData();
        sm.saveData();
        stm.saveData();
    }

    // this is a helper function that we may use in our MAIN file                           
    public boolean isComitteeMember() {                                                     // GOOD
        return isComiteeMember;
    }

    public boolean isStaff() {                                                              // GOOD
        return isStaff;
    }

    // a helper function accounts for the off by one in campID
    // note that campManager has a built in process for this;
    public int processID(int ID) {                                                          // GOOD
        return ID - 1;
    }

    // a helper function that assists in printing elements of a list;
    public int processIndex(int index) {                                                    // GOOD
        return index + 1;
    }

///////////////////////////////////////////////////////////////////// Student 

    // a function that allows the user to register for a camp
    public void registerForCamp(int campID) {                                               // GOOD
        
        if(isStaff) return; // guard clause; Staff cannot register for camps;

        try {
            Camp camp = cm.getCamp(campID);
            Student student = sm.getStudent(ID);

            if(canRegisterForCamp(student, camp)) {
                
                cm.addMember(camp, student);
                
                sm.registerForCamp(student,camp);
                System.out.println(Highlights.highlight("Successfully Registered For: " + camp.getCampInfo().getName()));
            }

            // campMap.clear();
            // campsUpdated = false;

            int newCampID = campMap.size();
            campMap.put(newCampID,camp);
        }

        catch (IndexOutOfBoundsException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            e.printStackTrace();
            Message.standardErrorMessage();
        }
    }

    // a function that checks if a student can register for a camp
    public boolean canRegisterForCamp(Student student, Camp camp) {                        // GOOD
        
        LocalDate registrationClosingDate = camp.getCampInfo().getRegistrationClosingDate();

        
        if(!camp.isVisible()) {                                        // VALIDATES FILTERS
            Message.invalidCampIndexMessage();
            return false;
        }

        String campOpenTo = camp.getCampInfo().getSchool();             // VALIDATES FILTERS

        if(!campOpenTo.equals("NTU") && !campOpenTo.equals(getMySchool())) {
            Message.invalidCampIndexMessage();
            return false;
        }

        List<String> betrayers = camp.getBetrayers();                  // VALIDATES BETRAYERS
        for(var member : betrayers) {
            if(member.equals(student.getName())) {
                System.out.println();
                Message.betrayerMessage(student.getName());
                return false;
            }
        }
        
        if(camp.getCampInfo().getAvailableSlots() == 0 ||               // VALIDATES DATES
            !todaysDate.isBefore(registrationClosingDate)) {
            System.out.println(Highlights.err("Awww Shucks, looks like you're too late"));
            return false;
        }

        List<Camp> camps = student.getMyCamps();

        LocalDate campStartDate = camp.getCampInfo().getDates()[0];
        LocalDate campEndDate = camp.getCampInfo().getDates()[1];

        for(var c : camps) {

            LocalDate tempStartDate = c.getCampInfo().getDates()[0];
            LocalDate tempEndDate = c.getCampInfo().getDates()[1];

            if(camp.getCampInfo().getName().equals(c.getCampInfo().getName())) {
                
                Message.alreadyRegisteredMessage(camp.getCampInfo().getName());
                return false;
            }

            // VALIDATES CAMP DURATION DATES
            if(!campEndDate.isBefore(tempStartDate) && !campStartDate.isAfter(tempEndDate)) { 
                System.out.println();  
                System.out.println(Highlights.err("Could not Register"));
                System.out.println(Highlights.err("The following Camps have overlapping Dates:"));
                System.out.println();
                System.out.println(camp.briefView2());
                System.out.println(c.briefView2());

                return false;
            } 
        }

        return true;
    }

    // a function to get the school of a user;
    public String getMySchool() {                                                          // GOOD
        
        if(isStaff) {
            Staff s = stm.getStaff(ID);
            return s.getFaculty();
        }

        Student s = sm.getStudent(ID);
        return s.getFaculty();
    }

    // a function that helps a student deregister from a camp;
    public void deregisterFromCamp(int campID) {                                            // GOOD
        try {
            Student student = sm.getStudent(ID);
            Camp camp = getMyCamp(campID);

            if(isComiteeMember && student instanceof ComitteeMember) {
                ComitteeMember member = (ComitteeMember) student;
                if(member.getCamp().getCampInfo().getName().equals(camp.getCampInfo().getName())) {
                    Message.comitteeMemberDeregisterErrorMessage();
                    return;
                }
            }

            cm.removeMember(camp, student);
            camp.getBetrayers().add(student.getName()); // need to find a better place for this
            sm.deregisterFromCamp(student, processID(campID));

            campMap.clear();
            campsUpdated = false;

            Message.successfulDeregisterMessage(camp.getCampInfo().getName());
            Message.divider();
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }
    
    // a function that allows the student to view his her details;
    public void viewStudentDetails(String id) {                                             // GOOD
        sm.viewDetails(id);
    }

    // a function that allows a student to view his/her registered camps                    
    public void viewRegisteredCamps(String userID) {                                        // GOOD
        System.out.println(Highlights.bold("Camps Registered: "));
        sm.viewRegisteredCamps(userID);
    }

///////////////////////////////////////////////////////////////// Camps

    // a function that sorts the camps and lists them out for the user;
    public void viewCamps(int sortingOption) {                                              // GOOD
        
        switch (sortingOption) {

            case 1: // NAME
                cm.getCampList().sort(new CampNameComparator());
                break;

            case 2: // LOCATION
                cm.getCampList().sort(new CampLocationComparator());
                break;

            case 3: // DATES
                cm.getCampList().sort(new CampDatesComparator());

            case 4: // REGISTRATION CLOSING DATE
                cm.getCampList().sort(new CampRegistrationClosingDateComparator());
                break;

            case 5: // AVAILABLE SLOTS
                cm.getCampList().sort(new CampSlotsComparator());
                break;

            case 6: // CAMP COMITTEE SLOTS
                cm.getCampList().sort(new CampComitteeSlotsComparator());
                break;
        
            default:
                Menu.invalidOptionMessage();
                return;
        }

        if(isStaff)
            cm.viewCamps();

        else
            cm.viewCamps(getMySchool());
    }

//////////////////////////////////////////////////////////////// Staff and Comittee Members

    // a function that allows staff and comittee members to view all members of a camp;
    public void viewCampMembers(int id) {                                                   // GOOD
        try {
            cm.viewMembers(getMyCamp(id));
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

    // a function that allows staff and comittee members to view comittee members of a camp;
    public void viewCampComitteeMembers(int id) {                                           // GOOD

        try {
            cm.viewComitteeMembers(getMyCamp(id));
        }

        catch(NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

    // a function that allows staff and comittee members to view regular members of a camp 
    public void viewAllCampMembers(int id) {                                                // GOOD
        try {
            cm.viewAllMembers(getMyCamp(id));
        }

        catch(NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }


////////////////////////////////////////////////////////////// Camps

    //  function that allows users to view details of a camp from the campList;
    public void viewCampDetails(int id) {                                                  // GOOD
        cm.viewDetails(id);
    }


    // function that allows users to view details of a camp from the list of his/her own camps
    public void viewMyCampDetails(int campID) {                                           // GOOD
        try {
            Camp camp = getMyCamp(campID);
            cm.viewDetails(camp);
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }
        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

/////////////////////////////////////////////////////////////////////// All User Functions

    // a function that allows the user to view a list of his/her camps;
    // ensures that all camp details are up to date;
    public void viewMyCamps() {                                // GOOD

        // this ensures that the camps are updated only when they have to 
        // prevents uneccessary updates;
        if(!campsUpdated) {
            updateMyCamps();
            campsUpdated = true; // this ensures, that the camps are updated only once.
            try {TimeUnit.MILLISECONDS.sleep(250);}
            catch (InterruptedException e){}
        }

        if(isStaff) stm.viewMyCamps(ID);
        else sm.viewRegisteredCamps(ID);
    }

    // a helper function that retrives new date from the original camp list and updates camp
    // details on the users end
    public void updateMyCamps() {                                                         // GOOD

        List<Camp> camps = isStaff ? stm.getMyCamps(ID) : sm.getRegisteredCamps(ID);
        
        Message.updatingCampDataMessage();
        Message.divider();

        for(int i = 0; i < camps.size(); ++i) {
            Camp myCamp = camps.get(i);
            campMap.put(i,cm.getCamp(myCamp));
        }

        for(int i = 0; i < camps.size(); ++i) {
            camps.set(i,campMap.get(i));
        }
    }

    // a function to get a camp that a either student has registered for, or a staff has created
    // makes use of the campMap 
    private Camp getMyCamp(int campID) {                                                 // GOOD
        return campMap.get(processID(campID));
    }


//////////////////////////////////////////////////////////////////////////// Admin

    // a function to validate a users credentials when logging in
    public boolean validateCredentials(String userID, String password, boolean isStaff) {   // GOOD
        boolean isValid = 
            isStaff ? stm.validCredentials(userID, password) : sm.validCredentials(userID, password);
        
        if(isValid && isStaff) {
            ID = userID;
            this.isStaff = true;
            this.isComiteeMember = false;
            Message.divider();
            System.out.println(Highlights.highlight("Welcome Back " + stm.getStaff(userID).getName()));
        }

        else if(isValid && !isStaff) {
            Student s = sm.getStudent(userID);
            isComiteeMember = s.isComitteeMember();
            ID = userID;
            this.isStaff = false;

            if(s.isComitteeMember())
                isComiteeMember = true;
            else 
                isComiteeMember = false;
            
            Message.divider();
            System.out.println(Highlights.highlight("Welcome Back " + s.getName()));
        }

        else {
            Message.divider();
            System.out.println(Highlights.err("Invalid credentials"));
            Message.divider();
        }
        
        campMap.clear();
        campsUpdated = false;
        return isValid;
    }

    // a function to register a user
    public void registerUser(String[] userCredentials, boolean isStaff) {                   // GOOD

        if(isStaff) {
            Staff s = new Staff(userCredentials);
            stm.registerUser(s);
        }

        else {
            Student s = new Student(userCredentials);
            sm.registerUser(s);
        }

        String type = isStaff ? "Staff" : "Student";

        Message.divider();
        System.out.printf(Highlights.highlight("Successfully registered %s as %s\n"),
        userCredentials[0],type);
 
    }

    // a function to change a users password
    public void changePassword(String newPassword) {                                        // GOOD
        if(isStaff) 
            stm.changePassword(ID, newPassword);

        else 
            sm.changePassword(ID, newPassword);

        System.out.println(Highlights.highlight("Password Changed Successfuly!"));
    }

/////////////////////////////////////////////////////////////////// Staff

    // a function that allows the staff to delete a camp;
    public void deleteCamp(int id) {                                                        // GOOD
        try {
            Camp camp = getMyCamp(id);

            String campName = camp.getCampInfo().getName();

            if(!camp.getComitteeMembers().isEmpty() || !camp.getMembers().isEmpty())
                System.out.printf(Highlights.err("Yikes, Looks like its too late to delete %s"),campName);

            cm.removeCamp(camp);

            System.out.printf(Highlights.bold("Awww shucks, I was looking forward to %s :(\n"),
            campName);
            Message.divider();
            campMap.clear();
            campsUpdated = false;
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }
    
    // a function that allows the staff to add a camp
    // it allows the staff to interact with the camp manager
    public void addCamp(String[] campInfo) {                                                // GOOD
        Staff s = stm.getStaff(ID);
        Camp c = new Camp(campInfo,s);
        s.addCamp(c);
        cm.addCamp(c);

        System.out.println(Highlights.bold("Camp Preview...").indent(70));
        System.out.println(c.getCampInfo());

        int newCampIndex = campMap.size();
        campMap.put(newCampIndex,c);
    }

    // a function that allows staffs to edit their camps;
    public void editCamp(int campID, String type, String detail) {                         // GOOD
        try {
            Camp camp = getMyCamp(campID);
            CampInfo ci = camp.getCampInfo();

            switch (type.toLowerCase()) {

                case "name":
                    ci.setName(detail);
                    break;

                case "duration":                                // Students must deregister from camps by themselves
                    String[] dates = detail.split(",");   

                    LocalDate startDate = LocalDate.parse(dates[0]);
                    LocalDate endDate = LocalDate.parse(dates[1]);

                    ci.setCampDuration(startDate, endDate);
                    break;

                case "registration closing date":
                    ci.setRegistrationClosingDate(LocalDate.parse(detail));
                    break;
        
                case "location":
                    ci.setLocation(detail);
                    break;

                case "description":
                    ci.setDescription(detail);
                    break;

                case "total slots":
                    ci.setSlots(Integer.parseInt(detail));
                    break;

                case "comittee slots":
                    ci.setComitteeSlots(Integer.parseInt(detail));
                    break;

                case "school":
                    ci.setSchool(detail.toUpperCase());
                    break;

                default:
                    System.out.println(Highlights.err("Invalid Detail"));
                    return;
            }

            Message.divider();
            Message.campEditedSuccessfullyMessage(ci.getName(), type);
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (DateTimeParseException e) {
            System.out.println();
            Message.invalidDateMessage();
        }

        catch (NumberFormatException e) {
            Message.standardErrorMessage();
        }
    }

    // a function that allows staffs to toggle camp visibility
    public void toggleCampVisibility(int campID) {                                         // GOOD
        try {
            Camp camp = getMyCamp(campID);

            if (camp.isVisible())   
                camp.setVisible(false);

            else 
                camp.setVisible(true);

            System.out.printf(Highlights.highlight("%s visibility set to %s\n"), 
            camp.getCampInfo().getName(), camp.isVisible());
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

////////////////////////////////////////////////////////////////// Student 

    // a function that allows a student to apply for a camp comittee position
    public void applyForCampComittee(int campID) {                                      // GOOD                                                      // GOOD
        try{
            Camp camp = getMyCamp(campID);
            
            if(camp.getCampInfo().getAvailableComitteeSlots() == 0) {
                System.out.println(Highlights.err("Awww Shucks, looks like you're too late"));
                return;
            }

            Student s = sm.getStudent(ID);
            if(isComiteeMember) {
                ComitteeMember member = (ComitteeMember) s;
                if(member.getCamp() != null)                        // i think this might solve a bug
                    Message.campComitteeErrorMessage();
                return;
            }

            isComiteeMember = true;
            
            cm.removeMember(camp, s);
            ComitteeMember c = new ComitteeMember(s);
            c.setCamp(camp);
            cm.addMember(camp, c);
            
            sm.convertToCampComittee(s,c);

            Message.campComitteeWelcomeMessage1(camp.getCampInfo().getName());
            Message.campComitteeWelcomeMessage2();
            Message.divider();
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
            e.printStackTrace();
        }
    }

/////////////////////////////////////////////////////////////////// Comittee Member

    // a function that allows a camp comittee member to view their profile 
    // (I think all users should have this feature)
    // will implement that later;
    public void viewProfile() {                                                            // GOOD
        if(isComiteeMember) {
            ComitteeMember member = (ComitteeMember) sm.getStudent(ID); 
            member.viewDetails();
        }
    }

/////////////////////////////////////////////////////////////////// Enquiries

    // a function that allows the student to post enquiries
    public void postEnquiry(String enquiry, int campID) {                                  // GOOD
        try {
            Student s = sm.getStudent(ID);
            Camp camp = getMyCamp(campID);
            
            if(isComiteeMember) {
                ComitteeMember mem = (ComitteeMember) s;
                if(mem.getCamp().getCampInfo().getName().equals(camp.getCampInfo().getName())) {
                    System.out.println(Highlights.highlight("NICE TRY COMITTEE MEMBER"));
                    System.out.println(Highlights.highlight("YOU SHOULD BE ANSWERING QUESTIONS NOT ASKING THEM"));
                    return;
                }
            }

            Enquiry e = new Enquiry(enquiry, s.getName());

            camp.getEnquiries().add(e);
            
            Message.divider();
            System.out.println(Highlights.highlight("Enquiry Submitted Successfully"));
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

    // a function that allows the user to view enquiries regarding their camp              // GOOD
    public boolean viewEnquiries(int campID) {
        try {
            Camp camp = getMyCamp(campID);

            List<Enquiry> enquiries = camp.getEnquiries();

            System.out.println(Highlights.bold(camp.getCampInfo().getName() + " Enquiries").indent(70));
            if(enquiries.isEmpty()) {
                System.out.println(Highlights.bold("No Enquiries"));
                return true;
            }

            for(int i = 0; i < enquiries.size(); ++i) {
                String index = Integer.toString(i + 1) + ") ";
                System.out.print(Highlights.bold(index));
                System.out.println(enquiries.get(i).toString());
            }

            return true;
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
            return false;
        }
    }

    // a function that checks if an enquiry has already been answered;
    public boolean checkHasBeenAnswered(int campID, int enquiryID) {                       // GOOD
        try {
            Camp camp = getMyCamp(campID);

            Enquiry e =  camp.getEnquiries().get(processID(enquiryID));

            if(e.hasBeenAnswered()) {
                Message.divider();
                Message.enquiryHasBeenAnsweredMessage();
                return true;
            }

            return false;
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
            return true;
        }

        catch (IndexOutOfBoundsException e) {
            Message.invalidEnquiryIndexMessage();
            return true;
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
        
        return false; // i could improve this part;
    }

    // a function that allows staff and comittee members to reply to enquiries
    public void replyToEnquiry(int campID, int enquiryID, String reply) {                  // GOOD
        try {
            Camp camp = getMyCamp(campID);

            Enquiry e = camp.getEnquiries().get(processID(enquiryID));
            
            String memberName = "";

            if(isComiteeMember) {
                ComitteeMember member = (ComitteeMember) sm.getStudent(ID);
                ComitteeMember actualMember = cm.getComitteeMember(camp, member);

                member.incrementPoints();
                member.incrementEnquiriesAnswered();

                if(member != actualMember) {
                    actualMember.incrementPoints();
                    actualMember.incrementEnquiriesAnswered();
                }

                memberName = member.getName();
            }

            else if(isStaff) {
                Staff member = stm.getStaff(ID);
                memberName = member.getName();
            }

            e.setReply(reply, memberName);
            System.out.println(Highlights.highlight("Reply Sent Successfully"));
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
            e.printStackTrace();
        }

        catch (IndexOutOfBoundsException e) {
            Message.invalidEnquiryIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }   

    // a helper function that fixes a bug
    // prevents comittee members of one camp from replying to enquiries or view suggestions
    // of another camp
    public boolean hasAccess(int campID) {                                                 // GOOD
        try {
            Camp camp = getMyCamp(campID);
            if( isComiteeMember &&
                !camp.getCampInfo().getName().equals(getMyComitteeCampName())) {
                System.out.println(Highlights.err("OOPS ! Looks like you do not have access"));
                return false;
            }
            return true;
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }

        return false;
    }


////////////////////////////////////////////////////////////////////// Comittee Member

    // a function to return the campID of the camp comittee member;
    // that specific camp;
    // functions that use this function may need to catch a possible index out of bounds exception
    // i could also modify this function to make a use of the campMAP
    public int getMyCampID() {                                                          // OPTIMISE
        ComitteeMember me = (ComitteeMember) sm.getStudent(ID);

        if(!me.doesHaveCamp())
            return -1;

        List<Camp> myCamps = me.getMyCamps();

        for(int i = 0; i < myCamps.size(); ++i) {
            String myCampName = me.getCamp().getCampInfo().getName();
            String campName = myCamps.get(i).getCampInfo().getName();

            if(myCampName.equals(campName)) {
                return i + 1;
            }
        }

        return -1;
    }


    // a helper function that fixes a bug
    // prevents a member of one comitte from replying to an enquiry regarding another camp;
    public String getMyComitteeCampName() {
        ComitteeMember member = (ComitteeMember) sm.getStudent(ID);
        return member.getCamp().getCampInfo().getName();
    }

/////////////////////////////////////////////////////////////// Suggestions

    // a funciton that allows comitteeMembers to post their sugestions
    public void postSuggestion(String suggestion, int campID) {                            // GOOD

        try {
            Student attendie = sm.getStudent(ID);

            if(attendie instanceof ComitteeMember)  {
                ComitteeMember member = (ComitteeMember) attendie;
                Camp camp = getMyCamp(campID);
                
                Suggestion s = new Suggestion(suggestion, member);
                camp.getSuggestions().add(s);
                member.incrementPoints();
                member.incrementSuggestionsMade();

                ComitteeMember actualComitteeMember = cm.getComitteeMember(camp, member);

                if(member != actualComitteeMember) { 
                    actualComitteeMember.incrementPoints();
                    actualComitteeMember.incrementSuggestionsMade();
                }

                Message.divider();
                System.out.println(Highlights.highlight("Suggestion Posted Successfully"));
            }
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

    // a method, for staffs and comittee members to view suggestions;
    public boolean viewSuggestions(int campID) {                                           // GOOD
       try { 

           Camp camp = getMyCamp(campID);

            List<Suggestion> suggestions = camp.getSuggestions();
            
            System.out.printf(Highlights.bold("%s Suggestions\n").indent(70),
            camp.getCampInfo().getName());

            if(suggestions.isEmpty()) {
                System.out.println(Highlights.bold("No suggestions, yet")); // we can put a better message here
                return true;
            }

            for(int i = 0; i < suggestions.size(); ++i) {
                String index = Highlights.bold(Integer.toString(i + 1) + ") ");

                System.out.println(index + suggestions.get(i));
            }
            return true;
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
            return false;
        }

        catch (Exception e) {
            Message.standardErrorMessage();
            return false;
        }
    }

    // a function that allows staff to accept a suggestion
    public void acceptSuggestion(int campID, int suggestionID) {                           // GOOD
        try {
            Camp camp = getMyCamp(campID);
            Suggestion suggestion = camp.getSuggestions().get(processID(suggestionID));

            if(checkHasBeenAccepted(suggestion)) {
                Message.divider();
                System.out.println(Highlights.highlight("Dont sweat it. This suggestion has already been accepted"));
                return;
            }

            suggestion.accept();

            ComitteeMember member = (ComitteeMember) sm.getStudent(suggestion.getComitteeMember().getUserID());
            ComitteeMember actualMember = cm.getComitteeMember(camp, member);
            
            member.incrementPoints();
            actualMember.incrementPoints();

            Message.divider();
            System.out.printf(Highlights.highlight("Successfully accepted %s's suggestion\n")
            , suggestion.getComitteeMember().getName());
        }

        catch (NullPointerException e) {
            Message.invalidCampIndexMessage();
        }

        catch (IndexOutOfBoundsException e) {
            Message.invalidSuggestionIndexMessage();
        }

        catch (Exception e) {
            Message.standardErrorMessage();
        }
    }

    // a function that checks if a suggestion has been accepted;
    public boolean checkHasBeenAccepted(Suggestion s) {                                    // GOOD
        return s.isAccepted();
    }
/////////////////////////////////////////////////////////////////////// Generate Reports
    
    // a function that allows comittee members and staff's to generate a report of their camp
    // it includes filters (All Members, Comittee Members, Members);
    public void generateReport(int campID, int reportType, String fileName) {              // GOOD

        try {
            File f = new File("Reports\\" + fileName + ".txt");
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            Camp camp = getMyCamp(campID);

            pw.println("REPORT".indent(70));

            pw.printf("Generated On: %-100s", todaysDate);
            pw.printf("By: %s\n\n", getMyName());

            pw.println(cm.campDetailsReport(camp));

            switch (reportType) {
                case 1:
                    pw.println(cm.allMembersReport(camp));
                    break;

                case 2:
                    pw.println(cm.comitteeMembersReport(camp));
                    break;

                case 3:
                    pw.println(cm.membersReport(camp));
                    break;
            
                default:
                    pw.println(cm.allMembersReport(camp));
            }

            pw.close();

            System.out.println(Highlights.highlight("Report Generated Successfully"));
        }

        catch (Exception e) {
            Message.generateReportErrorMessage(fileName);
        }
    }

    public void generateEnquiryReport(int campID, String fileName) {
        try {
            File f = new File("Reports\\" + fileName + ".txt");
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            Camp camp = getMyCamp(campID);
            String campName = camp.getCampInfo().getName();

            pw.printf("%s Enquiry Report".indent(70),campName);
            pw.println();

            pw.printf("Generated On: %-100s", todaysDate);
            pw.printf("By: %s\n\n", getMyName());

            List<Enquiry> enquiries = camp.getEnquiries();

            for(var enquiry : enquiries) {
                pw.println(enquiry.reportView());                
            }

            pw.close();

            System.out.println(Highlights.highlight("Report Generated Successfully"));

        }

        catch (Exception e) {
            Message.generateReportErrorMessage(fileName);
        }
    }

    // a helper function that gets the users name
    public String getMyName() {                                                            // GOOD
        
        return isStaff ? stm.getStaff(ID).getName() : sm.getStudent(ID).getName();
    }

}

