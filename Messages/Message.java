package Messages;
import Design.Highlights;

public class Message {

    private static int center = 70;
    private static int dividerLength = 150;

    ////////////////  MAIN Messages;
    
    public static void openingMessage() {
        System.out.println(Highlights.bold("Welcome to CAMS !".indent(center)));
        divider();
    }

    public static void closingMessage() {
        System.out.println(Highlights.bold("It was a Pleasure :)"));
    }

    public static void createCampMessage() {
        System.out.println(Highlights.bold("Creating a New Camp....".indent(center)));
    }

    public static void createEnquiryMessage() {
        System.out.println(Highlights.bold("Creating a new Enquiry ... ".indent(center)));
    }

    public static void createEnquiryReplyMessage() {
        System.out.println(Highlights.bold("Creating a new Reply ...".indent(center)));
    }

    public static void createSuggestionMessage() {
        System.out.println(Highlights.bold("Creating a Suggestion...").indent(center));
    }

    public static void divider() {
        System.out.println("-".repeat(dividerLength));
    }

    public static void invalidInputMessage() {
        divider();
        System.out.println(Highlights.err("Yikes! Looks like you've pressed the wrong key"));
    }

    public static void confirmActionsMessage() {
        System.out.print(Highlights.bold("Are you sure you would like to proceed (Y/N) : "));
    }

    public static void campEditedSuccessfullyMessage(String campName, String detail) {
        System.out.printf(Highlights.highlight("%s's %s was edited successfully\n"),
        campName, detail);
    }

    public static void standardErrorMessage() {
        divider();
        System.out.println(Highlights.err("Sorry, something went wrong"));
    }

    /////////// Reception Messages
    public static void invalidCampIndexMessage() {
        divider();
        System.out.println(Highlights.err("This is an invalid Camp option"));
    }

    public static void invalidEnquiryIndexMessage() {
        divider();
        System.out.println(Highlights.err("This is an invalid Enquiry option"));
    }

    public static void invalidSuggestionIndexMessage() {
        divider();
        System.out.println(Highlights.err("This is an invalid Suggestion option"));
    }

    public static void invalidDateMessage() {
        System.out.println(Highlights.err("You've got to enter the date in the right format"));
    }

    public static void campComitteeWelcomeMessage1(String campName) {
        System.out.printf(Highlights.highlight("Welcome to %s Camp Comittee!\n"),
        campName);
    }

    public static void campComitteeWelcomeMessage2() {
        System.out.println(Highlights.highlight("We're thrilled to see what you bring to the table!"));
    }

    public static void campComitteeErrorMessage() {
        Highlights.err("Cannot Apply for Comittee Member Position \nYou are already in the Comittee of another Camp");
    }

    public static void successfulDeregisterMessage(String campName) {
        System.out.printf(Highlights.highlight("Successfully Deregistered from %s\n"),
        campName);
    }

    public static void comitteeMemberDeregisterErrorMessage() {
        System.out.println(Highlights.err("Nice Try. You are bound to this camp"));
    }

    public static void enquiryHasBeenAnsweredMessage() {
        System.out.println(Highlights.highlight("Dont't sweat it. This enquiry has already been answered."));
    }   

    public static void alreadyRegisteredMessage(String campName) {
        System.out.printf(Highlights.highlight("Dont sweat it. You've already Registered for %s\n"), 
        campName);
    }

    public static void generateReportErrorMessage(String reportName) {
        System.out.println(Highlights.err("Something went wrong"));
        System.out.printf(Highlights.err("Couldn't generate %s\n"),
        reportName);
    }

    public static void betrayerMessage(String betrayer) {
        System.out.println(Highlights.bold("Well, Well, Well..."));
        System.out.printf(Highlights.bold("If it isn't our dear %s\n"), betrayer);
        System.out.println(Highlights.bold("We're sorry, we cant let you back in."));
    }

    public static void updatingCampDataMessage() {
        System.out.println(Highlights.bold("Updating Camp Details..."));
    }
}
