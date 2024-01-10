package Camps.Comparators;

import java.util.Comparator;

import Camps.Camp;

public class CampLocationComparator implements Comparator<Camp> {

    @Override
    public int compare(Camp camp1, Camp camp2) {
        
        String camp1Location = camp1.getCampInfo().getLocation();
        String camp2Location = camp2.getCampInfo().getLocation();

        int result = camp1Location.compareTo(camp2Location);

        if(result > 0)
            return 1;

        if(result < 0)
            return -1;

        else 
            return 0;
    }

}
