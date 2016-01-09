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
        Descent longestDescent = new Descent(0, Integer.MAX_VALUE);
        for (int i = 0; i < matrixF.getWidth() * matrixF.getHeight(); i++) {
            Descent initialDescent = new Descent(0, Integer.MAX_VALUE);
            Descent newDescent = matrixF.getLongestPath(i, initialDescent);
            if (longestDescent.lessThan(newDescent)) {
                longestDescent = newDescent;
                System.out.println("Matrix point: " + i + " value = " + matrixF.get(i) + " == " + newDescent);
            }
        }
        System.out.println(" Longest path: " + longestDescent);
    }

    @Test
    public void testGetLongestPathSnake() throws Exception {
        // even 10x10 snake-filled matrix takes significant time to complete
        Matrix matrixS = Matrix.snakeFill(10, 10);
        Descent initialDescent = new Descent(0, Integer.MAX_VALUE);
        // actual length is 100 by task definition as starting point also counted
        // we starting here from the highest point as it apparently has longest descent
        Descent descent = matrixS.getLongestPath(90, initialDescent);
        assertEquals(99, descent.getLength());
        System.out.println(descent);
    }
}