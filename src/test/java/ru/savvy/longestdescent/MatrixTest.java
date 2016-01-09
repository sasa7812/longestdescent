package ru.savvy.longestdescent;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class MatrixTest {

    @Test
    public void testFromFile() throws Exception {
        Matrix matrixF = Matrix.fromFile(ClassLoader.getSystemResourceAsStream("map.txt"));
        assertEquals(1000, matrixF.getWidth());
        assertEquals(1000, matrixF.getHeight());
        assertEquals(1460, matrixF.get(999_999).getHeight());
        assertEquals(50, matrixF.get(0).getHeight());
    }

    @Test
    public void testGoDirections() throws Exception {
        Matrix matrixF = Matrix.fromFile(ClassLoader.getSystemResourceAsStream("map.txt"));
        assertEquals(null, matrixF.goNorth(0));
        assertEquals(null, matrixF.goWest(0));
        assertEquals(new Integer(1000), matrixF.goSouth(0));
        assertEquals(new Integer(1), matrixF.goEast(0));
        assertEquals(null, matrixF.goSouth(999_999));
        assertEquals(null, matrixF.goEast(999_999));
        assertEquals(Integer.valueOf(999_998), matrixF.goWest(999_999));
        assertEquals(Integer.valueOf(998_999), matrixF.goNorth(999_999));
    }

    @Test
    public void testSnakeFill() throws Exception {
        Matrix matrixS = Matrix.snakeFill(1000, 1000);
        assertEquals(999, matrixS.get(999).getHeight());
        assertEquals(1999, matrixS.get(1000).getHeight());
        assertEquals(1000, matrixS.get(1999).getHeight());
        assertEquals(2000, matrixS.get(2000).getHeight());
        assertEquals(3999, matrixS.get(3000).getHeight());
    }

    @Test
    public void testLongestPathForSnakeFill_1000(){
        Matrix matrix = Matrix.snakeFill(1000,1000);
        Descent descent = matrix.getLongestPathIndexed();
        assertEquals(999_999, descent.getHeight());
        assertEquals(999_999, descent.getLength());
        assertEquals(999_999, descent.getDepth());
    }

    @Test
    @Ignore
    /*
      It works even for 16M elements matrix but heap size becomes an issue,
      needs 8Gb heap and takes 12s to complete on my computer
     */
    public void testLongestPathForSnakeFill_4000(){
        Matrix matrix = Matrix.snakeFill(4000,4000);
        Descent descent = matrix.getLongestPathIndexed();
        assertEquals(15_999_999, descent.getHeight());
        assertEquals(15_999_999, descent.getLength());
        assertEquals(15_999_999, descent.getDepth());
    }

    @Test
    /*
        This presents the answer to the task given
        By the rules of the task the actual descent length is 15
        as it is the amount of points in descent while
        this algorithm counts the number of jumps
     */
    public void testLongestPathForFile() throws Exception{
        Matrix matrix = Matrix.fromFile(ClassLoader.getSystemResourceAsStream("map.txt"));
        Descent descent = matrix.getLongestPathIndexed();
        System.out.println(descent);
        assertEquals(14, descent.getLength());
        assertEquals(1422, descent.getDepth());
        assertEquals(1422, descent.getHeight());
    }

    @Test
    public void testGetHeightsIndex() throws Exception {
        Matrix matrix = Matrix.fromFile(ClassLoader.getSystemResourceAsStream("map.txt"));
        Map<Integer, List<Integer>> heightIndex = matrix.getHeightsIndex();
        assertEquals(1500, heightIndex.size());
        int positionsCounter = 0;
        for(List<Integer> positions : heightIndex.values()){
            positionsCounter += positions.size();
        }
        assertEquals(1_000_000, positionsCounter);
    }
}