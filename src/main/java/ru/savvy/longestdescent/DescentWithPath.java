package ru.savvy.longestdescent;

import java.util.List;

/**
 * @author sasa <a href="mailto:sasa7812@gmail.com">Alexander Nikitin</a>
 */
public class DescentWithPath extends Descent {
    private int startingPoint;
    private final List<Integer> path;

    public DescentWithPath(int length, int lowestPoint, List<Integer> path){
        super(length,lowestPoint);
        this.path = path;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(int startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void connect(DescentWithPath descent){
        this.setLength(getLength() + descent.getLength());
        this.setLowestPoint(descent.getLowestPoint());
        this.path.addAll(descent.getPath());
    }
}
