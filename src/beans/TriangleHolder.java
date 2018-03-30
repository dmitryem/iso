package beans;

/**
 * Created by demelyanov on 30.03.2018.
 */
public class TriangleHolder {

    private String triangle;
    private int number;

    public TriangleHolder(String tr, int n){
        triangle = tr;
        number = n;
    }

    public int getNumber() {
        return number;
    }

    public String getTriangle() {
        return triangle;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTriangle(String triangle) {
        this.triangle = triangle;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if( obj instanceof TriangleHolder) {
            equals = number == ((TriangleHolder) obj).number;
        }
        return equals;
    }
}
