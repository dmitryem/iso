package lab1;

import beans.StringEdge;
import beans.Triangle;

import java.io.*;
import java.util.*;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class ComponentsFinder {

    private static int numberOfSets = 0;
    private static boolean canStart;
    private static List<StringEdge> list;
    private static List<Triangle> triangles;
    private Stack<Triangle> stack;
    private String fileName;
    private boolean valueStored;

    public ComponentsFinder(String fileName) {
        stack = new Stack<>();
        list = new LinkedList<>();
        canStart = false;
        this.fileName = fileName;
        valueStored = false;
    }

    public int getStrongComponentCount() throws IOException {
        if (!valueStored) {
            readFromFile();
            //m log m
            list.sort(Comparator.comparing(StringEdge::getNumber));
            Iterator<StringEdge> iterator = list.iterator();
            StringEdge previous = iterator.next();
            //m
            while (iterator.hasNext()) {
                StringEdge current = iterator.next();
                if (Objects.equals(current.getNumber(), previous.getNumber())) {
                    triangles.get(previous.getTriangleNumber()).addNeighbor(current.getTriangleNumber());
                    triangles.get(current.getTriangleNumber()).addNeighbor(previous.getTriangleNumber());
                    iterator.remove();
                } else {
                    previous = current;
                }
            }
            for (Triangle tr : triangles) {
                if (!tr.isVisited()) {
                    depthGo(tr);
                }
            }
            valueStored = true;
        }
        return numberOfSets;
    }

    private void readFromFile() throws IOException {

        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        int number = 0;
        while ((strLine = br.readLine()) != null) {
/*                if(strLine.contains("vertices")){
                    N = Integer.parseInt(strLine.split(" ")[1]);
                }*/
            if (strLine.contains("triangles")) {
                int n = Integer.parseInt(strLine.split(" ")[1]);
                triangles = Arrays.asList(new Triangle[n]);
                canStart = true;
                continue;
            }
            if (canStart && !strLine.equals("")) {
                String[] sNumbers = strLine.split(" ");
                int[] numbers = {0, 0, 0};

                Triangle tr = new Triangle();
                tr.setNumber(number);
                number++;
                numbers[0] = Integer.parseInt(sNumbers[0]);
                numbers[1] = Integer.parseInt(sNumbers[1]);
                numbers[2] = Integer.parseInt(sNumbers[2]);
                list.add(createEdge(numbers[0], numbers[1], tr.getNumber()));
                list.add(createEdge(numbers[1], numbers[2], tr.getNumber()));
                list.add(createEdge(numbers[2], numbers[0], tr.getNumber()));
                triangles.set(tr.getNumber(), tr);
            } else if (canStart && strLine.equals("")) {
                canStart = false;
            }
        }
    }


    private StringEdge createEdge(int a, int b, int trNumber) {
        StringEdge e = new StringEdge();
        e.setTriangleNumber(trNumber);
        if (a < b) {
            e.setNumber(String.valueOf(a) + "/" + String.valueOf(b));
        } else {
            e.setNumber(String.valueOf(b) + "/" + String.valueOf(a));
        }
        return e;
    }


    private void depthGo(Triangle triangle) {
        stack.push(triangle);
        while (!stack.empty()) {
            triangle = stack.pop();
            triangle.setVisited();
            if (triangle.getSetNumber() == -1) {
                triangle.setSetNumber(numberOfSets);
                numberOfSets++;
            }
            List<Integer> neighbors = triangle.getNeighbors();
            for (int trNumber : neighbors) {
                Triangle neighTriangle = triangles.get(trNumber);
                if (!neighTriangle.isVisited()) {
                    neighTriangle.setSetNumber(triangle.getSetNumber());
                    stack.push(neighTriangle);
                }
            }
        }
    }

}
