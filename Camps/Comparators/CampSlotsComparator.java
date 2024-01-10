package Camps.Comparators;

import java.util.Comparator;
import Camps.Camp;


public class CampSlotsComparator implements Comparator<Camp> {

    @Override
    public int compare(Camp camp1, Camp camp2) {
        
        int camp1AvailableSlots = camp1.getCampInfo().getAvailableSlots();
        int camp2AvailableSlots = camp2.getCampInfo().getAvailableSlots();

        if(camp1AvailableSlots < camp2AvailableSlots)
            return -1;

        if(camp1AvailableSlots > camp2AvailableSlots) 
            return 1;
        
        return 0;
    }
    
}
