import java.util.*;
import java.lang.reflect.*;
import java.lang.*;
import java.io.*;

/**
 * A class to test sequences of input and output.  
 *
 * @author Kyle Burke
 */
public class IOTester {

    //IO strings in a use case.  The first string is expected output from the program, followed by input from the user, then expected output, etc.
    private List<ExchangeString> exchanges;
    
    
    //input builder for the inputs
    private StringBuilder inputBuilder;
    
    //store the results
    private final StringBuilder results;
    
    //the class whose main method we'll call
    private final Class toTest;
    
    /**
     * Class constructor.
     */
    public IOTester(Class c) {
        this.exchanges = new ArrayList<ExchangeString>();
        //this.outputs = new ArrayList<String>();
        this.inputBuilder = new StringBuilder();
        this.results = new StringBuilder();
        this.toTest = c;
    }
    
    /**
     * Adds an input string from a use case.
     */
    public void addInput(String string) {
        this.exchanges.add(new InputString(string));
        this.inputBuilder.append(string + "\n");
    }
    
    /**
     * Adds an expected output from the program.
     */
    public void addOutput(String string) {
        this.exchanges.add(new OutputString(string));
        //this.outputs.add(string);
    }
    
    /**
     * Runs the tester.
     */
    public void run() {
        PrintStream oldStdOut = System.out;
        InputStream oldStdIn = System.in;
        try {
            //set up the new standard in with the input strings all put together.
            //StringBufferInputStream newIn = new StringBufferInputStream(this.userInput);
            StringBufferInputStream newIn = new StringBufferInputStream(this.inputBuilder.toString());
            System.setIn(newIn);
            
            //set up a new output stream that will pipe into an input stream
            PipedOutputStream newOut = new PipedOutputStream();
            InputStream inFromNewOut = new PipedInputStream(newOut); //now this is connected.
            BufferedReader reader = new BufferedReader(new InputStreamReader(inFromNewOut));
            System.setOut(new PrintStream(newOut, true));
            
    
            Runnable mainToTest = new Runnable() {
                public void run() {
                    //choose and run the main method
                    try {
                        Method[] methods = toTest.getDeclaredMethods();
                        for (Method method : methods) {
                            if (method.getName().equals("main")) {
                                method.invoke(toTest, new Object[] {new String[0]});
                            }
                        }
                    } catch (Throwable t) {
                        System.err.println("Something went wrong while reflecting!  Perhaps there weren't enough inputs supplied!");
                        results.append("Failure: Something went wrong here!");
                        System.err.println("Results: \n" + results);
                        //t.printStackTrace();
                    }
                }
            };
            new Thread(mainToTest).start();
            
            for (ExchangeString testString : this.exchanges) {
                this.results.append(testString.test(reader) + "\n");
            }
            this.results.append("Test Complete! \n");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Aaaaaaah!");
        }
        
        //put the old standard in and out back
        System.setOut(oldStdOut);
        System.setIn(oldStdIn);
        
        System.out.println("Results: \n" + this.results);
        
    }
    
    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
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
    
    
    //Either an input or output string
    private static abstract class ExchangeString {
        
        //test method
        public abstract String test(BufferedReader reader);
        
    } //end of ExchangeString
    
    //An expected output String
    private static class OutputString extends ExchangeString {
    
        //String we're expecting to see
        private String expected;
    
        //constructor
        public OutputString(String string) {
            this.expected = string;
        }
        
        //test method
        public String test(BufferedReader reader) {
            try {
                String line = reader.readLine();
                if (!line.trim().equals(this.expected.trim())) {
                    return "Failure! Expected: \"" + this.expected + "\" but got \"" + line + "\" instead!";
                } else {
                    return line;
                }
            } catch (Exception e) {
                return "Failure: Exception when I expected \"" + this.expected + "\".";
            }
        }
    } //end of OutputString
    
    //An Input String from a use case
    private static class InputString extends ExchangeString {
        
        //String we're going to simulate
        private String userInput;
        
        //constructor
        public InputString(String string) {
            this.userInput = string;
        }
        
        //test method
        public String test(BufferedReader reader) {
            /*
            InputStream oldStdIn = System.in;
            //set up the new standard in with the input strings all put together.
            StringBufferInputStream newIn = new StringBufferInputStream(this.userInput);
            System.setIn(newIn);
            //reset to the real stdIn
            System.setIn(oldStdIn);
            */
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.err.println("Thread.sleep failed in InputString.test!");
                return test(reader);
            }
            return this.userInput;
        }
        
    
    } //end of InputString
    


} // end of IOTester class
