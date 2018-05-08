import lab2.MinimalTree;

import java.io.IOException;

public class Main2 {


    public static void main(String[] args) {
        MinimalTree minimalTree = new MinimalTree("1023082.brs");
        try {
            long time = System.currentTimeMillis();
            minimalTree.buildMinimalTree();
            minimalTree.writeToFile("simple.vtk","points.vtk");
            System.out.println(System.currentTimeMillis() - time );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

