package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

class MyArc extends MyShape {
    MyPoint p;
    double w, h, startAngle, arcExtent;
    //ArcType Closure;

    //Constructors
    MyArc(MyPoint p, double w, double h, double startAngle, double arcExtent, MyColor color){
        super(new MyPoint(0,0), color);
        this.p = p;
        this.w = w;
        this.h = h;
        this.startAngle = startAngle;
        this.arcExtent = arcExtent;
        this.color = color;
    }
    MyArc(MyPoint p, double w, double h, double startAngle, double arcExtent){
        super(new MyPoint(0,0));
        this.p = p;
        this.w = w;
        this.h = h;
        this.startAngle = startAngle;
        this.arcExtent = arcExtent;
        this.color = MyColor.BLACK;
    }
    //Getters
    public MyPoint getCenter() { return p; }
    //Setters
    public void setColor(MyColor color) { this.color = color; }
    public void setPoint(MyPoint p) { this.p = p; }
    public void setAxes(double w, double h) { this.w = w; this.h = h; }
    //Other Methods
    @Override
    public double getArea() {
        return 0;
    }
    @Override
    public double getPerimeter() {
        return 0;
    }
    @Override
    public MyRectangle getBoundingRectangle() {
        return null;
    }
    @Override
    public MyPoint[][] getMyShapeArea() {
        return new MyPoint[0][]; //Use formula from MyOval and return when = 1 (on boundary)
    }
    @Override
    public String toString() {
        return "Arc with... ";
    }
    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(color.getColor());
        GC.fillArc(p.getX(), p.getY(), w, h, startAngle, arcExtent, ArcType.ROUND);
        //public void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent, ArcType closure)
    }
}