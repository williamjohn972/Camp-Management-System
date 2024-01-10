package Managment.Managers.UserManagers;

import Camps.Camp;
import Data.UserData;
import Users.Staff;
import Users.Student;
import Design.Highlights;

import java.util.List;

public class StaffManager extends UserManager {

    UserData<Staff> sd;
    
    public StaffManager() {
        super("Files\\Staff.txt");
        sd = (UserData<Staff>) getData();
    }

    // a function to toggle camps visibility
    public void toggleCampVisibility(Camp camp) {                                       // GOOD
        
        if(camp.isVisible()) camp.setVisible(false);
        else camp.setVisible(true);
    }

    // a function to view all camps that a staff has created
    public void viewMyCamps(String userID) { // repeated;                              // GOOD
        List<Camp> myCamps = getStaff(userID).getMyCamps();

        super.viewMyCamps(myCamps);
    }

    // a function to get the list of camps that a a staff has created                  // GOOD
    public List<Camp> getMyCamps(String userID) {
        return getStaff(userID).getMyCamps();
    }

    // a function to find a particular staff
    public Staff getStaff(String userID) {                                             // GOOD
        return sd.extractData(userID);
    }

    // a function to return a camp from the students list of registered camps         // GOOD
    public Camp getCamp(String userID, int campID) {
        return getMyCamps(userID).get(campID);
    }

    
}
