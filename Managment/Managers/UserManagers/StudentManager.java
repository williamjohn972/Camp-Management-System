package Managment.Managers.UserManagers;

import Data.UserData;
import Users.Student;
import Users.ComitteeMember;
import Camps.Camp;
import Design.Highlights;

import java.util.List;

public class StudentManager extends UserManager {

    UserData<Student> sd;
    
    public StudentManager() {
        super("Files\\Student.txt");
        sd = (UserData<Student>) getData();                                             // CHECK
    }

    // function to view a students details                                              // GOOD
    public void viewDetails(String id) {
        System.out.println(sd.extractData(id).toString());
        viewRegisteredCamps(id);
    }

    // a function to get the list of camps that a student has registered for            // GOOD
    public List<Camp> getRegisteredCamps(String userID) {
        return sd.extractData(userID).getMyCamps();
    }

    // function to view all camps that a student has registered for 
    public void viewRegisteredCamps(String userID) {                                    // GOOD

        List<Camp> camps = getStudent(userID).getMyCamps();
        viewMyCamps(camps, sd.extractData(userID));
    }

    // this function is present in the superClass 
    // but we've overloaded it here;
    public void viewMyCamps(List<Camp> myCamps, Student student) {                      // GOOD
        
        System.out.println(Highlights.bold("My Camps".indent(70)));
        
        if(myCamps.isEmpty()) 
            System.out.println(Highlights.bold("There are no camps here, yet"));

        for(int i = 0; i < myCamps.size(); ++i) {

            String campName = myCamps.get(i).getCampInfo().getName();

            String memberStatus = Highlights.bold("ATTENDIE"); // it is false by default;

            if(student instanceof ComitteeMember) {
                ComitteeMember member = (ComitteeMember) student;

                if(member.doesHaveCamp()) {
                    String myCampName = member.getCamp().getCampInfo().getName();

                    if(campName.equals(myCampName)) 
                        memberStatus = Highlights.bold("COMITTEE MEMBER"); 
                }
            }
            
            String index = Integer.toString(i + 1) + ") ";
            System.out.println(Highlights.bold(index) + " " + myCamps.get(i).briefView() + 
            Highlights.title("Role") + memberStatus);
        }
    }

    // a function to add a camp to a students list of registered camps;
    public void registerForCamp(Student s, Camp c) {                                    // GOOD
        s.getMyCamps().add(c);
    }   

    // a function to remove a camp from a students list of registered camps;            
    public void deregisterFromCamp(Student s, int campID) {                             // GOOD
        s.getMyCamps().remove(campID);
    }

    // a function to get a particular student from the database
    public Student getStudent(String userID) {                                          // GOOD
        return sd.extractData(userID);
    }

    // a function that assists converting a student to camp comittee member
    public void convertToCampComittee(Student s, ComitteeMember c) {                    // GOOD
        sd.addData(s.getUserID() , c);
    }

    // a function to return a camp from the students list of registered camps
    public Camp getCamp(String userID, int campID) {
        return getRegisteredCamps(userID).get(campID);
    }
}
