import java.util.*;
import java.util.concurrent.*;
import java.lang.reflect.*;
import java.lang.*;
import java.io.*;

/**
 * A class to test sequences of input and output.  Important: in order to use this tester, the class being tested can only have a single Scanner created using System.in at a time.
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
    
    //the number of correct outputs
    private int numCorrect;
    
    //the number of output attempts
    private int numOutputs;
    
    //the console output, hidden away during testing
    private PrintStream oldStdOut;
    
    /**
     * Class constructor.
     */
    public IOTester(Class c) {
        this.exchanges = new ArrayList<ExchangeString>();
        //this.outputs = new ArrayList<String>();
        this.inputBuilder = new StringBuilder();
        this.results = new StringBuilder();
        this.toTest = c;
        this.numCorrect = 0;
        this.numOutputs = 0;
        this.oldStdOut = System.out;
    }
    
    /**
     * Adds a correct output.
     */
    public void tallyCorrectOutput() {
        this.numCorrect ++;
        this.numOutputs ++;
    }
    
    /**
     * Adds an incorrect output.
     */
    public void tallyIncorrectOutput() {
        this.numOutputs ++;
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
     * Writes to the user during testing.
     */
    public void printDuringTesting(String s) {
        this.oldStdOut.println(s);
        //System.err.println(s);
    }
    
    /**
     * Runs the tester.
     */
    public void run() {
        this.oldStdOut = System.out;
        InputStream oldStdIn = System.in;
        OutputStream newOut = null;
        InputStream inFromNewOut = null;
        BufferedReader reader;
        
        /*
        System.out.println("*~~~~~~~~~*");
        System.out.println("This is the combined string that will be sent to the IO program:\n" + this.inputBuilder.toString());
        System.out.println("*~~~~~~~~~*");
        /* */
        byte[] bytes = this.inputBuilder.toString().getBytes();
        
        //this will prevent the streams from being closed prematurely
        //final Semaphore semaphore = new Semaphore(1);
        try {
        
            //set up the new standard in with the input strings all put together.
            //this will communicate the input from this tester to the program being tested
            
            InputStream newIn = new ByteArrayInputStream(bytes);
            //newIn = new ByteArrayInputStream("Hi\nI\nAm\nNot\nSure\nWhat's\nGoing\nOn!".getBytes());
            System.setIn(newIn);
            
            
            /*
            //set up a new output stream that will pipe into an input stream
            newOut = new PipedOutputStream();
            inFromNewOut = new PipedInputStream(newOut); //now this is connected.
            reader = new BufferedReader(new InputStreamReader(inFromNewOut));
            System.setOut(new PrintStream(newOut, true));
            /* */
            
            
            
            //the piping didn't work, so we're going to try using a file as the intermediary.
            //this will hold the output from the tested program to be read by this.
            String fileName = "TesterTempMonkeyFile.txt";
            File tempFile = new File(fileName);
            tempFile.createNewFile(); //create the file if it doesn't exist
            tempFile.setReadable(true);
            tempFile.setWritable(true);
            //clear the contents of the file, a la Vlad's answer here: https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
            PrintWriter writer = new PrintWriter(tempFile);
            writer.print("");
            writer.close();
            //tempFile.createNewFile();
            //tempFile.deleteOnExit();
            newOut = new FileOutputStream(tempFile, true);
            System.setOut(new PrintStream(newOut, true));
            //set the reader up to read from the file
            reader = new BufferedReader(new FileReader(fileName));
            /* */
            
            //semaphore.acquireUninterruptibly();
            /*
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
                        results.append("Failure: Something went wrong here!  " + t.getMessage());
                        t.printStackTrace();
                        //System.err.println("Results: \n" + results);
                        //t.printStackTrace();
                    }
                    semaphore.release();
                }
            };
            new Thread(mainToTest).start();
            /* */
            
            //now run the code (hopefully) writing out to the file
            try {
                Method[] methods = toTest.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals("main")) {
                        method.invoke(toTest, new Object[] {new String[0]});
                    }
                }
            } catch (Throwable t) {
                System.err.println("Something went wrong while processing the inputs!");
                results.append("Failure: Something went wrong here!  " + t.getMessage());
                t.printStackTrace();
                //System.err.println("Results: \n" + results);
                //t.printStackTrace();
            }
            
            
            for (ExchangeString testString : this.exchanges) {
                this.results.append(testString.test(reader, this) + "\n");
            }
            this.results.append("Test Complete! \n");
        
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Aaaaaaah!");
        }
        
        //semaphore.acquireUninterruptibly();
        
        /*
        try {
            //close the new piped streams
            System.err.println("CLOSING THE STREAMS NOW!");
            newOut.close();
            inFromNewOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        
        //put the old standard in and out back
        System.setOut(oldStdOut);
        System.setIn(oldStdIn);
        
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~*\n");//Results: \n" + this.results);
        System.out.println("Total parts correct: " + this.numCorrect + "/" + this.numOutputs);
        
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
        public abstract String test(BufferedReader reader, IOTester tester);
        
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
        public String test(BufferedReader reader, IOTester tester) {
            String outputDuringTesting = "Program outputs: ";
            String returnString;
            String line = "    No output; reader.readLine() failed!";
            try {
                line = reader.readLine();
                //line = line.trim();
            } catch (Exception e) {
                tester.tallyIncorrectOutput();
                String exceptionFeedback = "Failure: Exception: " + e.getMessage() + "\n" +
                                           "         Expected: \"" + this.expected + "\"";
                outputDuringTesting += "EXCEPTION: " + e.getMessage() + "\n";
                e.printStackTrace();
                returnString = exceptionFeedback;
            }
            outputDuringTesting += line + "  ";
            //if (!this.expected.trim().equals(line)) {
            if (!this.expected.equals(line)) {
                tester.tallyIncorrectOutput();
                outputDuringTesting += "(Incorrect)";
                returnString = "Incorrect: Expected: \"" + this.expected + "\" \n" +
                                "                Got: \"" + line + "\"";
            } else {
                tester.tallyCorrectOutput();
                outputDuringTesting += "(Correct)";
                returnString = "Correct: \"" + line + "\"";
            }
            tester.printDuringTesting(outputDuringTesting);
            return returnString;
            
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
        public String test(BufferedReader reader, IOTester tester) {
            /*
            InputStream oldStdIn = System.in;
            //set up the new standard in with the input strings all put together.
            StringBufferInputStream newIn = new StringBufferInputStream(this.userInput);
            System.setIn(newIn);
            //reset to the real stdIn
            System.setIn(oldStdIn);
            */
            tester.printDuringTesting("Sim-User types: " + this.userInput);
            try {
                //TODO: why do we have to sleep?
                //Thread.sleep(300);
            } catch (Exception e) {
                System.err.println("Thread.sleep failed in InputString.test!");
                return test(reader, tester);
            }
            return "  Input: \"" + this.userInput + "\"";
        }
        
    
    } //end of InputString
    


} // end of IOTester class
