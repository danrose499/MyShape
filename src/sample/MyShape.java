package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

abstract class MyShape implements MyShapeInterface{
    MyPoint p; //Reference point (x, y)
    MyColor color = MyColor.WHITE;    //Default color of shape
    //Constructors:
    MyShape(int x, int y, MyColor color) {
        p.setPoint(x, y);
        this.color = color;
    }
    MyShape(int x, int y) { p.setPoint(x, y); }
    MyShape(MyPoint p) { this.p = p; }
    MyShape(MyPoint p, MyColor color) {
        this.p = p;
        this.color = color;
    }
    MyShape() { p = new MyPoint(); } // Default position = (0,0)
    //Getters:
    public int getX() { return p.getX(); }
    public int getY() { return p.getY(); }
    public MyPoint getPoint() { return p; }
    public Color getColor() { return color.getColor(); }
    //Setters:
    public void setPoint(MyPoint p) { this.p = p; }
    public void setX(int x) { p.setX(x); }
    public void setY(int y) { p.setY(y); }
    public void setColor(MyColor color) { this.color = color; }
    //Abstract Methods;
    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract void draw(GraphicsContext GC);
    //Other Methods:
    public String toString() { return "This is a MyShape object"; }
}