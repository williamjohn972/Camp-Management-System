package Users;
import java.io.Serializable;

import Design.Highlights;

public class User implements Serializable {
    
    private String name;
    private String email;
    private String userID;
    private String password = "password";
    private String faculty;


    public User(String name, String email, String faculty) {

        this.name = name;
        this.email = email;
        this.userID = email.split("@")[0];
        this.faculty = faculty;
    }

    public User(String[] credentials) {
        this.name = credentials[0];
        this.email = credentials[1];
        this.faculty = credentials[2];

        userID = email.split("@")[0];
    }


    // Getters;
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getUserID() {return userID;}
    public String getPassword() {return password;}
    public String getFaculty() {return faculty;}


    // Setters; 
    public void setPassword(String newPassword) {this.password = newPassword;}

/////////////////// Miscellaneous;
    @Override 
    public String toString() {

        return 
        Highlights.highlight("Name: ") + String.format("%-30s",getName()) + 
        Highlights.highlight("Email: ") + String.format("%-30s",getEmail()) + 
        Highlights.highlight("Faculty: ") + String.format("%-30s",getFaculty()); 
    }

    public String reportString() {

        return 
        "Name: " + String.format("%-30s",getName()) + 
        "Email: " + String.format("%-30s",getEmail()) + 
        "Faculty: " + String.format("%-30s",getFaculty()); 
    }
}
