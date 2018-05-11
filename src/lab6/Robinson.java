package lab6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by demelyanov on 25.04.2018.
 */
public class Robinson {
    
    private final String filename;
    
    private int n = -1;
    private int m = -1;
    private int iterations = 12;
    
    private List<List<Integer>> strategiesFirst = new ArrayList<>();
    private List<List<Integer>> strategiesSecond = new ArrayList<>();
    
    private int[] accumulateFirst;
    private int[] accumulateSecond;
    
    private int[] accumulateChooseFirst;
    private int[] accumulateChooseSecond;
    
    public Robinson(String filename){
        this.filename = filename;
    }
    
    
    public void readFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String str = br.readLine();
        String[] sizes =str.split(" ");
        n = Integer.parseInt(sizes[0]);
        m = Integer.parseInt(sizes[1]);
        System.out.println(n + " " + m);
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
        int firstChoose = 0;// new Random().nextInt(n);
        int secondChoose = 0;//new Random().nextInt(m);
        while(count != iterations) {
            accumulateChooseFirst[firstChoose]++;
            accumulateChooseSecond[secondChoose]++;
            for (int i = 0; i < n; i++) {
                accumulateFirst[i] += strategiesSecond.get(secondChoose).get(i);
            }
            for (int i = 0; i < m; i++) {
                accumulateSecond[i] += strategiesFirst.get(firstChoose).get(i);
            }
            detalized.append("#").append(++count).append("\t1. ").append(firstChoose).append("\t2. ").append(secondChoose).append("\t")
            .append(Arrays.toString(accumulateFirst)).append("\t").append(Arrays.toString(accumulateSecond)).append("\n");
            
            int maxFirst = Integer.MIN_VALUE;
            int[] indexesFirst = getShuffledArray(n);
            for (int anIndexesFirst : indexesFirst) {
                if (accumulateFirst[anIndexesFirst] > maxFirst) {
                    maxFirst = accumulateFirst[anIndexesFirst];
                    firstChoose = anIndexesFirst;
                }
            }
            
            int minFirst = Integer.MAX_VALUE;
            int[] indexesSecond = getShuffledArray(m);
            for (int anIndexesSecond : indexesSecond) {
                if (accumulateSecond[anIndexesSecond] < minFirst) {
                    minFirst = accumulateSecond[anIndexesSecond];
                    secondChoose = anIndexesSecond;
                }
            }
        }
    }
    
    
    
    private int[] getShuffledArray(int n)
    {
        int[] ar = new int[n];
        for(int i = 0; i < n; i++){
            ar[i] = i;
        }
        
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        
        return ar;
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

