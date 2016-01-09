package ru.savvy.longestdescent;

import org.junit.Test;

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
        assertEquals(1460, matrixF.get(999_999));
        assertEquals(50, matrixF.get(0));
    }

    @Test
    public void testGoDirections() throws Exception {
        Matrix matrixF = Matrix.fromFile(ClassLoader.getSystemResourceAsStream("map.txt"));
        assertEquals(null, matrixF.goNorth(0));
        assertEquals(null, matrixF.goWest(0));
        assertEquals(null, matrixF.goSouth(0));
        assertEquals(null, matrixF.goEast(0));
        assertEquals(null, matrixF.goSouth(999_999));
        assertEquals(null, matrixF.goEast(999_999));
        assertEquals(Integer.valueOf(999_998), matrixF.goWest(999_999));
        assertEquals(Integer.valueOf(998_999), matrixF.goNorth(999_999));
    }

    @Test
    public void testSnakeFill() throws Exception {
        Matrix matrixS = Matrix.snakeFill(1000, 1000);
        assertEquals(999, matrixS.get(999));
        assertEquals(1999, matrixS.get(1000));
        assertEquals(1000, matrixS.get(1999));
        assertEquals(2000, matrixS.get(2000));
        assertEquals(3999, matrixS.get(3000));
    }

    @Test
    public void testGetLongestPathForFileSample() throws Exception {
        Matrix matrixF = Matrix.fromFile(ClassLoader.getSystemResourceAsStream("map.txt"));
        Descent longestDescent = matrixF.getLongestDescent();
        System.out.println(longestDescent);
        assertEquals(1422, longestDescent.getDepth());
        // actual length is 15 by definition of the task
        assertEquals(14,longestDescent.getLength());
    }

    @Test
    public void testGetLongestPathSnake() throws Exception {
        // even 10x10 snake-filled matrix takes significant time to complete
        Matrix matrixS = Matrix.snakeFill(8, 8);
        Descent descent = matrixS.getLongestDescent();
        System.out.println(descent);
        assertEquals(63, descent.getLength());
        assertEquals(63, descent.getDepth());
    }
}