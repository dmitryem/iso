package lab7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Bag {
    private final String filename;
    private int countOfItems = -1;
    private int capacity = -1;
    private List<Integer> costs;
    private List<Integer> weighs;

    public Bag(String filename) {
        this.filename = filename;
        costs = new ArrayList<>();
        weighs = new ArrayList<>();
    }

    public void readFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String str = br.readLine();
        countOfItems = Integer.parseInt(str);
        str = br.readLine();
        String[] costsS = str.split(" ");
        for (String s : costsS){
            costs.add(Integer.parseInt(s));
        }
        str = br.readLine();
        String[] weighsS = str.split(" ");
        for (String s : weighsS){
            weighs.add(Integer.parseInt(s));
        }
        str = br.readLine();
        capacity = Integer.parseInt(str);
        br.close();
    }

    public void run() {
        int[][] cells = new int[countOfItems][capacity];
        ArrayList[][] cellsOfItems = new ArrayList[countOfItems][capacity];
        for (int i = 0; i < countOfItems; i++) {
            for (int j = 0; j < capacity; j++) {
                cellsOfItems[i][j] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < countOfItems; i++) {
            for (int j = 0; j < capacity; j++) {
                if (i == 0) {
                    if (weighs.get(i) <= j + 1) {
                        cells[i][j] =costs.get(i);
                        cellsOfItems[i][j].add(i);
                    } else {
                        cells[i][j] = 0;
                    }
                } else {
                    if (weighs.get(i) <= j + 1) {
                        cells[i][j] = costs.get(i);
                        cellsOfItems[i][j].add(i);
                        if (j + 1 - weighs.get(i) > 0) {
                            cells[i][j] += cells[i - 1][j - weighs.get(i)];
                            cellsOfItems[i][j].addAll(cellsOfItems[i - 1][j - weighs.get(i)]);
                        }
                        if (cells[i - 1][j] >cells[i][j])
                        {

                            cells[i][j] =cells[i - 1][j];
                            cellsOfItems[i][j] =cellsOfItems[i - 1][j];
                        }
                    } else {
                        cells[i][j] =cells[i - 1][j];
                        cellsOfItems[i][j] =cellsOfItems[i - 1][j];
                    }
                }
            }
        }
        for (int i = 0; i < countOfItems; i++) {
            for (int j = 0; j < capacity; j++) {
                System.out.print(cells[i][j] +" " );
            }
            System.out.println();
        }
        for (int i = 0; i < countOfItems; i++) {
            for (int j = 0; j < capacity; j++) {
                System.out.print("(");
                System.out.print(cellsOfItems[i][j]);
                System.out.print(")");
            }
            System.out.println();
        }
    }
}

