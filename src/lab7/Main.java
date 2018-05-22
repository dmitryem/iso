package lab7;

import lab7.Bag;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        Bag bag = new Bag("bag.bg");
        try {
            bag.readFromFile();
            bag.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
