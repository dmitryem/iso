package lab5;

import beans.Edge;
import beans.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by demelyanov on 12.04.2018.
 */
public class Shteiner {

    private final int maxX = 13;
    private final int maxY = 6;
    private List<Point> points;
    private List<Edge> edges;
    private List<Point> initialPoints = new ArrayList<>();
    private List<Edge> initialEdges = new ArrayList<>();

    public void writeLeafsFile(String leafsFile) throws IOException {
        initialize();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {

                }
            }
        }

    }

    private void initialize() {
        initializePoints(initialPoints);
        initializeEdges(initialPoints, initialEdges);
    }


    private void initializePoints(List<Point> initialPoints) {
        initialPoints.add(new Point(0, 0));
        initialPoints.add(new Point(0, 3));
        initialPoints.add(new Point(0, 6));
        initialPoints.add(new Point(3, 3));
        initialPoints.add(new Point(4, 0));
        initialPoints.add(new Point(4, 6));
        initialPoints.add(new Point(7, 2));
        initialPoints.add(new Point(8, 0));
        initialPoints.add(new Point(9, 2));
        initialPoints.add(new Point(10, 6));
        initialPoints.add(new Point(11, 2));
        initialPoints.add(new Point(12, 0));
        initialPoints.add(new Point(13, 2));
    }

    private void initializeEdges(List<Point> initialPoints, List<Edge> initialEdges) {
        for (int i = 0; i < 13; i++) {
            for (int j = i + 1; j < 13; j++) {
                new Edge(i, j, Edge.distance(initialPoints.get(i), initialPoints.get(j)));
            }
        }
    }

    private void resetPoints(List<Point> initialPoints) {
        initialPoints.remove(13);
        initialPoints.remove(14);
        initialPoints.remove(15);
    }

}
