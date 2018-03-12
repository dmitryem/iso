import lab2.MinimalTree;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        MinimalTree minimalTree = new MinimalTree("1023082.brs");
        try {
            minimalTree.buildMinimalTree();
            minimalTree.writeToFile("simple.vtk");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

