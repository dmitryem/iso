package beans;

import java.util.List;

/**
 * @author demelyanov
 * 17.03.18
 */
public class WeightedStringEdge {

    private String edge;
    private double weight;

    public WeightedStringEdge(String edge, double weight){
        this.edge = edge;
        this.weight = weight;
    }

    public WeightedStringEdge(){

    }

    public double getWeight() {
        return weight;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return edge.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if( obj instanceof WeightedStringEdge) {
            equals = edge.equals(((WeightedStringEdge) obj).getEdge());
        }
        return equals;
    }

    public static WeightedStringEdge getEdgeAsString(int a, int b, List<Point> points) {
        WeightedStringEdge edge = new WeightedStringEdge();
        if (a < b) {
            edge.setEdge(a + "/" + b);
        } else {
            edge.setEdge(b + "/" + a);
        }
        Point p1 = points.get(a);
        Point p2 = points.get(b);
        if(distance(p1, p2) == 0){
            System.out.println();
        }
        edge.setWeight(distance(p1, p2));
        return edge;
    }

    private static double distance(Point p1, Point p2) {
        double dx = Math.pow(p1.getX() - p2.getX(), 2);
        double dy = Math.pow(p1.getY() - p2.getY(), 2);
        double dz = Math.pow(p1.getZ() - p2.getZ(), 2);
        return Math.sqrt(dx + dy + dz);
    }

}
