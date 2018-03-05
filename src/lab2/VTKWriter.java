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
        Map<Point,Integer> pointMap = new HashMap<>();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        wr.write("# vtk DataFile Version 1.0\n" +
                "Stroked lines spell hello...\n" +
                "ASCII\n\nDATASET POLYDATA\n");
        wr.write("POINTS " + points.size() + " float\n");

        int firstNumber, secondNumber;
        StringBuffer buffer = new StringBuffer();
        buffer.append("LINES ").append(edges.size()).append(" ").append(edges.size()*3).append("\n");
        for(Edge e : edges){
            Point firstPoint = points.get(e.getFirstPoint());
            Point secondPoint = points.get(e.getSecondPoint());
            int n = pointMap.size();
            if(!pointMap.containsKey(firstPoint)){
                pointMap.put(firstPoint,n++);
                firstNumber = n;
            }else{
                if(pointMap.get(firstPoint) ==null){
                    System.out.println("1");
                }
                firstNumber = pointMap.get(firstPoint);
            }
            if(!pointMap.containsKey(secondPoint)){
                pointMap.put(secondPoint,n++);
                secondNumber = n;
            }else{
                secondNumber = pointMap.get(secondPoint);
            }
            buffer.append("2 ").append(firstNumber).append(" ").append(secondNumber).append("\n");
        }
        holder = null;
        edges = null;
        points = null;

        points = Arrays.asList(new Point[pointMap.size()]);
        for(Point p : pointMap.keySet()){
            int number = pointMap.get(p);
            points.set(number,p);
        }
        for (Point p : points) {
            wr.write(p.getX() + " " + p.getY() + " " + p.getZ() + "\n");
        }
        wr.newLine();
        wr.write(String.valueOf(buffer));


        wr.flush();
        wr.close();

    }




}