package beans;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class Edge{

    private int firstPoint;
    private int secondPoint;
    private double weight;

    public int getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(int firstPoint) {
        this.firstPoint = firstPoint;
    }

    public int getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(int secondPoint) {
        this.secondPoint = secondPoint;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Edge){
            Edge edgeObj = (Edge) obj;
            return edgeObj.getFirstPoint() == firstPoint && edgeObj.getSecondPoint() == secondPoint;
        }else {
            return false;
        }
    }
}
