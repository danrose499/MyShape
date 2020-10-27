package sample;

import javafx.scene.canvas.GraphicsContext;

class MyPolygon extends MyShape {
    int n; // number of the polygon’s equal side lengths and equal interior angles
    int r; //  radius (r)
    MyPoint p;
    //Constructors:
    MyPolygon(MyPoint p, int n, int r, MyColor color) {
        super(new MyPoint(0,0), color);
        this.p = p;
        this.color = color;
        this.n = n;
        this.r = r;
        this.p = p;
    }
    MyPolygon(MyPoint p, int n, int r) {
        super(new MyPoint(0, 0));
        this.p = p;
        this.n = n;
        this.r = r;
        this.p =p;
    }
    //Methods:
    public double getSide() { return 2*r*Math.sin(Math.PI/n); }
    public double getPerimeter() { return getSide()*n; }
    public double getArea() { return ((n*Math.pow(getSide(),2))/(4*Math.tan(Math.PI/n))); }
    public double getAngle() { return 180.0*(n-2)/n; } //180(n-2) = sum of interior angles
    public double getApothem() { return r/(2*Math.tan(Math.PI/n)); }
    @Override
    public String toString(){
        return "Polygon with " + n + " sides of length " + getSide() + ", interior angles of " + getAngle() +
                " degrees, an area of " + getArea() + " and a perimeter of " + getPerimeter();
    }
    @Override
    public void draw(GraphicsContext GC) {
        double[] xVertices = new double[n];
        double[] yVertices = new double[n];
        double centralAngle = 0;
        double inc = (2*Math.PI)/n;
        for(int i = 0; i < n; i++){
            xVertices[i] = (int) (r*Math.sin(centralAngle)+p.getX());
            yVertices[i] = (int) (-r*Math.cos(centralAngle)+p.getY());
            centralAngle+=inc;
        }
        GC.setFill(super.getColor());
        GC.fillPolygon(xVertices, yVertices, n);
    }
    public MyRectangle getBoundingRectangle(){
        MyPoint q = new MyPoint(p.getX() - r, p.getY() - r);
        return new MyRectangle(q, r*2,  r*2);
    }
    public MyPoint[][] getMyShapeArea(){ //Currently only an estimate, calls getMyShape area on inner circle
        MyCircle c = new MyCircle(p, getApothem());
        return c.getMyShapeArea();
    }
}