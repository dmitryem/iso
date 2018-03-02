package beans;

/**
 * @author demelyanov
 * 20.02.18
 */
public class StringEdge {
    private String number;
    private  int triangleNumber = -1;

    public int getTriangleNumber() {
        return triangleNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTriangleNumber(int triangleNumber) {
        this.triangleNumber = triangleNumber;
    }
}
