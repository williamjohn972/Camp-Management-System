package Camps;
import Users.Staff;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;

import Design.Highlights;

public class CampInfo implements Serializable {

    private static final int maxComitteeSlots = 10;
    
    private String name;
    private LocalDate[] dates = new LocalDate[3]; // contains the start date, end date and the registration closing date;
    private String school;
    private String location;
    private String description;

    private int slots;
    private int comitteeSlots;
    private int occupiedSlots = 0; // the camp manager should make sure that this never exceeds its limit;
    private int occupiedComitteeSlots = 0; // the camp manager should make sure that this never exceeds its limit;

    private Staff staffIncharge; // the staff that created this camp;


    // For a good user experience;
    private String note = "We hope to see you there !!";

////////////////////////////////////////// Constructors

    public CampInfo(String name, LocalDate startDate, LocalDate endDate, LocalDate registrationClosingDate,
                    String school, String location, String description,
                    int slots, int comitteeSlots, 
                    Staff staffIncharge) 
                {
                    this.name = name;

                    this.dates[0] = startDate;
                    this.dates[1] = endDate;
                    this.dates[2] = registrationClosingDate;

                    this.school = school;
                    this.location = location;
                    this.description = description;
                    this.slots = slots;
                    this.staffIncharge = staffIncharge;
                    
                    setComitteeSlots(comitteeSlots);
                }

    public CampInfo (String[] info, Staff staffIncharge) {

        boolean flag = false;
        List<String> attention = new ArrayList<>();

        this.name = info[0];

        try {
            this.dates[0] = LocalDate.parse(info[1]);
        }

        catch (DateTimeParseException e) {
            this.dates[0] = LocalDate.now();
            flag = true;
            attention.add("Start Date");
        }

        try {
            this.dates[1] = LocalDate.parse(info[2]);
        }

        catch (DateTimeParseException e) {
            this.dates[1] = LocalDate.now();
            flag = true;
            attention.add("End Date");
        }

        try {
            this.dates[2] = LocalDate.parse(info[3]);
        }

        catch (DateTimeParseException e) {
            this.dates[2] = LocalDate.now();
            flag = true;
            attention.add("Registration Closing Date");
        }

        this.school = info[4].toUpperCase();

        this.location = info[5];
        this.description = info[6];
        this.staffIncharge = staffIncharge;
        
        try {
            this.slots = Integer.parseInt(info[7]);
        }

        catch (NumberFormatException e) {
            this.slots = 0;
            flag = true;
            attention.add("Total Slots");
        }

        try {
            setComitteeSlots(Integer.parseInt(info[8]));
        }

        catch (NumberFormatException e) {
            setComitteeSlots(0);
            flag = true;
            attention.add("Comittee Slots");
        }

        if(flag) {
            System.out.println(Highlights.bold("The following require your immediate attention: "));
            for(var label : attention) {
                System.out.println(Highlights.bold("- " + label));
            }
        }
    }

//////////////////////////////////////////////////// Getters
    public String getName() {return name;}
    public LocalDate[] getDates() {return dates;}
    public LocalDate getRegistrationClosingDate() {return dates[2];}
    public String getSchool() {return school;}
    public String getLocation() {return location;}
    public String getDescription() {return description;}
    public String getNote() {return note;}
    
    public int getAvailableSlots() {return slots - occupiedSlots;}
    public int getAvailableComitteeSlots() {return comitteeSlots - occupiedComitteeSlots;}

    public Staff getStaffIncharge() {return staffIncharge;}

///////////////////////////////////////////////////// Setters;
    public void setName(String newName) {name = newName;}
    public void setRegistrationClosingDate(LocalDate newRegistrationClosingDate) {dates[2] = newRegistrationClosingDate;}
    public void setSchool(String newSchool) {school = newSchool;}
    public void setLocation(String newLocation) {location = newLocation;}
    public void setDescription(String newDescription) {description = newDescription;}

    public void setSlots(int newSlots) {slots = newSlots;}

    // these functions modify the slots;
    public void incrementOccupiedSlots() {occupiedSlots ++;}
    public void incrementOccupiedComitteeSlots() {occupiedComitteeSlots++;}
    public void decrementOccupiedSlots() {occupiedSlots--;}
    public void decrementOccupiedComitteeSlots() {occupiedComitteeSlots--;}

    public void setCustomNote(String newNote) {note = newNote;}

    public void setCampDuration(LocalDate startDate, LocalDate endDate) {
        dates[0] = startDate;
        dates[1] = endDate;
    } 

    public void setComitteeSlots(int newComitteeSlots) {
        comitteeSlots = newComitteeSlots <= maxComitteeSlots ? newComitteeSlots : maxComitteeSlots;
    }


//////////////////////////////// Miscellaneous;

    @Override
    public String toString() {

        return 
        title("Camp") + getName() + "\n" + 
        title("Duration")  + viewCampDuration()  + "\n" +
        title("Registration Closing Date") + viewRegistrationClosingDate() + "\n" + 
        title("Open to") + getSchool() + "\n" + 
        seperator() + "\n" + 

        title("Location") + getLocation() + "\n\n" +
        title("Description") + getDescription() + "\n" +
        seperator() + "\n" + 

        title("Available Slots") + getAvailableSlots() + "\n" + 
        title("Available Comittee Slots") + getAvailableComitteeSlots() + "\n" + 
        seperator() + "\n" + 

        title("Hosted By") + "\n" + 
        getStaffIncharge().toString() + "\n" +  
        seperator() + "\n" +  

        getNote() + "\n" + 
        seperator() + "\n";
    }

    // These are just some helper functions;
    // To be revised later;
    public String viewCampDuration() {
        return formatDate(dates[0]) + " - " + formatDate(dates[1]);
    }

    public String viewRegistrationClosingDate() {
        return formatDate(getRegistrationClosingDate());
    }

    private String formatDate(LocalDate d) {
        return d.getDayOfMonth() + " " + d.getMonth() + " " + d.getYear();
    }

    private String seperator() { // we can move this to the menu class later;
        return "-".repeat(150);
    }

    private String title(String t) {
        String Highlight = "\u001B[32m";
        String Reset = "\u001B[0m";

        return Highlight + t + ": " + Reset;
        
    }


    public String reportString() {

        return 
        "Camp: " + getName() + "\n" + 
        "Duration: "  + viewCampDuration()  + "\n" +
        "Registration Closing Date: " + viewRegistrationClosingDate() + "\n" + 
        "Open to: " + getSchool() + "\n" + 
        seperator() + "\n" + 

        "Location: " + getLocation() + "\n" + "\n" + 
        "Description: " + getDescription() + "\n" +
        seperator() + "\n" + 

        "Available Slots: " + getAvailableSlots() + "\n" + 
        "Available Comittee Slots: " + getAvailableComitteeSlots() + "\n" + 
        seperator() + "\n" + 

        "Hosted By: " + "\n" + 
        getStaffIncharge().reportString() + "\n" +  
        seperator() + "\n" + "\n" + 
        seperator() + "\n";
    }
}
