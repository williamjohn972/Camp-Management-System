package Data;

import java.util.List;

import java.util.ArrayList;
import java.io.Serializable;

import Camps.Camp;
import Design.Highlights;

public class CampData implements Data, Serializable {
    
    List<Camp> camps = new ArrayList<>();

    public List<Camp> getAllCampData() {return camps;}

    @Override
    public void viewAllData() {
        
        if(camps.size() == 0) {
            System.out.println("There are no camps here, yet");
            return;
        }

        for(int i = 0; i < camps.size(); ++i) {
            int index = i + 1;
            
            System.out.println(Highlights.bold("Index: " + index));
            camps.get(i).viewCampDetails();
        }
    }

    public void viewAllData(String school) {

        boolean flag = false;
       
        if(camps.size() == 0) {
            System.out.println("There are no camps here, yet");
            return;
        }

        for(int i = 0; i < camps.size(); ++i) {
            int index = i + 1;

            boolean isVisible = camps.get(i).isVisible();
            String campSchool = camps.get(i).getCampInfo().getSchool();

            if(isVisible && (campSchool.equals(school) || campSchool.equals("NTU"))) {
                flag = true;
            
                System.out.println(Highlights.bold("Index: " + index));
                camps.get(i).viewCampDetails();
            }
        }

        if(flag == false) {
            System.out.println("There are no camps here, yet");
        }
    }

}
