import java.io.*;
import java.util.*;

public class Main {

    private static int N = 0;
    private static int numberOfSets = 0;
    private static boolean canStart = false;
    private static List<Edge> list = new LinkedList<>();
    private static List<Triangle> triangles;

    public static void main(String[] args) {

        try {
            FileInputStream fstream = new FileInputStream("p000001.brs");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int number = 0;
            while ((strLine = br.readLine()) != null) {
/*                if(strLine.contains("vertices")){
                    N = Integer.parseInt(strLine.split(" ")[1]);
                }*/
                if (strLine.contains("triangles")) {
                    N = Integer.parseInt(strLine.split(" ")[1]);
                    triangles = Arrays.asList(new Triangle[N]);
                    canStart = true;
                    continue;
                }
                if (canStart && !strLine.equals("")) {
                    String[] sNumbers = strLine.split(" ");
                    int[] numbers = {0, 0, 0};

                    Triangle tr = new Triangle();
                    tr.number = number;
                    number++;
                    numbers[0] = Integer.parseInt(sNumbers[0]);
                    numbers[1] = Integer.parseInt(sNumbers[1]);
                    numbers[2] = Integer.parseInt(sNumbers[2]);
                    list.add(createEdge(numbers[0], numbers[1], tr.number));
                    list.add(createEdge(numbers[1], numbers[2], tr.number));
                    list.add(createEdge(numbers[2], numbers[0], tr.number));
                    triangles.set(tr.number, tr);
                } else if (canStart && strLine.equals("")) {
                    canStart = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //m log m
        list.sort(Comparator.comparing(o -> o.number));
        Iterator<Edge> iterator = list.iterator();
        Edge previous = iterator.next();
        //m
        while (iterator.hasNext()) {
            Edge current = iterator.next();
            if(Objects.equals(current.number, previous.number)){
                triangles.get(previous.triangleNumber).neighbors.add(current.triangleNumber);
                triangles.get(current.triangleNumber).neighbors.add(previous.triangleNumber);
                iterator.remove();
            }else {
                previous = current;
            }
        }
        for(Triangle tr: triangles){
            if(!tr.isVisited){
                depthGo(tr);
            }
        }
        System.out.println(numberOfSets);
    }

    private static Edge createEdge(int a, int b, int trNumber) {
        Edge e = new Edge();
        e.triangleNumber = trNumber;
        if (a < b) {
            e.number = String.valueOf(a) + "/" + String.valueOf(b);
        } else {
            e.number = String.valueOf(b) + "/" + String.valueOf(a);
        }
        return e;
    }

    private static Stack<Triangle> stack = new Stack<>();

    private static void depthGo(Triangle triangle){
        stack.push(triangle);
        while(!stack.empty()) {
            triangle = stack.pop();
            triangle.isVisited = true;
            if (triangle.setNumber == -1) {
                triangle.setNumber = numberOfSets;
                numberOfSets++;
            }
            for (int trNumber : triangle.neighbors) {
                Triangle neighTriangle = triangles.get(trNumber);
                if (!neighTriangle.isVisited) {
                    neighTriangle.setNumber = triangle.setNumber;
                    stack.push(neighTriangle);
                }
            }
        }
    }


}
