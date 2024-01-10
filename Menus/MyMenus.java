package Menus;

import java.util.Arrays;

public class MyMenus {
    
        public static Menu menu1 = new Menu(Arrays.asList( "Exit","Login", "Register"));
        public static Menu menu2 = new Menu(Arrays.asList("Back","Student", "Staff"));

        public static Menu studentMenu = new Menu(Arrays.asList("Back", "View All Camps", "View My Camps", "View Camp Details", "Register For Camp", "Change Password"));
        public static Menu comitteeMemberMenu = new Menu(Arrays.asList("Back", "MyProfile", "View All Camps","View Camp Details", "View My Camps", "Register For Camp",  "Change Password"));
        public static Menu staffMenu = new Menu(Arrays.asList("Back", "View All Camps","View My Camps", "View Camp Details", "Add New Camp", "Change Password"));

        public static Menu campDetailsMenu = new Menu(Arrays.asList("Back", "All Members", "Comittee Members", "Members"));
        public static Menu myCampsStudentMenu = new Menu(Arrays.asList("Back","Apply for Camp Comittee","Camp Details", "View Enquiries", "Deregister"));
        public static Menu myCampsComitteeMemberMenu = new Menu(Arrays.asList("Back", "View My Camp Details","Camp Details", "View Enquiries", "View Suggestions", "Deregister"));
        public static Menu myCampsStaffMenu = new Menu(Arrays.asList("Back", "View My Camp Details", "View Enquiries", "View Suggestions", "Edit Camp", "Delete Camp"));

        public static Menu studentEnquiriesMenu = new Menu(Arrays.asList("Back","Post Enquiry"));
        public static Menu comitteeEnquiriesMenu = new Menu(Arrays.asList("Back","Post Enquiry", "Reply", "Generate Report"));
        public static Menu staffEnquiriesMenu = new Menu(Arrays.asList("Back", "Reply","Generate Report"));

        public static Menu comitteeSuggestionsMenu = new Menu(Arrays.asList("Back", "Add a Suggestion"));
        public static Menu staffSuggestionsMenu = new Menu(Arrays.asList("Back","Accept a Suggestion"));

        public static Menu viewCampsMenu = new Menu(Arrays.asList("Back","Name","Location","Dates","Registration Closing Date", "Availalbe Slots", "Available Comittee Slots"));

        public static Menu generateReportMenu = new Menu(Arrays.asList("Back", "Generate Report"));

        public static Menu staffEditCampMenu = new Menu(Arrays.asList("Back", "Duration", "Registration Closing Date",  "Location", "Description", "Total Slots", "Comittee Slots", "School", "Toggle Visibility"));
}
