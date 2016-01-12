package ru.savvy.longestdescent;

import java.util.List;

/**
 * We use this also as element of matrix instead of pure int, as we need data structure for algorithm
 *
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class Descent {
    private  int length; // length
    private int lowestPoint; // lowestPoint of the longest descent
    private int height; // height of current matrix point
    private boolean calculated; // just to be sure all calculations are done properly


    private boolean greaterThanByLowest(Descent descent) { // yeah I know about Comparable, this one is more elegant
        if (descent.length == this.length) {
            return descent.lowestPoint > lowestPoint;
        } else {
            return descent.length < length;
        }
    }


    /**
     * @param descent given
     * @return true if given descent less than {@code this}
     */
    public boolean greaterThan(Descent descent){
        if (descent.length == this.length){
            return descent.getDepth() < getDepth();
        } else {
            return descent.length < length;
        }
    }

    // we use constructor just to create points in matrix
    public Descent(int height){
        this.height = height;
        this.length = 0;
        this.lowestPoint = height;
        this.calculated = false;
    }

    /**
     * This function we use to join already calculated descent into this one
     * @param directions array of destinations
     */
    void join(List<Descent> directions) throws IllegalStateException { // mutation point hence package private

        // Here is almost all the logic of algorithm:
        // - for given element of matrix we take all one step directions
        // - we choose the direction with the longest descent which complies with condition

        Descent descentToJoinWith = null;
        for(Descent d : directions){
            if (d.height < height) {
                if (!d.calculated) {
                    throw new IllegalStateException("Matrix should be traversed in ascending order. From lowest heights to highest");
                }
                else{
                    if (descentToJoinWith == null || d.greaterThanByLowest(descentToJoinWith)) {
                        descentToJoinWith = d;
                    }
                }
            }
        }

        this.calculated = true;

        if (descentToJoinWith != null) {
            this.length = descentToJoinWith.length + 1;
            this.lowestPoint = descentToJoinWith.lowestPoint;
        }
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getDepth(){
        return height - lowestPoint;
    }

    public boolean isCalculated() {
        return calculated;
    }

    @Override
    public String toString() {
        return "Descent length: " + length + " depth: " + getDepth();
    }
}
