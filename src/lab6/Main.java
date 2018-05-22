package lab6;

import lab6.Robinson;

import java.io.IOException;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        playGame("matrix.mtr");
        playGame("m2.mtr");
        playGame("m3.mtr");
        playGame("m4.mtr");

    }


    private static void playGame(String filename){
        try{
            Robinson robinson = new Robinson(filename);
            robinson.readFromFile();
            robinson.setIterations(1000);
            robinson.fictiveGame();
            System.out.println(Arrays.toString(robinson.getAccumulateChooseFirst()));
            System.out.println(Arrays.toString(robinson.getAccumulateChooseSecond()));
            System.out.println("------------------------------------------------");
        }catch (IOException e){
            System.out.println(1);
        }
    }
}

