package sample;

import javafx.scene.canvas.GraphicsContext;

class MyOval extends MyShape{
    MyPoint p;   //P is the top left corner of inscribing rectangle
    double w, h; //Width and Height of inscribing rectangle
    double a, b; //Semi Major and Semi Minor axes
    //Constructors
    MyOval(MyPoint p, double w, double h, MyColor color) {
        super(new MyPoint(0, 0), color);
        this.p = p;
        setAxes(w, h);
    }
    MyOval(MyPoint p, double h, double w) {
        super(new MyPoint(0, 0));
        this.p = p;
        setAxes(w, h);
    }
    //Setters
    public void setAxes(double w, double h) {
        this.a = w/2;
        this.b = h/2;
        this.w = w;
        this.h = h;
    }
    public void setCenter(MyPoint newP) { //To set p from newP, shift left and up
        p.setX(newP.getX() - (int) a);
        p.setY(newP.getY() - (int) b);
    }
    //Methods
    public MyPoint getCenter() { //P is top left corner, we need to shift down and across
        return new MyPoint(p.getX() + (int) a, p.getY() + (int) b);
    }
    @Override
    public double getArea() { return Math.PI*a*b; }
    @Override
    public double getPerimeter() { return Math.PI*(3*(a+b)-Math.sqrt((3*a+b)*(a+3*b))); } //Ramanujan's approximation
    @Override
    public String toString() {
        return "Oval with width: " + w + ", height: " + h + ", perimeter: "
                + getPerimeter() + ", and area: " + getArea();
    }
    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(super.getColor());
        GC.fillOval(p.getX(), p.getY(), w, h);
    }
    public MyRectangle getBoundingRectangle(){
        return new MyRectangle(p, (int) w, (int) h);
    }
    public MyPoint[][] getMyShapeArea(){
        MyPoint[][] pArea = this.getBoundingRectangle().getMyShapeArea();
        int i = 0;
        for(int x = p.getX(); x < getCenter().getX(); x++){
            int j = 0;
            for(int y = p.getY(); y < getCenter().getY(); y++) {
                double within = (Math.pow((x-getCenter().getX()), 2)/Math.pow(a,2))
                        + (Math.pow((y-getCenter().getY()),2)/Math.pow(b, 2));
                if (within > 1) {
                    pArea[i][j] = null;
                    pArea[(int) w - i][j] = null;
                    pArea[i][(int) h - j] = null;
                    pArea[(int) w - i][(int) h - j] = null;
                }
            }
        }
        return pArea;
    }
}