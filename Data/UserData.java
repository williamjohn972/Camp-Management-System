package Data;
import java.io.Serializable;
import java.util.*;

public class UserData<T> implements Data, Serializable { 
    
    private Map<String,T> dataMap = new HashMap<>(); // we'll stick to the hashMap implementation for now;

    ///////////////// What functions does a Data object need ?

    public void addData(String id, T data) {
        dataMap.put(id,data);
    }

    public T extractData(String id) { // this could return a null value if the data is not found
        return dataMap.get(id);
    }

    public void removeData(String id) {
        dataMap.remove(id);
    }

    public boolean hasData(String id) {return dataMap.containsKey(id);}

    @Override
    public void viewAllData() {
        dataMap.forEach((key,value) -> {
            System.out.println(value.toString());
        });
    }

}
