package lab4;

import beans.Edge;
import beans.Point;
import beans.TriangleHolder;
import beans.WeightedStringEdge;

import java.io.*;
import java.util.*;

/**
 * Created by demelyanov on 21.03.2018.
 */
public class MaxFlow {

    private int storedStart;
    private int storedEnd;
    private double storedMaxFlow;
    private String filename;

    private List<List<Edge>> graph = new ArrayList<>();
    private List<TriangleHolder> triangles = new ArrayList<>();

    public MaxFlow(String filename) throws IOException {
        this.filename = filename;
        readFromFile();

    }

    @SuppressWarnings("Duplicates")
    private void readFromFile() throws IOException {
        List<Point> points = new ArrayList<>();
        Map<WeightedStringEdge, Integer> neighbors = new HashMap<>();
        boolean canStartPoints = false;
        boolean canStartEdges = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            if (strLine.contains("vertices")) {
                canStartPoints = true;
                continue;
            }
            if (strLine.contains("triangles")) {
                canStartEdges = true;
                createGraph(Integer.valueOf(strLine.split(" ")[1]));
                continue;
            }
            if (canStartEdges && !strLine.equals("")) {
                String[] sNumbers = strLine.split(" ");
                TriangleHolder tr = new TriangleHolder(strLine, triangles.size());
                triangles.add(tr);
                int[] numbers = {0, 0, 0};
                numbers[0] = Integer.parseInt(sNumbers[0]);
                numbers[1] = Integer.parseInt(sNumbers[1]);
                numbers[2] = Integer.parseInt(sNumbers[2]);
                for (int i = 0; i < 3; i++) {
                    WeightedStringEdge edge = WeightedStringEdge.getEdgeAsString(numbers[i], numbers[(i + 1) % 3], points);
                    processEdge(tr, edge, neighbors);
                }

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


    private void processEdge(TriangleHolder tr, WeightedStringEdge edge, Map<WeightedStringEdge, Integer> neighbors) {
        Integer number = neighbors.get(edge);
        if (number == null) {
            neighbors.put(edge, triangles.size() - 1);
        } else {
            TriangleHolder neighbor = triangles.get(number);
            //TODO orient edge
            addEdge(neighbor.getNumber(), tr.getNumber(), edge.getWeight());
        }
    }

    private List<List<Edge>> createGraph(int nodes) {
        for (int i = 0; i < nodes; i++)
            graph.add(new ArrayList<>());
        return graph;
    }

    private void addEdge(int s, int t, double cap) {
        graph.get(s).add(new Edge(t, graph.get(t).size(), cap));
        graph.get(t).add(new Edge(s, graph.get(s).size() - 1, cap));
    }

    private boolean dinicBfs(int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        int[] Q = new int[graph.size()];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (Edge e : graph.get(u)) {
                if (dist[e.getSecondPoint()] < 0 && e.getFlow() < e.getWeight()) {
                    dist[e.getSecondPoint()] = dist[u] + 1;
                    Q[sizeQ++] = e.getSecondPoint();
                }
            }
        }
        return dist[dest] >= 0;
    }

    private double dinicDfs(int[] ptr, int[] dist, int dest, int u, double f) {
        if (u == dest)
            return f;
        for (; ptr[u] < graph.get(u).size(); ++ptr[u]) {
            Edge e = graph.get(u).get(ptr[u]);
            if (dist[e.getSecondPoint()] == dist[u] + 1 && e.getFlow() < e.getWeight()) {
                double df = dinicDfs(ptr, dist, dest, e.getSecondPoint(), Math.min(f, e.getWeight() - e.getFlow()));
                if (df > 0) {
                    e.setFlow(e.getFlow() + df);
                    Edge edge = graph.get(e.getSecondPoint()).get(e.getFirstPoint());
                    edge.setFlow(edge.getFlow() - df);
                    return df;
                }
            }
        }
        return 0;
    }

    public double getMaxFlow(int src, int dest) {
        double flow;
        if (storedEnd == dest && storedStart == src) {
            flow = storedMaxFlow;
        } else {
            storedMaxFlow = flow = maxFlow(src, dest);
            storedEnd = dest;
            storedStart = src;
        }
        return flow;
    }

    private double maxFlow(int src, int dest) {
        double flow = 0;
        int[] dist = new int[graph.size()];
        while (dinicBfs(src, dest, dist)) {
            int[] ptr = new int[graph.size()];
            while (true) {
                double df = dinicDfs(ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }


    public void writeToFile(String out, int start, int end) throws IOException {
        if (storedEnd != end || storedStart != start) {
            storedMaxFlow = maxFlow(start, end);
            storedEnd = end;
            storedStart = start;
        }
        writeToFile(out);

    }

    @SuppressWarnings("Duplicates")
    private void writeToFile(String out) throws IOException {

        BufferedWriter wrLeafs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
        wrLeafs.write("# vtk DataFile Version 1.0\n...\nASCII\n\nDATASET POLYDATA\n");
        boolean canStartPoints = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

        List<String> flow = new ArrayList<>();
        for (List<Edge> list : graph) {
            for (Edge e : list) {
                if (e.getFlow() != 0) {

                    flow.add(triangles.get(e.getSecondPoint()).getTriangle());
                }
            }
        }
        String strLine;
        while ((strLine = br.readLine()) != null) {
            if (strLine.contains("vertices")) {
                canStartPoints = true;
                wrLeafs.write("POINTS " + strLine.split(" ")[1] + " float\n");
                continue;
            }
            if (strLine.contains("triangles")) {

                wrLeafs.write("\nTRIANGLE_STRIPS " + flow.size() + " " + flow.size() * 4);
                for (String s : flow) {
                    wrLeafs.write("\n3 ");
                    wrLeafs.write(s);
                }

                continue;
            }
            if (canStartPoints && !strLine.equals("")) {
                wrLeafs.write("\n" + strLine);
            } else if (canStartPoints && strLine.equals("")) {
                canStartPoints = false;

            }
        }
        wrLeafs.flush();
        wrLeafs.close();
        br.close();
    }

}
