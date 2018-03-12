package lab2;

import beans.Edge;
import beans.Point;
import beans.PointFather;

import java.io.*;
import java.util.*;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class MinimalTree {

    private String fileName;
    private List<Point> points;
    private List<Edge> edges;
    private Set<Point> maxComponent = null;
    private Integer[] length;
    private Set<Point> leafs = new HashSet<>();
    private List<Integer> oldNumbers;
    private List<Edge> newEdges = new ArrayList<>();
    private List<Point> newPoints = new ArrayList<>();


    public MinimalTree(String fileName) {
        this.fileName = fileName;
        points = new ArrayList<>();
        edges = new LinkedList<>();
    }


    public void buildMinimalTree() throws IOException {
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
            if (p1.getSet() != p2.getSet()) {
                union(p1, p2);
                A.add(next);
            }
            previous = next;
        }
        saveMaxComponentToHolder(A);
    }
    /*
     * 72067
     * 69326
     */

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
                edges.add(createEdge(numbers[0], numbers[1]));
                edges.add(createEdge(numbers[1], numbers[2]));
                edges.add(createEdge(numbers[0], numbers[2]));
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

    private int nTriangles = 0;

    private StringBuffer readFromFileForLeafs() throws IOException {
        boolean canStartEdges = false;
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        StringBuffer buffer = new StringBuffer();
        while ((strLine = br.readLine()) != null) {
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
                for(int i = 0;i<3;i++) {
                    if (leafs.contains(points.get(numbers[i]))) {
                        buffer.append("3 ").append(strLine).append("\n");
                        nTriangles++;
                    }
                }
            }
            if (canStartEdges && strLine.equals("")) {
                canStartEdges = false;
            }
        }
        br.close();
        return buffer;
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
        if (c < 0) {
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
        Set<Point> mainSet = p1.getSet();
        if (mainSet.size() < additionalSet.size()) {
            mainSet = p2.getSet();
            additionalSet = p1.getSet();
        }
        mainSet.addAll(additionalSet);
        for (Point p : additionalSet) {
            p.setSet(mainSet);
        }
        if (maxComponent != null) {
            if (mainSet.size() > maxComponent.size() && maxComponent != mainSet) {
                maxComponent = mainSet;
            }
        } else {
            maxComponent = mainSet;
        }
    }

    private void saveMaxComponentToHolder(List<Edge> forest) {
        List<Integer> newNumbers = Arrays.asList(new Integer[points.size()]);
        List<Integer> oldNumbers = Arrays.asList(new Integer[maxComponent.size()]);
        for (Edge e : forest) {
            Point firstPoint = points.get(e.getFirstPoint());
            if (maxComponent.contains(firstPoint)) {
                int first = e.getFirstPoint();
                int second = e.getSecondPoint();
                Point secondPoint = points.get(second);

                if (newNumbers.get(first) == null) {
                    newPoints.add(firstPoint);
                    int newFirst = newPoints.size() - 1;
                    newNumbers.set(first, newFirst);
                    oldNumbers.set(newFirst, first);
                    e.setFirstPoint(newFirst);
                } else {
                    e.setFirstPoint(newNumbers.get(first));
                }

                if (newNumbers.get(second) == null) {
                    newPoints.add(secondPoint);
                    int newSecond = newPoints.size() - 1;
                    newNumbers.set(second, newSecond);
                    oldNumbers.set(newSecond, second);
                    e.setSecondPoint(newSecond);
                } else {
                    e.setSecondPoint(newNumbers.get(second));
                }
                newEdges.add(e);
            }
        }
    }

    public void writeToFile(String mainFile) throws IOException {

        length = new Integer[newPoints.size()];
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mainFile)));
        wr.write("# vtk DataFile Version 1.0\n" +
                "...\n" +
                "ASCII\n\nDATASET POLYDATA\n");

        wr.write("POINTS " + newPoints.size() + " float\n");

        for (Point p : newPoints) {
            wr.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wr.newLine();
        wr.write("LINES " + newEdges.size() + " " + newEdges.size() * 3 + "\n");
        for (Edge e : newEdges) {
            wr.write("2 " + e.getFirstPoint() + " " + e.getSecondPoint() + "\n");
        }

        BFS(newPoints.size() / 5);

        wr.write("POINT_DATA " + length.length +
                "\nSCALARS temp int\n" +
                "LOOKUP_TABLE default\n");
        for (Integer aLength : length) {
            wr.write(aLength.toString() + "\n");
        }
        wr.flush();
        wr.close();
        BufferedWriter wrLeafs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("points.vtk")));
        wrLeafs.write("# vtk DataFile Version 1.0\n" +
                "...\n" +
                "ASCII\n\nDATASET POLYDATA\n");

        wrLeafs.write("POINTS " + points.size() + " float\n");

        for (Point p : points) {
            wrLeafs.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wrLeafs.newLine();
        String  s = readFromFileForLeafs().toString();
        wrLeafs.write("LINES " + nTriangles + " " + nTriangles * 3 + "\n");
        wrLeafs.write(s);
        wrLeafs.flush();
        wrLeafs.close();


    }


    private List<List<Integer>> buildNeighbors() {
        List<List<Integer>> neighbors = Arrays.asList(new ArrayList[newPoints.size()]);

        for (Edge e : newEdges) {
            int first = e.getFirstPoint();
            int second = e.getSecondPoint();
            if (neighbors.get(first) == null) {
                neighbors.set(first, new ArrayList<>());
            }
            if (neighbors.get(second) == null) {
                neighbors.set(second, new ArrayList<>());
            }
            neighbors.get(first).add(second);
            neighbors.get(second).add(first);
        }

        return neighbors;
    }


    private void BFS(int start_node) {
        List<List<Integer>> neighbors = buildNeighbors();
        boolean[] visited = new boolean[neighbors.size()];
        Queue<PointFather> queue = new LinkedList<>();
        queue.add(new PointFather(start_node, start_node));
        visited[start_node] = true;
        length[start_node] = -1;
        while (!queue.isEmpty()) {
            PointFather node = queue.poll();
            visited[node.getNode()] = true;
            length[node.getNode()] = length[node.getFather()] + 1;
            List<Integer> localNeighbors = neighbors.get(node.getNode());
            if (localNeighbors.size() == 1 && visited[localNeighbors.get(0)]) {
                leafs.add(newPoints.get(node.getNode()));
                continue;
            }
            for (int l : localNeighbors) {
                if (!visited[l]) {
                    queue.add(new PointFather(l, node.getNode()));
                    visited[l] = true;
                }
            }
        }
    }


}
