package ru.savvy.longestdescent;

/**
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class Descent {
    private  int length;
    private int lowestPoint;

    public boolean lessThan(Descent descent) { // yeah I know about Comparable, this one is more elegant
        if (descent.length == this.length) {
            return descent.lowestPoint < lowestPoint;
        } else {
            return descent.length > length;
        }
    }

    public Descent(int length, int lowestPoint) {
        this.length = length;
        this.lowestPoint = lowestPoint;
    }

    public int getLength() {
        return length;
    }

    public int getLowestPoint() {
        return lowestPoint;
    }

    protected void setLength(int length) {
        this.length = length;
    }

    protected void setLowestPoint(int lowestPoint) {
        this.lowestPoint = lowestPoint;
    }

    @Override
    public String toString() {
        return "Descent length: " + length + " lowestPoint: " + lowestPoint;
    }
}
