package Design;
public class Highlights {
    
    private static final String green = "\u001B[32m";
    private static final String red = "\u001B[31m";
    private static final String reset = "\u001B[0m";

    private static final String bold = "\033[1m";
    private static final String italics = "\u001b[3m";
    private static final String reset1 = "\033[0m";

    public static String title(String s) {
        return green + s + ": " + reset; 
    }

    public static String err(String s) {
        return red + s + reset; 
    }

    public static String highlight(String s) {
        return green + s + reset; 
    }

    public static String highlight1(String s) {
        return "\u001B[15m" + s + reset;
    }

    public static String bold(String s) {
        return bold + s + reset1;
    }

    public static String italicize(String s) {
        return italics + s + reset1;
    }
}   
