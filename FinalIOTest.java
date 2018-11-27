/**
 * A test for BetterRiverDiscoverer.  For the CS 4140 final exam.
 *
 * @author Kyle Burke <paithanq@gmail.com>
 */
public class FinalIOTest {

    public static void main(String[] args) {
        System.out.println("Testing BetterRiverDiscoverer on the IO from the Final Project webpage...");
        IOTester tester = new IOTester(BetterRiverDiscoverer.class);
        //IOTester tester = new IOTester(BackwardsIOTest.class);
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
        tester.addOutput("Are you going to explore the tributary? (y/N)");
        tester.addInput("n");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("400");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
        tester.addInput("White Nile");
        tester.addOutput("How much water is flowing from that tributary into the main river?  (L/s)");
        tester.addInput("878000");
        tester.addOutput("Are you going to explore the tributary? (y/N)");
        tester.addInput("y");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("1200");
        tester.addOutput("Are you done traveling upstream on the White Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
        tester.addInput("Sobat River");
        tester.addOutput("How much water is flowing from that tributary into the main river?  (L/s)");
        tester.addInput("412000");
        tester.addOutput("Are you going to explore the tributary? (y/N)");
        tester.addInput("n");
        tester.addOutput("Are you done traveling upstream on the White Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("200");
        tester.addOutput("Are you done traveling upstream on the White Nile? (y/N)");
        tester.addInput("y");
        tester.addOutput("Finished exploring the White Nile.");
        tester.addOutput("Returning 1400 km back to the Nile...");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("700");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("That means you must be at another confluence.  What is the name the locals have given the river flowing into this one?");
        tester.addInput("Dinder River");
        tester.addOutput("How much water is flowing from that tributary into the main river?  (L/s)");
        tester.addInput("112000");
        tester.addOutput("Are you going to explore the tributary? (y/N)");
        tester.addInput("n");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("n");
        tester.addOutput("How far did you walk until you reached the next junction? (in km)");
        tester.addInput("750");
        tester.addOutput("Are you done traveling upstream on the Nile? (y/N)");
        tester.addInput("y");
        tester.addOutput("Finished exploring the Nile.");
        tester.addOutput("Great!  We're done!");
        tester.addOutput("Nile, at the furthest upstream we explored, dumps 1466000 L/s");
        tester.addOutput("This flows 750 km downstream.");
        tester.addOutput("Then, tributary:");
        tester.addOutput("    Dinder River, at the furthest upstream we explored, dumps 112000 L/s");
        tester.addOutput("    In total, we explored 0 km of the Dinder River.");
        tester.addOutput("The Nile now flows 1578000 L/s.");
        tester.addOutput("This flows 700 km downstream.");
        tester.addOutput("Then, tributary:");
        tester.addOutput("    White Nile, at the furthest upstream we explored, dumps 466000 L/s");
        tester.addOutput("    This flows 200 km downstream.");
        tester.addOutput("    Then, tributary:");
        tester.addOutput("        Sobat River, at the furthest upstream we explored, dumps 412000 L/s");
        tester.addOutput("        In total, we explored 0 km of the Sobat River.");
        tester.addOutput("    The White Nile now flows 878000 L/s.");
        tester.addOutput("    This flows 1200 km downstream.");
        tester.addOutput("    In total, we explored 1400 km of the White Nile.");
        tester.addOutput("The Nile now flows 2456000 L/s.");
        tester.addOutput("This flows 400 km downstream.");
        tester.addOutput("Then, tributary:");
        tester.addOutput("    Atbarah River, at the furthest upstream we explored, dumps 374000 L/s");
        tester.addOutput("    In total, we explored 0 km of the Atbarah River.");
        tester.addOutput("The Nile now flows 2830000 L/s.");
        tester.addOutput("This flows 3000 km downstream.");
        tester.addOutput("In total, we explored 4850 km of the Nile.");
        tester.addOutput("Then the Nile flows into the sea.");
        tester.run();
    }

} //end of FinalIOTest
