package ru.savvy.longestdescent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class Matrix {

    private final int width;
    private final int height;
    private final List<Descent> matrix;

    public static Matrix fromFile(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        List<Descent> matrix = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            int h = scanner.nextInt();
            matrix.add(new Descent(h));
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
        List<Descent> matrix = new ArrayList<>(width*height);
        for (int i = 0; i < width * height; i++){
            int row = i / width;
            int h;
            if (row % 2 == 1) {
                h = (row+1)*width - (i % width) - 1;
            }
            else {
                h = i;
            }
            matrix.add(new Descent(h));

        }
        return new Matrix(width,height,matrix);
    }

    private Matrix(int width, int height, List<Descent> matrix) {
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

    public Descent get(int position) {
        return matrix.get(position);
    }

    /**
     * @param position starting point
     * @return position in matrix to the North if there is one, and element to the North complies with condition which is from > to now,
     * otherwise {@code null}
     */
    public Integer goNorth(int position) {
        return ((position / width) > 0) ? position - width : null;
    }

    /**
     * @param position starting point
     * @return goes East, see {@link Matrix#goNorth(int)}
     */
    public Integer goEast(int position) {
        return ((position % width) != (width - 1)) ? position + 1 : null;
    }

    /**
     * @param position starting point
     * @return goes South, see {@link Matrix#goNorth(int)}
     */
    public Integer goSouth(int position) {
        return ((position / width) < (height-1))  ? position + width : null;
    }

    /**
     * @param position starting point
     * @return goes West, see {@link Matrix#goNorth(int)}
     */
    public Integer goWest(int position) {
        return ((position % width) != 0)  ? position - 1 : null;
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
     * Builds matrix's height indexes
     * @return Map where key is height and value is list of positions of this height
     */
    public SortedMap<Integer, List<Integer>> getHeightsIndex(){
        SortedMap<Integer, List<Integer>> result = new TreeMap<>();
        for(int i=0; i<getHeight()*getWidth(); i++){
            int height = matrix.get(i).getHeight();
            if (result.get(height) == null){
                result.put(height, new ArrayList<>());
            }
            result.get(height).add(i);
        }
        return result;
    }

    private List<Descent> getAllNeighbours(int position){
        List<Descent> descents = new ArrayList<>();
        for (Direction direction : Direction.values()){
            Integer neighbour = go(position,direction);
            if (neighbour != null){
                descents.add(matrix.get(neighbour));
            }
        }
        return descents;
    }

    private Descent findOutGreatestDescent(){
        Descent longestDescent = matrix.get(0);
        for(Descent descent : matrix){
            if (!descent.isCalculated()) throw new IllegalStateException("Not all descents were calculated!");
            if (descent.greaterThan(longestDescent)){
                longestDescent = descent;
            }
        }
        return longestDescent;
    }

    /**
     * @return longest and deepest path in matrix
     */
    public Descent getLongestPathIndexed(){
        // Algorithm:
        // - calculate matrix's height indexes as SortedMap where key is height and value is the list of positions, see this#getHeightsIndex()
        // - going through points in ascending by height order we calculate the longest/deepest descent relatively to
        // the closest neighbours, see Descent#join
        // - looping through the matrix again we find the longest/deepest descent, see this#findOutGreatestDescent()
        //
        Map<Integer, List<Integer>> index = getHeightsIndex();
        for(List<Integer> positions : index.values()){
            for(int position : positions){
                matrix.get(position).join(getAllNeighbours(position));
            }
        }
        return findOutGreatestDescent();
    }

}
