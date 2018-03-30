package lab3;

import beans.Point;
import beans.Vertex;
import beans.WeightedStringEdge;

import java.io.*;
import java.util.*;

/**
 * @author demelyanov
 * 15.03.18
 */
public class PathBetweenTriangles {
    private String filename;
    private List<Vertex> vertices;


    public PathBetweenTriangles(String filename) throws IOException {
        this.filename = filename;
        vertices = new ArrayList<>();
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
                continue;
            }
            if (canStartEdges && !strLine.equals("")) {
                Vertex vertex = new Vertex(strLine);
                String[] sNumbers = strLine.split(" ");
                int[] numbers = {0, 0, 0};
                numbers[0] = Integer.parseInt(sNumbers[0]);
                numbers[1] = Integer.parseInt(sNumbers[1]);
                numbers[2] = Integer.parseInt(sNumbers[2]);
                for (int i = 0; i < 3; i++) {
                    WeightedStringEdge edge = WeightedStringEdge.getEdgeAsString(numbers[i], numbers[(i + 1) % 3], points);
                    processEdge(vertex, edge, neighbors);
                }
                vertex.setNumber(vertices.size());
                vertices.add(vertex);
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





    private void processEdge(Vertex vertex, WeightedStringEdge edge, Map<WeightedStringEdge, Integer> neighbors) {
        Integer number = neighbors.get(edge);
        if (number == null) {
            neighbors.put(edge, vertices.size());
        } else {
            Vertex neighbor = vertices.get(number);
            neighbor.addToNeighbors(vertices.size(), edge.getWeight());
            vertex.addToNeighbors(number, edge.getWeight());
        }
    }

    private List<String> getShortestWay(int start, int end) {
        List<String> way;
        if (start > vertices.size() || end > vertices.size()) {
            way = null;
        } else if (start < 0 || end < 0) {
            way = null;
        } else {
            vertices.get(start).setWay(0);
            way = deikstra(start, end);
        }
        return way;

    }

    private List<String> deikstra(int start, int end) {
        List<List<String>> p = Arrays.asList(new ArrayList[vertices.size()]);
        p.set(start, new ArrayList<>());
        TreeSet<Vertex> d = new TreeSet<>(vertices);
        while (true) {
            if (d.isEmpty()) {
                break;
            }
            Vertex v = d.first();
            if (v == null || v.getWay() == Double.POSITIVE_INFINITY) {
                break;
            }
            v.setVisited(true);
            d.remove(v);
            Map<Integer, Double> neighbors = v.getNeighbors();
            for (Integer tr : neighbors.keySet()) {
                Vertex u = vertices.get(tr);
                if (u.getWay() > v.getWay() + neighbors.get(tr)) {
                    if (!u.isVisited()) {
                        d.remove(u);
                        u.setWay(v.getWay() + neighbors.get(tr));
                        d.add(u);
                    } else {
                        u.setWay(v.getWay() + neighbors.get(tr));
                    }
                    List<String> path = new ArrayList<>(p.get(v.getNumber()));
                    path.add(v.getTriangle());
                    p.set(tr, path);
                }
            }
        }

        return p.get(end);
    }

    public void writePathToFile(String out, int start, int end) throws IOException {
        List<String> sPath = getShortestWay(start, end);
        BufferedWriter wrLeafs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
        wrLeafs.write("# vtk DataFile Version 1.0\n...\nASCII\n\nDATASET POLYDATA\n");

        boolean canStartPoints = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            if (strLine.contains("vertices")) {
                canStartPoints = true;
                wrLeafs.write("POINTS " + strLine.split(" ")[1] + " float\n");
                continue;
            }
            if (strLine.contains("triangles")) {
                if (sPath != null) {
                    wrLeafs.write("\nTRIANGLE_STRIPS " + sPath.size() + " " + sPath.size() * 4);
                    for (String s : sPath) {
                        wrLeafs.write("\n3 ");
                        wrLeafs.write(s);
                    }
                }
                continue;
            }
            if (canStartPoints && !strLine.equals("")) {
                wrLeafs.write("\n" + strLine);
            } else if (canStartPoints && strLine.equals("")) {
                canStartPoints = false;

            }
        }
        br.close();
        wrLeafs.flush();
        wrLeafs.close();
    }

}
