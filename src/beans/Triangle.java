package beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author demelyanov
 *         20.02.18
 */
public class Triangle {

    private int number;
    private int setNumber = -1;
    private boolean isVisited;
    private List<Integer> neighbors = new ArrayList<>();

    public int getNumber() {
        return number;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public List<Integer> getNeighbors() {
        return neighbors;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNeighbors(List<Integer> neighbors) {
        this.neighbors = neighbors;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public void setVisited() {
        isVisited = true;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void addNeighbor(int i) {
        if (neighbors != null) {
            neighbors.add(i);
        }

    }
}
