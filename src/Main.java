import beans.Point;
import beans.TreeHolder;
import lab2.MinimalTree;
import lab2.VTKWriter;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
       /* //ComponentsFinder finder = new ComponentsFinder("1023082.brs");
        ComponentsFinder finder = new ComponentsFinder("p000001.brs");
       // ComponentsFinder finder = new ComponentsFinder("1023082.brs");
        int componentCount = 0;
        try {
            componentCount = finder.getStrongComponentCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(componentCount);*/
        TreeHolder treeHolder = null;
        MinimalTree minimalTree = new MinimalTree("1023082.brs");
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

