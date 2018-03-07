package beans;

/**
 * Created by demelyanov on 07.03.2018.
 */
public class PointFather {
    private int node;
    private int father;

    public PointFather(int node,int father){
        this.node = node;
        this.father = father;
    }

    public int getFather() {
        return father;
    }

    public int getNode() {
        return node;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public void setNode(int node) {
        this.node = node;
    }
}
