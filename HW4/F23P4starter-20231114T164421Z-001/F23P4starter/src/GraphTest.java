import student.TestCase;
/**
 * GraphTest class
 *
 * @author <Xianwei && Jiren>
 * @version <Nov, 2023>
 */
public class GraphTest extends TestCase {
    /**
     * Test method for the trimZeros method in the Graph class.
     */
    public void testTrimZeros() {
        Graph tmp = new Graph(10);
        int[][] matrix1 = { { 1, 2, 3, 0 }, { 4, 5, 6, 0 }, { 7, 8, 9, 0 },
            { 0, 0, 0, 0 } };
        int[][] matrix2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] matrix3 = tmp.trimZeros(matrix1);
        assertEquals(true, areMatricesEqual(matrix3, matrix2));
    }

    /**
     * Compares two matrices to check for equality.
     * 
     * @param matrix1 first matrix to compare
     * @param matrix2 second matrix to compare
     * @return true if matrices are equal, otherwise false
     */
    public boolean areMatricesEqual(int[][] matrix1, int[][] matrix2) {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
