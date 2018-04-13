package lab5;

import beans.Edge;
import beans.Point;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Created by demelyanov on 12.04.2018.
 */
public class Shteiner {

    private final int maxX;
    private final int maxY;
    private List<Point> points;
    private List<Edge> edges;
    private List<Point> initialPoints = new ArrayList<>();
    private List<Edge> initialEdges = new ArrayList<>();

    private int addedPoints = 0;

    public Shteiner(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }


    public void writeShteinerToFile(String shteinerFile) throws IOException {
        initialize();
        walkThrough();
        writeMainFile(shteinerFile);
    }

    private void initialize() {

        initializeEdges();
    }


    public void initializePoints(Initializer initializer) {
        initialPoints.clear();
        initializer.initializePoints(initialPoints);
    }

    private void initializeEdges() {
        int size = initialPoints.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                initialEdges.add(new Edge(i, j, Edge.distance(initialPoints.get(i), initialPoints.get(j))));
            }
        }
    }

    private void resetPoints() {
        for (int i = 0; i < addedPoints; i++) {
            initialPoints.remove(initialPoints.size() - 1);
        }
    }

    private void resetEdges() {
        int edges = (initialPoints.size() - 1) * addedPoints;
        for (int i = 0; i < edges; i++) {
            initialEdges.remove(initialEdges.size() - 1);
        }
    }

    private void walkThrough() {
        Point[] p = new Point[addedPoints];
        walkThrough(0, p);
    }

    private void walkThrough(int level, Point... points) {
        if (level != addedPoints) {
            PointGenerator generator = new PointGenerator(maxX, maxY);
            while (generator.hasNextPoint()) {
                Point point = generator.getNextPoint();
                points[level] = point;
                walkThrough(level + 1, points);
            }
        } else {
            workWithPoints(points);
        }
    }

    private void workWithPoints(Point... points) {
        int initialSize = initialPoints.size();
        initialPoints.addAll(Arrays.asList(points));
        for (int i = 0; i < initialPoints.size(); i++) {
            for(int j = 0;j < addedPoints;j++){
                initialPoints.get(i).clear();
                if (initialSize + j != i) {
                    initialEdges.add(new Edge(i, initialSize + j, Edge.distance(initialPoints.get(i), points[j])));
                }
            }
        }

        List<Edge> localEdges = new ArrayList<>(initialEdges);
        List<Edge> tree = buildMinimalTree(localEdges);
        if (length < minLength) {
            this.points = new ArrayList<>(initialPoints);
            edges = new ArrayList<>(tree);
            minLength = length;
        }
        resetEdges();
        resetPoints();
    }

    private double minLength = Double.MAX_VALUE;
    private double length = 0;

    private List<Edge> buildMinimalTree(List<Edge> localEdges) {
        length = 0;
        List<Edge> A = new ArrayList<>();
        localEdges.sort(Comparator.comparing(Edge::getWeight));
        Iterator<Edge> iterator = localEdges.iterator();
        Edge previous = null;
        while (iterator.hasNext()) {
            Edge next = iterator.next();
            if (next.equals(previous)) {
                iterator.remove();
                continue;
            }
            Point p1 = initialPoints.get(next.getFirstPoint());
            Point p2 = initialPoints.get(next.getSecondPoint());
            if (p1.getSet() != p2.getSet()) {
                union(p1, p2);
                A.add(next);
                length += next.getWeight();
            }
            previous = next;
        }
        return A;
    }

    private void union(Point p1, Point p2) {
        Set<Point> additionalSet = p2.getSet();
        Set<Point> mainSet = p1.getSet();
        mainSet.addAll(additionalSet);
        for (Point p : additionalSet) {
            p.setSet(mainSet);
        }
    }

    private void writeMainFile(String mainFile) throws IOException {
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mainFile)));
        wr.write("# vtk DataFile Version 1.0\n...\nASCII\n\nDATASET POLYDATA\n");
        wr.write("POINTS " + points.size() + " float\n");
        for (Point p : points) {
            wr.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wr.write("\nLINES " + edges.size() + " " + edges.size() * 3 + "\n");
        for (Edge e : edges) {
            wr.write("2 " + e.getFirstPoint() + " " + e.getSecondPoint() + "\n");
        }
        wr.flush();
        wr.close();
    }

    public int getAddedPoints() {
        return addedPoints;
    }

    public void setAddedPoints(int addedPoints) {
        this.addedPoints = addedPoints;
    }
}
