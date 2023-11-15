import student.TestCase;

/**
 * @author <Put something here>
 * @version <Put something here>
 */
public class HashTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.getHomeSlot("a", 10000) == 97);
        assertTrue(Hash.getHomeSlot("b", 10000) == 98);
        assertTrue(Hash.getHomeSlot("aaaa", 10000) == 1873);
        assertTrue(Hash.getHomeSlot("aaab", 10000) == 9089);
        assertTrue(Hash.getHomeSlot("baaa", 10000) == 1874);
        assertTrue(Hash.getHomeSlot("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.getHomeSlot("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.getHomeSlot("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.getHomeSlot("long Lonesome Blues", 10000) == 4667);
    }
}
