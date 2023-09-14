import student.TestCase;
/**
 * Test the SemManager class
 * 
 * @author Xianwei Wu/Jiren Wang
 * @version September 2023, updated September 2023
 */
public class SemManagerTest extends TestCase {
    /**
     * Sets up the tests that follow. 
     * In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }

    /**
     * Get code coverage of the class declaration.
     */
    public void testMInitx() {
        SemManager sem = new SemManager();
        assertNotNull(sem);
        SemManager.main(null);
        SemManager.main(new String[] {
            "512", "4", "src/P1Sample_input.txt" });
    }
}