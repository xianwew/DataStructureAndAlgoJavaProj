import student.TestCase;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class was designed to test the GraphProject
 *
 * @author <Put something here>
 * @version <Put something here>
 */
public class GraphProjectTest
    extends TestCase
{
    // ----------------------------------------------------------
    /**
     * Read contents of a file into a string
     * @param path File name
     * @return the string
     * @throws IOException
     */
    static String readFile(String path)
        throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * Set up the tests that follow.
     */
    public void setUp()
    { // Nothing needed yet

    }

    public void testGraphProject() {
        String filePath = "src/CustomOutput.txt";

        String refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            refOut = new String(bytes, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        systemOut().clearHistory();
        GraphProject.main(new String[] { "10", "src/P4sampleInput.txt" });
        String printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        filePath = "src/CustomOutput2.txt";
        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                refOut = new String(bytes, StandardCharsets.UTF_8);
            }
        catch (IOException e) {
            e.printStackTrace();
        }

        systemOut().clearHistory();
        GraphProject.main(new String[] { "10", "src/P4sampleInput2.txt" });
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        filePath = "src/CustomOutput3.txt";
        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                refOut = new String(bytes, StandardCharsets.UTF_8);
            }
        catch (IOException e) {
            e.printStackTrace();
        }

        systemOut().clearHistory();
        GraphProject.main(new String[] { "10", "src/P4InsertInput.txt" });
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        filePath = "src/CustomOutput4.txt";
        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                refOut = new String(bytes, StandardCharsets.UTF_8);
            }
        catch (IOException e) {
            e.printStackTrace();
        }

        systemOut().clearHistory();
        GraphProject.main(new String[] { "10", "src/P4DeleteInput.txt" });
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        filePath = "src/CustomOutput5.txt";
        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                refOut = new String(bytes, StandardCharsets.UTF_8);
            }
        catch (IOException e) {
            e.printStackTrace();
        }

        systemOut().clearHistory();
        GraphProject.main(new String[] { "2", "src/P4InsertInput.txt" });
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
    }
    
    
    /**
     * This method is simply to get code coverage of the class declaration.
     */
    public void testQInit()
    {
        GraphProject it = new GraphProject();
        assertNotNull(it);
    }
}
