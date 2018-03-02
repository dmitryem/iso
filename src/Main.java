import beans.Point;

public class Main {


    public static void main(String[] args) {
       /* //ComponentsFinder finder = new ComponentsFinder("1023082.brs");
        ComponentsFinder finder = new ComponentsFinder("p000001.brs");
       // ComponentsFinder finder = new ComponentsFinder("1023082.brs");
        int componentCount = 0;
        try {
            componentCount = finder.getStrongComponentCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(componentCount);*/
        Point o1 = new Point();
        o1.setX(1);
        o1.setY(1);
        o1.setZ(1);
        Point o2 = new Point();
        o2.setX(2);
        o2.setY(2);
        o2.setZ(2);
        System.out.println(o1.compareTo(o2));
    }




}
