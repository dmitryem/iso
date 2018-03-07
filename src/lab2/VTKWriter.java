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
                "Stroked lines spell hello...\n" +
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
        BFS(2);
        wr.write("FIELD FieldData 1\n" +
                "length 1 "+ length.length  +" int\n");
        for (int i = 0; i < length.length; i++) {
            if (length[i] == null) {
                wr.write("-1 ");
            } else {
                wr.write(length[i].toString() + " ");
            }
        }
        wr.flush();
        wr.close();

    }

    private void BFS(int start_node) {
        List<List<Integer>> neighbors = holder.getNeighbors();
        boolean[] visited = new boolean[neighbors.size()];
        Queue<PointFather> queue = new LinkedList<>();
        queue.add(new PointFather(start_node, start_node));
        visited[start_node] = true;
        length[start_node] = 0;
        while (!queue.isEmpty()) {
            PointFather node = queue.poll();
            visited[node.getNode()] = true;
            length[node.getNode()] = length[node.getFather()] + 1;
            List<Integer> localNeighbors = neighbors.get(node.getNode());
            for (int l : localNeighbors) {
                if (!visited[l]) {
                    queue.add(new PointFather(l, node.getNode()));
                    visited[l] = true;
                }
            }
        }
    }

}