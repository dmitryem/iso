package lab2;

import beans.Edge;
import beans.Point;
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

    public VTKWriter(String fileName) {
        this.filename = fileName;
    }

    public void writeToFile(TreeHolder holder) throws IOException {
        List<Edge> edges = holder.getEdges();
        List<Point> points = holder.getPoints();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        wr.write("# vtk DataFile Version 1.0\n" +
                "Stroked lines spell hello...\n" +
                "ASCII\n\nDATASET POLYDATA\n");
        wr.write("POINTS " + points.size() + " float\n");

        for (Point p : points) {
            wr.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wr.newLine();
        wr.write("LINES " + edges.size() +" " + edges.size()*3 + "\n");
        for(Edge e : edges){
            wr.write("2 " + e.getFirstPoint() + " " + e.getSecondPoint() +"\n");
        }
        holder = null;
        edges = null;
        points = null;

        wr.flush();
        wr.close();

    }




}