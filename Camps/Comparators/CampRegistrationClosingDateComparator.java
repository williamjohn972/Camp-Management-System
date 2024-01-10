package Camps.Comparators;

import java.time.LocalDate;
import java.util.Comparator;
import Camps.Camp;

public class CampRegistrationClosingDateComparator implements Comparator<Camp> {

    @Override
    public int compare(Camp camp1, Camp camp2) {
        
        LocalDate camp1RegDate = camp1.getCampInfo().getRegistrationClosingDate();
        LocalDate camp2RegDate = camp2.getCampInfo().getRegistrationClosingDate();

        if(camp1RegDate.isBefore(camp2RegDate))
            return -1;
        
        if(camp1RegDate.isAfter(camp2RegDate))
            return 1;

        return 0;
    }
    
}
