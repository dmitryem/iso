package beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class Point implements Comparable<Point> {
    private double x;
    private double y;
    private double z;
    private Set<Point> set;

    public Point(){
        set = new HashSet<>();
        set.add(this);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Point){
            Point pointObj = (Point) obj;
            return pointObj.getX() == x && pointObj.getY() == y && pointObj.getZ() == z;
        }else {
            return false;
        }

    }

    @Override
    public int compareTo(Point o) {
        return vectorLength().compareTo(o.vectorLength());
    }

    private Double vectorLength(){
        return Math.sqrt(x*x + y*y + z*z);
    }

    public void setSet(Set<Point> set) {
        this.set = set;
    }

    public Set<Point> getSet() {
        return set;
    }

    public void addToSet(Point p){
        set.add(p);
    }

}
