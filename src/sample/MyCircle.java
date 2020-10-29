package sample;

import javafx.scene.canvas.GraphicsContext;

class  MyCircle extends MyOval {
    MyPoint p;
    double r;
    //Constructors:
    MyCircle(MyPoint p, double r, MyColor color) {
        super(new MyPoint((int) (p.getX()-r), (int) (p.getY()-r)), r, r, color);
        this.p = p;
        this.r = r;
    }
    MyCircle(MyPoint p, double r) {
        super(new MyPoint((int) (p.getX()-r), (int) (p.getY()-r)), r, r);
        this.p = p;
        this.r = r;
    }
    //Methods:
    @Override
    public double getArea() { return Math.PI * Math.pow(r, 2); } // A = (PI)r^2
    @Override
    public double getPerimeter(){ return Math.PI*(r*2); } //Circumference = 2(PI)r
    @Override
    public String toString(){
        return "Circle with Radius: " + r + ", Area: " + getArea() + ", and Perimeter: "
                + getPerimeter() + ", at X = " + p.getX() + " and Y = " + p.getY();
    }
    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(super.getColor());
        GC.fillOval(super.p.getX(), super.p.getY(), 2*r, 2*r);
    }
    public void stroke(GraphicsContext GC) {
        GC.setFill(MyColor.BLACK.getColor());
        GC.strokeOval(super.p.getX(), super.p.getY(), 2*r, 2*r);
    }
    //Abstract Methods
    public MyRectangle getBoundingRectangle(){
        MyPoint q = new MyPoint(p.getX() - (int) r,p.getY() - (int) r);
        return new MyRectangle(q, (int) r*2, (int) r*2);
    }
    public MyPoint[][] getMyShapeArea(){
        MyPoint[][] pArea = this.getBoundingRectangle().getMyShapeArea();
        int i = 0;
        for(int x = p.getX() - (int) r; x < p.getX(); x++){
            int j = 0;
            for(int y = p.getY() - (int) r; y < p.getY(); y++) {
                if (pArea[i][j].distance(p) > r) {
                    pArea[i][j] = null;
                    pArea[(int) r*2 - i][j] = null;
                    pArea[i][(int) r*2 - j] = null;
                    pArea[(int) r*2 - i][(int) r*2 - j] = null;
                }
            }
        }
        return pArea;
    }
}
