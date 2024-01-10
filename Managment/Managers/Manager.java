package Managment.Managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Data.*;
import Users.Student;
import Users.Staff;

public class Manager {
    private final String file;
    private Data data;
    
    public Manager(String f) {

        file = f;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            data = (Data) ois.readObject();
            ois.close();
        }

        catch (Exception e) {
            if(file.contains("Camp")) data = new CampData();
            if(file.contains("Student")) data = new UserData<Student> (); 
            if(file.contains("Staff")) data = new UserData<Staff>();
        }
    }

    public Data getData() {       
        return data;
    }

//////////////////// Viewing Data

    public void viewData() {data.viewAllData();}

/////////////////////// Saving data to file;

    public void saveData() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(data);
            oos.close();
        }

        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
