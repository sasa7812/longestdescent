package ru.savvy.longestdescent;

/**
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class Descent {
    private  int length;
    private int depth;

    public boolean lessThan(Descent descent) { // yeah I know about Comparable, this one is more elegant
        if (descent.length == this.length) {
            return descent.depth > depth;
        } else {
            return descent.length > length;
        }
    }

    public Descent(int length, int depth) {
        this.length = length;
        this.depth = depth;
    }

    public int getLength() {
        return length;
    }

    public int getDepth() {
        return depth;
    }

    public Descent stepAhead(int stepDepth){
        return new Descent(length+1, depth + stepDepth);
    }

    @Override
    public String toString() {
        return "Descent length: " + length + " depth : " + depth;
    }
}
