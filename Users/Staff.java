package Users;
import java.util.List;
import java.util.ArrayList;

import Camps.Camp;

public class Staff extends User {
    
    private List<Camp> campsIncharge = new ArrayList<>();

    public Staff(String name, String email, String faculty) {
        super(name, email, faculty);
    }

    public Staff(String[] credentials) {
        super(credentials);
    }

//////////////////////////// Getters
    public List<Camp> getMyCamps() {return campsIncharge;} 

/////////////////// What can the Staff do ? 
    public void addCamp(Camp camp) {campsIncharge.add(camp);}

    public void removeCamp(Camp camp) {
        String campName = camp.getCampInfo().getName();

        int i = 0;

        for(i = 0; i < campsIncharge.size(); ++i) {
            
            if(campsIncharge.get(i).getCampInfo().getName().equals(campName)) {
                break;
            }
        }

        campsIncharge.remove(i);
    }


//////////////////////// Miscellaneous
    public void viewCampsInchargeOf() {
        campsIncharge.forEach((camp)-> System.out.println(camp.toString()));
    }
}
