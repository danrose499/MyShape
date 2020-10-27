package sample;

interface MyShapeInterface {
    //Abstract Methods
    MyRectangle getBoundingRectangle(); //Returns bounding rectangle
    MyPoint[][] getMyShapeArea(); //Returns area of canvas occupied by MyShape object

    //Static Methods
    static boolean doOverlapMyRectangles(MyRectangle R1, MyRectangle R2){ //Checks for overlap
        int x1 = R1.getPoint().getX();
        int y1 = R1.getPoint().getY();
        int w1 = R1.getWidth();
        int h1 = R1.getHeight();

        int x2 = R2.getPoint().getX();
        int y2 = R2.getPoint().getY();
        int w2 = R2.getWidth();
        int h2 = R2.getHeight();
        //No Overlap: One MyRectangle object is above the other
        if(y1 + h1 < y2 || y1 > y2 + h2) { return false; }
        //No overlap: One MyRectangle is next to the other
        if(x1 + w1 < x2 || x1 > x2 + w2) { return false; }
        return true;
    }
    static MyRectangle overlapMyRectangles(MyRectangle R1, MyRectangle R2){
        if(!doOverlapMyRectangles(R1, R2)) { return null; }
        else{
            int x1 = R1.getPoint().getX();
            int y1 = R1.getPoint().getY();
            int w1 = R1.getWidth();
            int h1 = R1.getHeight();

            int x2 = R2.getPoint().getX();
            int y2 = R2.getPoint().getY();
            int w2 = R2.getWidth();
            int h2 = R2.getHeight();
            //Return Overlap
            int xMax = Math.max(x1, x2);
            int yMax = Math.max(y1, y2);
            int xMin = Math.min(x1 + w1, x2 + w2);
            int yMin = Math.min(y1 + h1, y2 + h2);

            MyPoint p = new MyPoint(xMax, yMax);
            return new MyRectangle(p, Math.abs(xMin - xMax), Math.abs(yMin - yMax));
        }
    }
    static MyRectangle overlapMyShapes(MyShape S1, MyShape S2) {
        if(S1 instanceof MyLine || S2 instanceof MyLine) { return null; }
        MyRectangle R1 = S1.getBoundingRectangle();
        MyRectangle R2 = S2.getBoundingRectangle();
        return overlapMyRectangles(R1, R2);
    }
}