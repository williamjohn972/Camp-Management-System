package Camps.Comparators;

import java.time.LocalDate;
import java.util.Comparator;

import Camps.Camp;

public class CampDatesComparator implements Comparator<Camp> {

    @Override
    public int compare(Camp camp1, Camp camp2) {
        LocalDate camp1StartDate = camp1.getCampInfo().getDates()[0];
        LocalDate camp2StartDate = camp2.getCampInfo().getDates()[0];

        if(camp1StartDate.isBefore(camp2StartDate)) {
            return -1;
        }

        if(camp1StartDate.isAfter(camp2StartDate)) {
            return 1;
        }

        else {
            return 0;
        }

    }
    
}
