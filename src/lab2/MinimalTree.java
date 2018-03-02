package lab2;

import beans.Edge;
import beans.Point;
import beans.TreeHolder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class MinimalTree {

    private String fileName;
    private List<Point> points;
    private List<Edge> edges;

    public MinimalTree(String fileName) {
        this.fileName = fileName;
        points = new LinkedList<>();
        edges = new LinkedList<>();
    }


    public TreeHolder buildMinimalTree() throws IOException {
        List<Edge> A = new ArrayList<>();
        readFromFile();
        edges.sort(Comparator.comparing(Edge::getWeight));
        Iterator<Edge> iterator = edges.iterator();
        Edge previous = null;
        while (iterator.hasNext()) {
            Edge next = iterator.next();
            if (next.equals(previous)) {
                iterator.remove();
                continue;
            }
            Point p1 = points.get(next.getFirstPoint());
            Point p2 = points.get(next.getSecondPoint());
            if(p1.getSet() != p2.getSet()){
                union(p1,p2);
                A.add(next);
            }
            previous = next;
        }
        TreeHolder treeHolder = new TreeHolder();
        treeHolder.setEdges(A);
        if (previous != null) {
            treeHolder.setPoints(points.get(previous.getFirstPoint()).getSet());
        }
        return treeHolder;
    }


    private void readFromFile() throws IOException {
        boolean canStartPoints = false;
        boolean canStartEdges = false;
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            if (strLine.contains("vertices")) {
                canStartPoints = true;
                continue;
            }
            if (strLine.contains("triangles")) {
                canStartEdges = true;
                continue;
            }
            if (canStartEdges && !strLine.equals("")) {
                String[] sNumbers = strLine.split(" ");
                int[] numbers = {0, 0, 0};
                numbers[0] = Integer.parseInt(sNumbers[0]);
                numbers[1] = Integer.parseInt(sNumbers[1]);
                numbers[2] = Integer.parseInt(sNumbers[2]);
                Edge e = createEdge(numbers[0], numbers[1]);
                edges.add(e);
                e = createEdge(numbers[1], numbers[2]);
                edges.add(e);
                e = createEdge(numbers[0], numbers[2]);
                edges.add(e);
            }
            if (canStartPoints && !strLine.equals("")) {
                String[] sNumbers = strLine.split(" ");
                double[] numbers = {0, 0, 0};
                numbers[0] = Double.parseDouble(sNumbers[0]);
                numbers[1] = Double.parseDouble(sNumbers[1]);
                numbers[2] = Double.parseDouble(sNumbers[2]);
                Point p = new Point();
                p.setX(numbers[0]);
                p.setY(numbers[1]);
                p.setZ(numbers[2]);
                points.add(p);
            } else if (canStartPoints && strLine.equals("")) {
                canStartPoints = false;
            } else if (canStartEdges && strLine.equals("")) {
                canStartEdges = false;
            }
        }
        br.close();

    }

    private double distance(Point p1, Point p2) {
        double dx = Math.pow(p1.getX() - p2.getX(), 2);
        double dy = Math.pow(p1.getY() - p2.getY(), 2);
        double dz = Math.pow(p1.getZ() - p2.getZ(), 2);
        return Math.sqrt(dx + dy + dz);
    }

    private Edge createEdge(int i1, int i2) {
        Edge e = new Edge();
        Point p1 = points.get(i1);
        Point p2 = points.get(i2);
        int c = p1.compareTo(p2);

        e.setWeight(distance(p1, p2));
        if (c == -1) {
            e.setFirstPoint(i1);
            e.setSecondPoint(i2);
        } else {
            e.setFirstPoint(i2);
            e.setSecondPoint(i1);
        }
        return e;
    }


    private void union(Point p1, Point p2) {
        Set<Point> additionalSet = p2.getSet();
        for (Point p : additionalSet) {
            p1.addToSet(p);
        }
        p2.setSet(p1.getSet());
    }

}
