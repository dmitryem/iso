package lab6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by demelyanov on 25.04.2018.
 */
public class Robinson {

    private final String filename;

    private int n = -1;
    private int m = -1;
    private int iterations = 12;

    List<List<Integer>> strategiesFirst = new ArrayList<>();
    List<List<Integer>> strategiesSecond = new ArrayList<>();

    private int[] accumulateFirst;
    private int[] accumulateSecond;

    int[] accumulateChooseFirst;
    int[] accumulateChooseSecond;

    public Robinson(String filename){
        this.filename = filename;
    }


    public void readFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String str = br.readLine();
        String[] sizes =str.split(" ");
        n = Integer.parseInt(sizes[0]);
        m = Integer.parseInt(sizes[1]);
        accumulateFirst =  new int[n];
        accumulateSecond =  new int[m];
        accumulateChooseFirst = new int[n];
        accumulateChooseSecond = new int[m];
        for(int i = 0;i < n;i++){
            str = br.readLine();
            sizes = str.split(" ");
            strategiesFirst.add(new ArrayList<>());
            for(int j = 0;j < m;j++){
                int ij = Integer.parseInt(sizes[j]);
                if(strategiesSecond.size() != m){
                    strategiesSecond.add(new ArrayList<>());
                }
                strategiesFirst.get(i).add(ij);
                strategiesSecond.get(j).add(ij);
            }
        }
        br.close();
    }



    private StringBuilder detalized = new StringBuilder();

    public StringBuilder getDetalized() {
        return detalized;
    }

    public void fictiveGame(){
        int count = 0;
        int firstChoose = 0;//new Random().nextInt(n);
        int secondChoose = 0;//new Random().nextInt(m);
        while(count != iterations) {
            accumulateChooseFirst[firstChoose]++;
            accumulateChooseSecond[secondChoose]++;
            for (int i = 0; i < n; i++) {
                accumulateFirst[i] += strategiesSecond.get(firstChoose).get(i);
            }
            for (int i = 0; i < m; i++) {
                accumulateSecond[i] += strategiesFirst.get(secondChoose).get(i);
            }
            detalized.append(count).append(" ").append(firstChoose).append(" ").append(secondChoose).append(" ")
                    .append(Arrays.toString(accumulateFirst)).append(" ").append(Arrays.toString(accumulateSecond)).append(" ").append("\n");
            int maxFirst = Integer.MIN_VALUE;
            for(int i = 0;i < n;i++) {
                if(accumulateFirst[i] > maxFirst){
                    maxFirst = accumulateFirst[i];
                    firstChoose = i;
                }
            }
            maxFirst = Integer.MAX_VALUE;
            for(int i = 0;i < m;i++) {
                if(accumulateSecond[i] < maxFirst){
                    maxFirst = accumulateSecond[i];
                    secondChoose = i;
                }
            }
            count++;
        }
    }



    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int[] getAccumulateFirst() {
        return accumulateFirst;
    }

    public int[] getAccumulateSecond() {
        return accumulateSecond;
    }

    public double[] getAccumulateChooseFirst() {
        double[] array = new double[n];
        for(int i = 0;i < n;i++){
            array[i] = ((double)accumulateChooseFirst[i])/iterations;
        }
        return array;
    }

    public double[] getAccumulateChooseSecond() {
        double[] array = new double[m];
        for(int i = 0;i < m;i++){
            array[i] = ((double)accumulateChooseSecond[i])/iterations;
        }
        return array;
    }
}
