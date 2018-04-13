package lab5;

import beans.Point;

/**
 * Created by demelyanov on 13.04.2018.
 */
public class PointGenerator {

    private final int maxX;
    private final int maxY;
    private int currentX;
    private int currentY;

    public  PointGenerator(int maxX,int maxY){
        this.maxX =  maxX+1;
        this.maxY =  maxY+1;
        currentX = 0;
        currentY = 0;
    }

     private Point point = null;

    public Point getNextPoint(){
        return point;
    }

    public boolean hasNextPoint(){
        boolean has = false;
        point = null;
        if(currentX == maxX){
            currentY++;
            currentX = 0;
        }
        if(currentY < maxY){
            point = new Point(currentX++,currentY);
            has = true;
        }
        return has;
    }

}
