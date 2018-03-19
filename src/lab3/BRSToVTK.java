package lab3;

import beans.Point;

import java.io.*;

/**
 * @author demelyanov
 * 15.03.18
 */
public class BRSToVTK {

    public static void writeBRSToVTK(String input, String output) throws IOException {
        BufferedWriter wrLeafs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
        wrLeafs.write("# vtk DataFile Version 1.0\n...\nASCII\n\nDATASET POLYDATA\n");

        boolean canStartPoints = false;
        boolean canStartEdges = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            if (strLine.contains("vertices")) {
                canStartPoints = true;
                wrLeafs.write("POINTS " + strLine.split(" ")[1] + " float\n");
                continue;
            }
            if (strLine.contains("triangles")) {
                canStartEdges = true;
                int count = Integer.parseInt(strLine.split(" ")[1]);
                wrLeafs.write("\nLINES " + count + " " + count * 5);
                continue;
            }
            if (canStartEdges && !strLine.equals("")) {
                wrLeafs.write("\n4 " + strLine + " " + strLine.split(" ")[0]);
            }
            if (canStartPoints && !strLine.equals("")) {
                wrLeafs.write("\n" + strLine);
            } else if (canStartPoints && strLine.equals("")) {
                canStartPoints = false;

            } else if (canStartEdges && strLine.equals("")) {
                canStartEdges = false;

            }
        }
        br.close();
        wrLeafs.flush();
        wrLeafs.close();
    }


}
