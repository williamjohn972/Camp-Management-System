import Managment.Reception;
import Menus.Menu;
import Menus.MyMenus;
import Messages.Message;
import Design.Highlights;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class CAMSApp {

    private static String campChoice = Highlights.bold("Camp: ");


    private static Reception r = new Reception();
    private static Scanner sc = new Scanner(System.in);

    private static void dividerAlternate() {
        System.out.println("-".repeat(10));
    }

    public static void main(String[] args) {
        run();
    }
///////////////////////////////////// Menu Functions //////////////////////////////////////////
    private static void run() {
        Message.openingMessage();

        int choice = 0;
        
        do {
            try {
                MyMenus.menu1.display();

                choice = sc.nextInt();

                Message.divider();
        
                switch (choice) {
                    case 0:
                    Message.closingMessage();
                    break;

                    case 1:
                        login();
                        break;

                    case 2:
                        register();
                        break;
                
                    default:
                        Menu.invalidOptionMessage();
                        break;
                }

            }

            catch (InputMismatchException e) {
                Message.invalidInputMessage();
                sc.nextLine();
                continue;
            }

        } while(choice != Menu.quitOption());

        sc.close();
        r.saveChanges();
    }

    private static void register() {
        int choice = 0;
        String[] userInfo = new String[3];

        do {

            try {
                System.out.println(Highlights.bold("Register as ..."));
                MyMenus.menu2.display();
                choice = sc.nextInt();

                switch (choice) {
                    case 0:
                        return;

                    case 1:
                        userInfo = getUserInfo();
                        r.registerUser(userInfo, false);
                        Message.divider();
                        break;

                    case 2:
                        userInfo = getUserInfo();
                        r.registerUser(userInfo, true);
                        Message.divider();
                        break;
                
                    default:
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }
    
        } while(choice != Menu.quitOption());
    }

    private static void login() {
        int choice = 0;
        String[] credentials = new String[2];
        boolean isValid = false;

        do {
            try {
                System.out.println(Highlights.bold("Login as ..."));
                MyMenus.menu2.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {
                    case 0:
                        return;

                    case 1:
                        credentials = getCredentials();
                        isValid = r.validateCredentials(credentials[0], credentials[1], false);
                        
                        if(isValid) { 
                            if(r.isComitteeMember()) comitteeMemberOptions();
                            else studentOptions();
                        }

                        break;

                    case 2:
                        credentials = getCredentials();
                        isValid = r.validateCredentials(credentials[0], credentials[1], true);
                        if(isValid) staffOptions();
                        break;   

                    default:
                        Menu.invalidOptionMessage();
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

            
        } while(choice != Menu.quitOption());
    }

    private static void staffOptions() {
        int choice = 0;
        int campID = 0;

        String[] campInfo = new String[9];

        do {
            try {
                MyMenus.staffMenu.display();
                choice = sc.nextInt();
                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        viewCampsOptions();
                        break;

                    case 2:
                        myCampsStaffOptions();
                        break;

                    case 3:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.viewCampDetails(campID);
                        break;

                    case 4:      
                        Message.createCampMessage();                         
                        campInfo = getCampInfo();
                        r.addCamp(campInfo);
                        System.out.println(Highlights.highlight("Created New Camp: " + campInfo[0]));
                        break;

                    case 5:
                        r.changePassword(getNewPassword());
                        break;
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;

                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void campDetailsOptions(int campID) { 
        
        if(campID <= 0) {
            Message.invalidCampIndexMessage();
            return;
        }

        int choice = 0;

        do {
            try {
                System.out.println(Highlights.bold("View ..."));

                MyMenus.campDetailsMenu.display();
                choice = sc.nextInt();
                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        r.viewMyCampDetails(campID);
                        r.viewAllCampMembers(campID);
                        generateReportOptions(campID, 1);
                        break;

                    case 2:
                        r.viewMyCampDetails(campID);
                        r.viewCampComitteeMembers(campID);
                        generateReportOptions(campID, 2);
                        break;

                    case 3:
                        r.viewMyCampDetails(campID);
                        r.viewCampMembers(campID);
                        generateReportOptions(campID, 3);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;

                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption()); 
    }

    private static void studentOptions() {
        int choice = 0;
        int campID = 0;

        do {
            try {

                MyMenus.studentMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        viewCampsOptions();
                        break;

                    case 2:
                        myCampsStudentOptions();
                        if(r.isComitteeMember()) return;
                        break;

                    case 3:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.viewCampDetails(campID);
                        break;

                    case 4:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.registerForCamp(campID);
                        break;
                    
                    case 5:
                        r.changePassword(getNewPassword());
                        break;
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;

                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());        
    }

    private static void myCampsStudentOptions() {
          int choice = 0;
          int campID = 0;

        do {
            try {
                r.viewMyCamps();
                MyMenus.myCampsStudentMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.applyForCampComittee(campID);
                        if(r.isComitteeMember()) return;
                        break;

                    case 2:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.viewMyCampDetails(campID);
                        break;

                    case 3:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        if(r.viewEnquiries(campID))
                            studentEnquiriesOptions(campID);
                        break;

                    case 4:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.deregisterFromCamp(campID);
                        break;
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;

                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());  
    }

    private static void studentEnquiriesOptions(int campID) {

        int choice = 0;

        do {
            try {
                MyMenus.studentEnquiriesMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        Message.createEnquiryMessage();
                        System.out.print(Highlights.bold("Question: "));
                        sc.nextLine();
                        String question = sc.nextLine();

                        if(!proceedWithAction())
                            break;
                    
                        r.postEnquiry(question,campID);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void comitteeMemberOptions() {
        int choice = 0;
        int campID = 0;

        do {
            try {
                MyMenus.comitteeMemberMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        r.viewProfile();
                        break;

                    case 2:
                        viewCampsOptions();
                        break;

                    case 3:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.viewCampDetails(campID);
                        break;

                    case 4:
                        myCampsComitteeMemberOptions();
                        break;

                    case 5:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.registerForCamp(campID);
                        break;

                    case 6:
                        r.changePassword(getNewPassword());
                        break;
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;

                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption()); 
    }

    private static void myCampsComitteeMemberOptions() {
        int choice = 0;
        int campID = 0;

        do {
            try {
                r.viewMyCamps();
                MyMenus.myCampsComitteeMemberMenu.display();
                choice = sc.nextInt();
                Message.divider();
                switch (choice) {
                    
                    case 0:
                        return;
                    
                    case 1:
                        campDetailsOptions(r.getMyCampID());
                        break;

                    case 2:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.viewMyCampDetails(campID);
                        break;

                    case 3:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        if(r.viewEnquiries(campID))
                            comitteeEnquiriesOptions(campID);
                        break;
                    
                    case 4:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        
                        if(!r.hasAccess(campID))
                        break;
                        
                        if(r.viewSuggestions(campID))   
                            comitteeSuggestionsOptions(campID);
                        break;
                    
                    case 5:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.deregisterFromCamp(campID);
                        break;
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void comitteeEnquiriesOptions(int campID) {
        
        int choice = 0;

        do {
            try {
                MyMenus.comitteeEnquiriesMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        Message.createEnquiryMessage();
                        System.out.print(Highlights.bold("Question: "));
                        sc.nextLine();
                        String question = sc.nextLine();
                        
                        if(!proceedWithAction())
                            break;

                        r.postEnquiry(question,campID);
                        break;

                    case 2:
                        if(!r.hasAccess(campID))
                            break;
                
                        Message.createEnquiryReplyMessage();
                        System.out.print(Highlights.bold("Which enquiry would you like to reply to: "));
                        int enquiryID = sc.nextInt();
                        sc.nextLine();
                        if(r.checkHasBeenAnswered(campID,enquiryID))
                            break;
    
                        System.out.print(Highlights.bold("\nReply: "));    
                        String reply = sc.nextLine();

                        if(!proceedWithAction())
                            break;

                        r.replyToEnquiry(campID, enquiryID,reply);
                        break;

                    case 3:
                        if(!r.hasAccess(campID))
                            break;
                        
                        String fileName;
                        System.out.print("Enter your file name: ");
                        if(sc.hasNext()) sc.nextLine();
                        fileName = sc.nextLine();
                        r.generateEnquiryReport(campID,fileName);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void myCampsStaffOptions() {
        int choice = 0;
        int campID = 0;
        
        do {
            try {
                r.viewMyCamps();
                MyMenus.myCampsStaffMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:    
                        return;

                    case 1: 
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        campDetailsOptions(campID);
                        break;

                    case 2:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        if(r.viewEnquiries(campID))
                            staffEnquiriesOptions(campID);
                        break;

                    case 3:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        if(r.viewSuggestions(campID))
                            staffSuggestionsOptions(campID);
                        break;

                    case 4:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        staffEditCampOptions(campID); 
                        break;

                    case 5:
                        System.out.print(campChoice);
                        campID = sc.nextInt();
                        r.deleteCamp(campID);  
                        break; 
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void staffEnquiriesOptions(int campID) {
        
        int choice = 0;
        do {
            try {
                MyMenus.staffEnquiriesMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        Message.createEnquiryReplyMessage();
                        System.out.print(Highlights.bold("Which enquiry would you like to reply to: "));
                        int enquiryID = sc.nextInt();
                        sc.nextLine();
                        if(r.checkHasBeenAnswered(campID,enquiryID))
                            break;
    
                        System.out.print(Highlights.bold("\nReply: "));    
                        String reply = sc.nextLine();

                        if(!proceedWithAction())
                            break;

                        r.replyToEnquiry(campID, enquiryID,reply);
                        break;

                    case 2:
                        String fileName;
                        System.out.print("Enter your file name: ");
                        if(sc.hasNext()) sc.nextLine();
                        fileName = sc.nextLine();
                        r.generateEnquiryReport(campID,fileName);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }
            
            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void comitteeSuggestionsOptions(int campID) {
        
        int choice = 0;
        String suggestion = "";

        do {
            try {
                MyMenus.comitteeSuggestionsMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        Message.createSuggestionMessage();
                        System.out.print(Highlights.bold("Suggestion: "));
                        sc.nextLine();

                        suggestion = sc.nextLine();

                        if(!proceedWithAction())
                            break;

                        r.postSuggestion(suggestion, campID);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void staffSuggestionsOptions(int campID) {
            int choice = 0;
            int suggestionID = 0;

        do {
            try {
                MyMenus.staffSuggestionsMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        System.out.print(Highlights.bold("Suggestion: "));
                        suggestionID = sc.nextInt();

                        if(!proceedWithAction())
                            break;

                        r.acceptSuggestion(campID, suggestionID);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }
            
            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    private static void viewCampsOptions() {
        int choice = 0;

        do {
            try {
                System.out.println(Highlights.bold("Sort Camps By... "));

                MyMenus.viewCampsMenu.display();
                choice = sc.nextInt();

                switch (choice) {
                    case 0:
                        return;
                
                    default:
                        Message.divider();
                        r.viewCamps(choice);
                        Message.divider();
                        break;
                }
            }

            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());   
    }

    private static void generateReportOptions(int campID, int reportType) {
        int choice = 0;

        do {
            try {
                MyMenus.generateReportMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;

                    case 1:
                        System.out.print(Highlights.bold("Enter your File Name: "));
                        sc.nextLine();
                        String fileName = sc.nextLine();
                        r.generateReport(campID, reportType, fileName);
                        break;
                    
                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }
        
            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }
                
        } while(choice != Menu.quitOption()); 
    }
    
    private static void staffEditCampOptions(int campID) {
        int choice = 0;

        do {
            try {
                MyMenus.staffEditCampMenu.display();
                choice = sc.nextInt();

                Message.divider();
                switch (choice) {

                    case 0:
                        return;
                    
                    case 1:
                        r.editCamp(campID, "Duration", getNewDetailsString("Duration"));
                        break;

                    case 2:
                        r.editCamp(campID, "Registration Closing Date", getNewDetailsString("Registration Closing Date"));
                        break;

                    case 3:
                        r.editCamp(campID, "Location", getNewDetailsString("Location"));
                        break;

                    case 4:
                        r.editCamp(campID, "Description", getNewDetailsString("Description"));
                        break;

                    case 5:
                        r.editCamp(campID, "Total Slots", getNewDetailsString("Total Slots"));
                        break;

                    case 6:
                        r.editCamp(campID, "Comittee Slots", getNewDetailsString("Comittee Slots"));
                        break;

                    case 7:
                        r.editCamp(campID, "School", getNewDetailsString("School"));
                        break;

                    case 8:
                        r.toggleCampVisibility(campID);
                        break;

                    default:
                        Menu.invalidOptionMessage();
                        break;
                }
            }
            
            catch (InputMismatchException e) {
                sc.nextLine();
                Message.invalidInputMessage();
                continue;
            }

        } while(choice != Menu.quitOption());
    }

    ///////////////////////////////////////// Helper Functions //////////////////////////////////////
    
    private static String[] getUserInfo() {

        String[] userInfo = new String[3];
        sc.nextLine();
        System.out.print("Name: ");
        userInfo[0] = sc.nextLine();

        System.out.print("Email: ");
        userInfo[1] = sc.next();

        System.out.print("Faculty: ");
        userInfo[2] = sc.next();

        return userInfo;
    }

    private static String[] getCredentials() {
        String[] creds = new String[2];
        System.out.print("Enter your UserID: ");
        creds[0] = sc.next();
        
        System.out.print("Enter your password: ");
        creds[1] = sc.next();

        return creds;
    }

    public static String getNewPassword() {
        System.out.print("Enter your new password: ");
        sc.nextLine();
        String password = sc.nextLine();
        return password;
    }

    private static String[] getCampInfo() {
        String[] campInfo = new String[9];
        sc.nextLine();

        System.out.print("Name: ");
        campInfo[0] = sc.nextLine();
        dividerAlternate();

        System.out.print("Start Date (yyyy-mm-dd): ");
        campInfo[1] = sc.nextLine();
        dividerAlternate();


        System.out.print("End Date (yyyy-mm-dd): ");
        campInfo[2] = sc.nextLine();
        dividerAlternate();

        System.out.print("Registration Closing Date (yyyy-mm-dd): ");
        campInfo[3] = sc.nextLine();
        dividerAlternate();

        System.out.print("School: ");
        campInfo[4] = sc.nextLine();
        dividerAlternate();

        System.out.print("Location: ");
        campInfo[5] = sc.nextLine();
        dividerAlternate();

        System.out.print("Description: ");
        campInfo[6] = sc.nextLine();
        dividerAlternate();

        System.out.print("Slots: ");
        campInfo[7] = sc.nextLine();
        dividerAlternate();

        System.out.print("Comittee Slots: ");
        campInfo[8] = sc.nextLine();
        dividerAlternate();
        
        return campInfo;
    }

    public static String getNewDetailsString(String detail) {
        
        sc.nextLine();

        if(detail.toLowerCase().equals("duration")) {

            System.out.print(Highlights.bold("Start Date (yyyy-mm-dd): "));
            String startDate = sc.nextLine();

            System.out.print(Highlights.bold("End Date (yyyy-mm-dd): "));
            String endDate = sc.nextLine();

            return startDate + "," + endDate;
        }

        else if(detail.toLowerCase().equals("registration closing date")) {
            System.out.print(Highlights.bold("Registration Closing Date (yyyy-mm-dd): "));
            return sc.nextLine();
        }
        
        String newDetail = "TBD";
        System.out.print(Highlights.bold(detail + ": "));
        newDetail = sc.nextLine();

        return newDetail;
    }

    private static boolean proceedWithAction() {

        System.out.println();
        Message.confirmActionsMessage();
        // if(sc.hasNextLine()) sc.nextLine();
        String proceed = sc.nextLine();

        if(proceed.toLowerCase().equals("y"))
            return true;

        return false;
    }
}


