/**
 * An initial test for the RiverDiscoverer.  For the CS 4140 final exam.
 *
 * @author Kyle Burke <paithanq@gmail.com>
 */
public class InitialIOTest {

    public static void main(String[] args) {
        System.out.println("Testing RiverDiscoverer on the initial IO from the Final Project webpage...");
        IOTester tester = new IOTester(RiverDiscoverer.class);
        tester.addOutput("You have found the mouth of a new river where it spills into the sea.  What do the locals call this river?");
        tester.addInput("Nile");
        tester.addOutput("How much water is flowing into the sea?  (L/s)");
        tester.addInput("2830000");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("3000");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
        tester.addInput("Atbarah River");
        tester.addOutput("How much water is flowing from that tributary into the main river?  (L/s)");
        tester.addInput("374000");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("400");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
        tester.addInput("White Nile");
        tester.addOutput("How much water is flowing from that tributary into the main river?  (L/s)");
        tester.addInput("878000");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("700");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
        tester.addInput("Dinder River");
        tester.addOutput("How much water is flowing from that tributary into the main river?  (L/s)");
        tester.addInput("112000");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("750");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("y");
        tester.addOutput("Great!  We're done!");
        tester.addOutput("You have now explored the Nile!");
        tester.addOutput("The river is at least 4850 km long.");
        tester.addOutput("From the furthest distance we explored:");
        tester.addOutput("1466000 L/s flows 750 km downstream.");
        tester.addOutput("Then, the Dinder River flows into the river.");
        tester.addOutput("Then 1578000 L/s flows 700 km downstream.");
        tester.addOutput("Then, the White Nile flows into the river.");
        tester.addOutput("Then 2456000 L/s flows 400 km downstream.");
        tester.addOutput("Then, the Atbarah River flows into the river.");
        tester.addOutput("2830000 L/s flows 3000 km downstream.");
        tester.addOutput("Then the Nile flows into the sea.");    
        tester.run();
    }

} //end of InitialIOTest
