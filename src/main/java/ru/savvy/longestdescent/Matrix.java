package ru.savvy.longestdescent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class Matrix {

    private int depth = 0;

    private final int width;
    private final int height;
    List<Integer> matrix = new ArrayList<>();

    public static Matrix fromFile(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        List<Integer> matrix = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            matrix.add(scanner.nextInt());
        }
        return new Matrix(width, height, matrix);
    }

    /**
     * Fills matrix with numbers in ascending order snake-like
     *  1  2  3  4
     *  8  7  6  5
     *  9 10 11 12
     * 16 15 14 13
     *
     * this should give a lot of long descents
     *
     * @param width
     * @param height
     * @return
     */
    public static Matrix snakeFill(int width, int height){
        List<Integer> matrix = new ArrayList<>(width*height);
        for (int i = 0; i < width * height; i++){
            int row = i / width;
            if (row % 2 == 1) {
                matrix.add((row+1)*width - (i % width) - 1);
            }
            else {
                matrix.add(i);
            }
        }
        return new Matrix(width,height,matrix);
    }

    private Matrix(int width, int height, List<Integer> matrix) {
        this.width = width;
        this.height = height;
        this.matrix = matrix; // no defensive copy, we have private constructor
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int get(int position) {
        return matrix.get(position);
    }

    public List<Integer> getMatrix() {
        return Collections.unmodifiableList(matrix);
    }

    /**
     * Condition of route it is from < to
     *
     * @param from position to go from
     * @param to   position to go to
     * @return
     */
    private boolean canGoThere(int from, int to) {
        return get(from) > get(to);
    }

    /**
     * @param position starting point
     * @return position in matrix to the North if there is one, and element to the North complies with condition which is from > to now,
     * otherwise {@code null}
     */
    public Integer goNorth(int position) {
        return (((position / width) > 0) && canGoThere(position, position - width)) ? position - width : null;
    }

    /**
     * @param position starting point
     * @return goes East, see {@link Matrix#goNorth(int)}
     */
    public Integer goEast(int position) {
        return (((position % width) != (width - 1)) && canGoThere(position, position + 1)) ? position + 1 : null;
    }

    /**
     * @param position starting point
     * @return goes South, see {@link Matrix#goNorth(int)}
     */
    public Integer goSouth(int position) {
        return (((position / width) < (height-1)) && canGoThere(position, position + width)) ? position + width : null;
    }

    /**
     * @param position starting point
     * @return goes West, see {@link Matrix#goNorth(int)}
     */
    public Integer goWest(int position) {
        return (((position % width) != 0) && canGoThere(position, position - 1)) ? position - 1 : null;
    }

    public Integer go(int position, Direction direction){
        switch (direction){
            case NORTH: return goNorth(position);
            case EAST: return goEast(position);
            case SOUTH: return goSouth(position);
            case WEST: return goWest(position);
            default: throw new IllegalArgumentException("Unknown direction");
        }
    }

    /**
     *
     * @param position
     * @param initialDescent
     * @return
     */
    public Descent getLongestPath(int position, Descent initialDescent) {
        Descent longestDescent = initialDescent;
        Descent descentToFollow = new Descent(initialDescent.getLength()+1,get(position));
        if (descentToFollow.getLength() > depth){
            //System.out.println("Depth : " + descentToFollow.getLength() + " reached");
            depth = descentToFollow.getLength();
        }
        for (Direction direction : Direction.values()){
            Descent newDescent = go(position,direction) != null ? getLongestPath(go(position,direction), descentToFollow) : longestDescent;
            if (longestDescent.lessThan(newDescent)) longestDescent = newDescent;
        }
        return longestDescent;
    }

}
