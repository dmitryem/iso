package lab2;

import beans.Edge;
import beans.Point;
import beans.PointFather;
import beans.TreeHolder;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class VTKWriter {

    private String filename;
    private TreeHolder holder;
    private Integer[] length;


    public VTKWriter(String fileName) {
        this.filename = fileName;
    }

    public void writeToFile(TreeHolder pHolder) throws IOException {

        holder = pHolder;
        List<Edge> edges = holder.getEdges();
        List<Point> points = holder.getPoints();
        length = new Integer[points.size()];
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        wr.write("# vtk DataFile Version 1.0\n" +
                "...\n" +
                "ASCII\n\nDATASET POLYDATA\n");

        wr.write("POINTS " + points.size() + " float\n");

        for (Point p : points) {
            wr.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wr.newLine();
        wr.write("LINES " + edges.size() + " " + edges.size() * 3 + "\n");
        for (Edge e : edges) {
            wr.write("2 " + e.getFirstPoint() + " " + e.getSecondPoint() + "\n");
        }
        BFS(points.size() / 5);

        wr.write("POINT_DATA " + length.length +
                "\nSCALARS temp int\n" +
                "LOOKUP_TABLE default\n");
        for (int i = 0; i < length.length; i++) {
            wr.write(length[i].toString() + "\n");
        }
        wr.flush();
        wr.close();

        /*BufferedWriter wrLeafs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("points.vtk")));
        wrLeafs.write("# vtk DataFile Version 1.0\n" +
                "...\n" +
                "ASCII\n\nDATASET STRUCTURED_GRID\nDIMENSIONS "+ leafs.size() + " "+ leafs.size() + " "+ leafs.size() + "\n");

        wrLeafs.write("POINTS " + leafs.size() + " float\n");

        for (Point p : leafs) {
            wrLeafs.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }*/
        /*wrLeafs.newLine();
        wrLeafs.write("LINES " + edges.size() + " " + edges.size() * 3 + "\n");
        for (Edge e : edges) {
            wr.write("2 " + e.getFirstPoint() + " " + e.getSecondPoint() + "\n");
        }
        BFS(points.size() / 5);

        wr.write("POINT_DATA " + length.length +
                "\nSCALARS temp int\n" +
                "LOOKUP_TABLE default\n");
        for (int i = 0; i < length.length; i++) {
            wr.write(length[i].toString() + "\n");
        }*/
        /*wrLeafs.flush();
        wrLeafs.close();*/



    }


    private List<List<Integer>> buildNeighbors() {
        List<Edge> edges = holder.getEdges();
        List<Point> points = holder.getPoints();
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

    List<Point> leafs = new ArrayList<>();

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
            if(localNeighbors.size() == 1 && visited[localNeighbors.get(0)]){
                leafs.add(holder.getPoints().get(node.getNode()));
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