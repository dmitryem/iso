package beans;

import java.util.List;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class TreeHolder {
    private List<Edge> edges;
    private List<Point> points;
    private List<List<Integer>> neighbors;


    public List<Edge> getEdges() {
        return edges;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<List<Integer>> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<List<Integer>> neighbors) {
        this.neighbors = neighbors;
    }
}
