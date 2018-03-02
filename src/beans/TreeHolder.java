package beans;

import java.util.List;
import java.util.Set;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class TreeHolder {
    private List<Edge> edges;
    private Set<Point> points;


    public List<Edge> getEdges() {
        return edges;
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setPoints(Set<Point> points) {
        this.points = points;
    }
}
