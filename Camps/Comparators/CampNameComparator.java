package Camps.Comparators;

import java.util.Comparator;

import Camps.Camp;

public class CampNameComparator implements Comparator<Camp> {

    @Override
    public int compare(Camp camp1, Camp camp2) {

        String camp1Name = camp1.getCampInfo().getName();
        String camp2Name = camp2.getCampInfo().getName();

        int result = camp1Name.compareTo(camp2Name);

        if(result < 0) 
            return -1;

        if(result > 0)
            return 1;

        return 0; 
    }
    
}
