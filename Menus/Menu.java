package Menus;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

import Design.Highlights; 

public class Menu {
    private List<String> options = new ArrayList<>();  
    
    public Menu(List<String> ops) {
        options = new ArrayList<>(ops);
    }

    public Menu(Menu m, List<String> newOps) {
        options = new ArrayList<>(m.options);
        for(var ops : newOps) 
            options.add(ops);
    }

    public void display() {
        
        System.out.println("-".repeat(150));

        for(int i = 0; i < options.size(); ++i) {
            String curOption = i +  ") " + options.get(i);
            System.out.println(Highlights.bold(curOption));
        }

        System.out.println("-".repeat(150));
        Menu.askChoice();
    }

    public static int quitOption() {return 0;}

    public static void askChoice() {
        System.out.print("Your Choice: ");
    }

    public static void invalidOptionMessage() {
        System.out.println(Highlights.err("Invalid Option"));
    }

    public static void main(String[] args) {
        Menu m = new Menu(Arrays.asList("Login","Register"));
        m.display();
    }

}
