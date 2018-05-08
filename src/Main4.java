import lab4.MaxFlow;

import java.io.IOException;


public class Main4 {


    public static void main(String[] args) {

        try {
            //BRSToVTK.writeBRSToVTK("1023082.brs","initial.vtk");
            long time = System.currentTimeMillis();
            System.out.println("time: " + (System.currentTimeMillis() - time));
            MaxFlow maxFlow = new MaxFlow("1023082.brs");
            time = System.currentTimeMillis();
            //maxFlow.writeToFile("flow.vtk",46686,129047);
            maxFlow.writeToFile("flow.vtk",46685,46686);
            System.out.println("time: " +(System.currentTimeMillis() - time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

