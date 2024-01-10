package Camps.Comparators;

import java.util.Comparator;
import Camps.Camp;

public class CampComitteeSlotsComparator implements Comparator<Camp> {
    
    
    @Override
    public int compare(Camp camp1, Camp camp2) {
        
        int camp1AvailableComitteeSlots = camp1.getCampInfo().getAvailableComitteeSlots();
        int camp2AvailableComitteeSlots = camp2.getCampInfo().getAvailableComitteeSlots();

        if(camp1AvailableComitteeSlots < camp2AvailableComitteeSlots)
            return -1;

        if(camp1AvailableComitteeSlots > camp2AvailableComitteeSlots) 
            return 1;
        
        return 0;
    }
}
