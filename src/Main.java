import lab6.Robinson;

import java.io.IOException;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        try{

            Robinson robinson = new Robinson("matrix.mtr");
            robinson.readFromFile();
            robinson.setIterations(10000);
            robinson.fictiveGame();
            System.out.println(Arrays.toString(robinson.getAccumulateChooseFirst()));
            System.out.println(Arrays.toString(robinson.getAccumulateChooseSecond()));
        }catch (IOException e){
            System.out.println(1);
        }
    }
}

