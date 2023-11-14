import student.TestCase;
import java.io.*;
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


    /**
     * This method is simply to get code coverage of the class declaration.
     */
    public void testQInit()
    {
        GraphProject it = new GraphProject();
        assertNotNull(it);
    }
}
