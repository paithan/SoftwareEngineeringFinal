import java.util.*;
import java.lang.*;

/**
 * Starting code for Kyle's 4140 Final Exam, Fall 2015.
 *
 * @author Kyle Burke <paithanq@gmail.com>
 */
public class RiverDiscoverer {

    /**
     * Main method to run this program.  
     *
     * @param args  Unused in this code.
     */
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        ArrayList<Integer> lengthsBetweenConvergences = new ArrayList<Integer>();
        ArrayList<String> convergenceNames = new ArrayList<String>();
        ArrayList<Integer> riverFlows = new ArrayList<Integer>(); //amount of flow on the main branch at each part of the river.
        
        //get the main river's name
        System.out.println("You have found the mouth of a new river where it spills into the sea.  What do the locals call this river?");
        String riverName = input.nextLine();
        convergenceNames.add(riverName);
        
        //measure the amount of flow
        while (true) {
            System.out.println("How much water is flowing into the sea?  (L/s)");
            try {
                int flow = Integer.parseInt(input.nextLine());
                riverFlows.add(flow);
                break;
            } catch (NumberFormatException e) {
                System.out.println("I didn't understand that!");
            }
        }
        
        while (true) {
            System.out.println("How far did you walk until you reached the next junction? (in km)");
            try {
                int length = Integer.parseInt(input.nextLine());
                lengthsBetweenConvergences.add(length);
            } catch (NumberFormatException e) {
                System.out.println("I didn't understand that!");
                break;
            }
            System.out.println("Are you done traveling upstream on the " + riverName + "? (y/N)");
            if (input.nextLine().equals("y")) {
                System.out.println("Great!  We're done!");
                break;
            } else {
                System.out.println("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
                String tributaryName = input.nextLine();
                convergenceNames.add(tributaryName);
                
                System.out.println("How much water is flowing from that tributary into the main river?  (L/s)");
                while (true) {
                    try {
                        int flow = Integer.parseInt(input.nextLine());
                        int previousFlow = riverFlows.get(riverFlows.size() - 1);
                        riverFlows.add(previousFlow - flow);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("I didn't understand that!");
                    }
                }
            }
            
        }
        
        //now print the results
        System.out.println("You have now explored the " + riverName + "!");
        
        //calculate the total length
        int totalLength= 0;
        
        for (Integer length : lengthsBetweenConvergences) {
            totalLength += length;
        }
        
        System.out.println("The river is at least " + totalLength + " km long.");
        System.out.println("From the furthest distance we explored:");
        
        System.out.println(riverFlows.get(riverFlows.size() - 1) + " L/s flows " + lengthsBetweenConvergences.get(riverFlows.size() - 1) + " km downstream.");
        System.out.println("Then, the " + convergenceNames.get(riverFlows.size() - 1) + " flows into the river."); 
        
        for (int i = riverFlows.size() - 2; i > 0; i--) {
            System.out.println("Then " + riverFlows.get(i) + " L/s flows " + lengthsBetweenConvergences.get(i) + " km downstream.");
            System.out.println("Then, the " + convergenceNames.get(i) + " flows into the river."); 
        }
        System.out.println(riverFlows.get(0) + " L/s flows " + lengthsBetweenConvergences.get(0) + " km downstream.");
        System.out.println("Then the " + riverName + " flows into the sea.");
        
        
    }


}
