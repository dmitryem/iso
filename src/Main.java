import beans.TreeHolder;
import lab2.MinimalTree;
import lab2.VTKWriter;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        TreeHolder treeHolder = null;
        MinimalTree minimalTree = new MinimalTree("p000001.brs");
        try {
            treeHolder = minimalTree.buildMinimalTree();
            minimalTree = null;
            if (treeHolder != null) {
                VTKWriter vtkWriter = new VTKWriter("simple.vtk");
                vtkWriter.writeToFile(treeHolder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

