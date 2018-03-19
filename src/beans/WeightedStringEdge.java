package beans;

/**
 * @author demelyanov
 * 17.03.18
 */
public class WeightedStringEdge {

    private String edge;
    private double weight;

    public WeightedStringEdge(String edge, double weight){
        this.edge = edge;
        this.weight = weight;
    }

    public WeightedStringEdge(){

    }

    public double getWeight() {
        return weight;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return edge.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if( obj instanceof WeightedStringEdge) {
            equals = edge.equals(((WeightedStringEdge) obj).getEdge());
        }
        return equals;
    }
}
