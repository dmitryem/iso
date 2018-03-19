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
    private int start = -1;
    private int nTriangles = 0;

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
        edges = A;
        saveMaxComponent();
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
                addEdges(strLine);
            }
            if (canStartPoints && !strLine.equals("")) {
                addPoint(strLine);
            } else if (canStartPoints && strLine.equals("")) {
                canStartPoints = false;

            } else if (canStartEdges && strLine.equals("")) {
                canStartEdges = false;

            }
        }
        br.close();

    }

    private void addEdges(String strLine){
        String[] sNumbers = strLine.split(" ");
        int[] numbers = {0, 0, 0};
        numbers[0] = Integer.parseInt(sNumbers[0]);
        numbers[1] = Integer.parseInt(sNumbers[1]);
        numbers[2] = Integer.parseInt(sNumbers[2]);
        edges.add(createEdge(numbers[0], numbers[1]));
        edges.add(createEdge(numbers[1], numbers[2]));
        edges.add(createEdge(numbers[0], numbers[2]));
    }

    private void addPoint(String strLine){
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

    private void saveMaxComponent() {
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge e = iterator.next();
            Point firstPoint = points.get(e.getFirstPoint());
            if (!maxComponent.contains(firstPoint)) {
                iterator.remove();
            }
        }
    }

    public void writeToFile(String mainFile, String leafsFile) throws IOException {
        writeMainFile(mainFile);
        writeLeafsFile(leafsFile);
    }

    private void writeMainFile(String mainFile) throws IOException {
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mainFile)));
        wr.write("# vtk DataFile Version 1.0\n...\nASCII\n\nDATASET POLYDATA\n");
        wr.write("POINTS " + points.size() + " float\n");
        int index = 0;
        for (Point p : points) {
            if (start == -1 && maxComponent.contains(p)) {
                start = index;
            }
            index++;
            wr.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wr.write("\nLINES " + edges.size() + " " + edges.size() * 3 + "\n");
        for (Edge e : edges) {
            wr.write("2 " + e.getFirstPoint() + " " + e.getSecondPoint() + "\n");
        }
        BFS(start);
        wr.write("POINT_DATA " + length.length +
                "\nSCALARS temp int\n" +
                "LOOKUP_TABLE default\n");
        for (Integer aLength : length) {
            if (aLength == null) {
                wr.write("-1\n");
            } else {
                wr.write(aLength.toString() + "\n");
            }
        }
        wr.flush();
        wr.close();
    }

    private void writeLeafsFile(String leafsFile) throws IOException{
        BufferedWriter wrLeafs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leafsFile)));
        wrLeafs.write("# vtk DataFile Version 1.0\n...\nASCII\n\nDATASET POLYDATA\n");
        wrLeafs.write("POINTS " + points.size() + " float\n");
        for (Point p : points) {
            wrLeafs.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        String s = readFromFileForLeafs().toString();
        wrLeafs.write("\nTRIANGLE_STRIPS " + nTriangles + " " + nTriangles * 4);
        wrLeafs.write(s);
        wrLeafs.flush();
        wrLeafs.close();
    }

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
                for (int i = 0; i < 3; i++) {
                    if (leafs.contains(points.get(numbers[i]))) {
                        buffer.append("\n3 ").append(strLine);
                        nTriangles++;
                        break;
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

    private void BFS(int start_node) {
        List<List<Integer>> neighbors = buildNeighbors();
        length = new Integer[points.size()];
        boolean[] visited = new boolean[points.size()];
        Queue<PointFather> queue = new LinkedList<>();
        queue.add(new PointFather(start_node, start_node));
        visited[start_node] = true;
        length[start_node] = -1;
        while (!queue.isEmpty()) {
            PointFather node = queue.poll();
            visited[node.getNode()] = true;
            length[node.getNode()] = length[node.getFather()] + 1;
            List<Integer> localNeighbors = neighbors.get(node.getNode());
            boolean leaf = true;
            for (int l : localNeighbors) {
                if (!visited[l]) {
                    queue.add(new PointFather(l, node.getNode()));
                    visited[l] = true;
                    leaf = false;
                }
            }
            if (leaf) {
                leafs.add(points.get(node.getNode()));
            }
        }
    }

    private List<List<Integer>> buildNeighbors() {
        List<List<Integer>> neighbors = Arrays.asList(new ArrayList[points.size()]);
        for (Edge e : edges) {
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


}
