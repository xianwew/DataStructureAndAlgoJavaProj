import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class SemSearchTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testMInitx()
    {
        SemSearch sem = new SemSearch();
        assertNotNull(sem);
        SemSearch.main(null);
        
        String filePath = "src/P2Sample_output.txt"; 

        String refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            refOut = new String(bytes, StandardCharsets.UTF_8);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        SemSearch.main(new String[]{"128", "src/P2Sample_input.txt"});
        String printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        //
        filePath = "src/P2syntaxInsert_output.txt"; 

        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            refOut = new String(bytes, StandardCharsets.UTF_8);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        systemOut().clearHistory();
        SemSearch.main(new String[]{"128", "src/P2syntaxInsert_input.txt"});
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        //	
        filePath = "src/P2Sample_output2.txt"; 

        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            refOut = new String(bytes, StandardCharsets.UTF_8);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        systemOut().clearHistory();
        SemSearch.main(new String[]{"128", "src/P2Sample_input2.txt"});
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
        
        
        //	
        filePath = "src/P2Sample_output3.txt"; 

        refOut = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            refOut = new String(bytes, StandardCharsets.UTF_8);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        systemOut().clearHistory();
        SemSearch.main(new String[]{"1024", "src/P2Sample_input2.txt"});
        printOut = systemOut().getHistory();
        assertFuzzyEquals(printOut, refOut);
    }
}

