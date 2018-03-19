package beans;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author demelyanov
 * 17.03.18
 */
public class Vertex implements Comparable<Vertex> {
    private String triangle;
    private boolean visited;
    private double way;
    private Map<Integer,Double> neighbors;
    private int number = -1;

    public Vertex(String triangle){
        neighbors = new HashMap<>();
        visited = false;
        way = Double.POSITIVE_INFINITY;
        this.triangle = triangle;
    }

    public double getWay() {
        return way;
    }

    public String getTriangle() {
        return triangle;
    }

    public boolean isVisited() {
        return visited;
    }

    public Map<Integer, Double> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Map<Integer, Double> neighbors) {
        this.neighbors = neighbors;
    }

    public void setTriangle(String triangle) {
        this.triangle = triangle;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setWay(double way) {
        this.way = way;
    }

    public void addToNeighbors(int vertex, double weight){
        neighbors.put(vertex,weight);
    }

    @Override
    public int compareTo(Vertex o) {
        return Double.compare(way, o.way);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
