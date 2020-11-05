package sample;

import javafx.scene.canvas.GraphicsContext;

class MyLine extends MyShape {
    MyPoint p1, p2;
    MyPoint [] pLine = new MyPoint[2];
    //Constructors
    MyLine(MyPoint p1, MyPoint p2) {
        super(new MyPoint(0,0));
        this.p1 = p1;
        this.p2 = p2;
        pLine[0] = p1;
        pLine[1] = p2;
    }
    MyLine(MyPoint p1, MyPoint p2, MyColor color) {
        super(new MyPoint(0,0), color);
        this.p1 = p1;
        this.p2 = p2;
        pLine[0] = p1;
        pLine[1] = p2;
    }
    //Getters
    public double getLength(){
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(),2));
    }
    public double get_xAngle(){
        return Math.toDegrees(Math.atan2((double) (p2.getX() - p1.getX()), (double) (p2.getY() - p1.getY())));
    }
    //Methods
    @Override
    public String toString(){ return "Line: [(" + p1.getX() +", "
            + p1.getY() + "), (" + p2.getX() + ", " + p2.getY() + ")], Length: "
            + getLength() + " and angle: " + get_xAngle();
    }
    @Override
    public void draw(GraphicsContext GC){
        GC.setStroke(super.getColor());
        GC.setLineWidth(1);
        GC.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    public double getPerimeter(){ return getLength(); }
    @Override
    public double getArea(){ return 0; }
    //Interface Methods
    public MyRectangle getBoundingRectangle() {
        MyPoint p = new MyPoint(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(),p2.getY()));
        return new MyRectangle(p, Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

    public MyPoint[][] getMyShapeArea(){
        MyPoint[][] pArea = this.getBoundingRectangle().getMyShapeArea();

        MyRectangle R = this.getBoundingRectangle();
        int xMin = R.p.getX();
        int xMax = xMin + R.getWidth();
        int yMin = R.p.getY();
        int yMax = yMin + R.getHeight();

        int i = 0;
        for(int x = xMin; x <= xMax; x++){
            int j = 0;
            for(int y = yMin; y <= yMax; y++) {
                MyLine l = new MyLine(new MyPoint(x,y), this.p2);
                if (l.get_xAngle() != this.get_xAngle()) {
                    pArea[i][j] = null;
                }
            }
        }
        return pArea;
    }
}
