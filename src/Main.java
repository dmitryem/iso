import beans.Point;
import lab5.Shteiner;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        try {

            Shteiner shteiner = new Shteiner(13, 6);
            shteiner.initializePoints(initialPoints -> {
                initialPoints.add(new Point(0, 0));
                initialPoints.add(new Point(0, 3));
                initialPoints.add(new Point(0, 6));
                initialPoints.add(new Point(3, 3));
                initialPoints.add(new Point(4, 0));
                initialPoints.add(new Point(4, 6));
                initialPoints.add(new Point(7, 2));
                initialPoints.add(new Point(8, 0));
                initialPoints.add(new Point(9, 2));
                initialPoints.add(new Point(10, 6));
                initialPoints.add(new Point(11, 2));
                initialPoints.add(new Point(12, 0));
                initialPoints.add(new Point(13, 2));
            });
            shteiner.setAddedPoints(3);
            long time = System.currentTimeMillis();
            shteiner.writeShteinerToFile("shteiner.vtk");
            System.out.println(System.currentTimeMillis() - time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

