package Managment.Managers.UserManagers;

import java.util.List;

import Camps.Camp;
import Data.UserData;
import Design.Highlights;
import Managment.Managers.Manager;
import Users.*;

public class UserManager extends Manager{

    private UserData<User> d = (UserData<User>) getData();                              // CHECK

    public UserManager(String f) {
        super(f);
    }

    // a function to validate a users credentials
    public boolean validCredentials(String userID, String password) {                   // GOOD
        
        if(d.hasData(userID)) {
            User u = getUser(userID);
            if(u.getPassword().equals(password)) {

                if(u.getPassword().equals("password")) {
                    System.out.println("-".repeat(150));
                    System.out.println(Highlights.bold("The following require your immediate attention: "));
                    System.out.println(Highlights.bold("- Please change your password"));
                }
                return true;
            }
        }
        return false;
    }

    public void registerUser(Student s) {
        d.addData(s.getUserID(),s);
    }

    public void registerUser(Staff s) {
        d.addData(s.getUserID(), s);
    }

    public void changePassword(String userID, String newPassword) {
        User u = getUser(userID);
        u.setPassword(newPassword);
    }

    private User getUser(String userID) {
        return (User) d.extractData(userID);
    }

    // a function to view all camps that either the user created, or is a part of;
    public void viewMyCamps(List<Camp> myCamps) {                                       // GOOD

        System.out.println(Highlights.bold("My Camps").indent(70));
        if(myCamps.isEmpty()) 
        System.out.println(Highlights.bold("There are no camps here, yet"));

        for(int i = 0; i < myCamps.size(); ++i) {
            String index = Integer.toString(i + 1) + ") ";
            System.out.println(Highlights.bold(index) + " " + myCamps.get(i).briefView());
        }
    }
}

