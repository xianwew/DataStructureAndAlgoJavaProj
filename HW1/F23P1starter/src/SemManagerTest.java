/**
 * This class contains a set of unit tests for the `MyHashTable` class.
 * @author xianwei&jiren
 * @version September 2023, updated September 2023
 */

import student.TestCase;
/**
 * Test the SemManager class
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