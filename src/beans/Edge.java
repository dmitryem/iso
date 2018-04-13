package beans;

/**
 * Created by demelyanov on 02.03.2018.
 */
public class Edge{


    private int firstPoint;
    private int secondPoint;
    private double weight;
    private double flow;

    public Edge(int t,int s,double w){
        setFirstPoint(s);
        setSecondPoint(t);
        setWeight(w);
    }

    public Edge() {

    }


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


    public void setFlow(double flow) {
        this.flow = flow;
    }

    public double getFlow() {
        return flow;
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

    public static double distance(Point p1, Point p2) {
        double dx = Math.pow(p1.getX() - p2.getX(), 2);
        double dy = Math.pow(p1.getY() - p2.getY(), 2);
        double dz = Math.pow(p1.getZ() - p2.getZ(), 2);
        return Math.sqrt(dx + dy + dz);
    }
}
