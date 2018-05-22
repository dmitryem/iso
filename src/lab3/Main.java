package lab3;

import lab3.PathBetweenTriangles;
import java.io.*;


public class Main {


    public static void main(String[] args) {

        try {
           // BRSToVTK.writeBRSToVTK("1023082.brs","initial.vtk");
            long time = System.currentTimeMillis();
            PathBetweenTriangles path = new PathBetweenTriangles("1023082.brs");
            System.out.println(System.currentTimeMillis() - time);
            time = System.currentTimeMillis();
            path.writePathToFile("path.vtk",46686,129047);
            System.out.println(System.currentTimeMillis() - time);
            time = System.currentTimeMillis();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

