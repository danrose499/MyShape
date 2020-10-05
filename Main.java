package sample;

import java.lang.Math;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

enum MyColor{
    //Constant Colors
    BLACK(0, 0, 0, 255),
    BLUE(0, 0, 255, 255),
    CYAN(0, 255,255, 255),
    DARKRED(139, 0, 0, 255),
    GREY(128, 128, 128, 255),
    GREEN(0, 128, 0, 255),
    LIME(0, 255, 0, 255),
    MAGENTA(255, 0, 255, 255),
    MAROON(128, 0, 0, 255),
    NAVYBLUE(0, 0, 128, 255),
    OLIVE(128, 128, 0, 255),
    PURPLE(128, 0, 128, 255),
    RED(255, 0 ,0, 255),
    SKYBLUE(135, 206, 250, 255),
    TEAL(0, 128, 128, 255),
    VIOLET(148, 0, 211, 255),
    WHITE(255, 255, 255, 255),
    YELLOW(255, 255, 0, 255);

    private int r, g, b, a; // red, green, blue, opacity components (0-255)
    private int argb;
    //Constructor
    MyColor(int r, int g, int b, int a) {
        setR(r);
        setG(g);
        setB(b);
        setA(a);
        setARGB(r, g, b, a);
    }
    //Setters
    private void setR(int r) { if (r >= 0 && r <= 255 )this.r = r; }
    private void setG(int g) { if (g >= 0 && g <= 255 )this.g = g; }
    private void setB(int b) { if (b >= 0 && b <= 255 )this.b = b; }
    private void setA(int a) { if (a >= 0 && a <= 255 )this.a = a; }
    private void setARGB(int r, int g, int b, int a) {
        this.argb = (a << 24) & 0xFF000000 |
                (r << 16) & 0x00FF0000 |
                (g << 8) & 0x0000FF00 |
                b;
    }
    //Getters
    public int getR(){ return r;}
    public int getG(){ return g;}
    public int getB(){ return b;}
    public int getA(){ return a;}
    public int getARGB(){ return argb;}
    public Color getColor(){
        return Color.rgb(r, g, b, (double) a / 255.0);
    }
}

class MyShape {
    private int x, y; //Reference point (x, y)
    MyColor color;    //Color of shape
    //Constructors:
    MyShape(int x, int y, MyColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    MyShape(int x, int y){
        this.x = x;
        this.y = y;
        this.color = MyColor.WHITE; // Default Color
    }
    MyShape(){
        this.x = 0;                 // Default position = (0,0)
        this.y = 0;
        this.color = MyColor.WHITE; // Default Color
    }
    //Getters:
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color.getColor(); }
    //Setters:
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setColor(MyColor color) { this.color = color; }
    //Methods:
    public String toString() { return "This is a MyShape object"; }
    public void draw(GraphicsContext GC) { //Paints the drawing canvas in the color specified
        GC.setFill(getColor());
        GC.fillRect(0, 0, this.x, this.y);
    }
}

class MyLine extends MyShape {
    private int x2, y2; //Endpoint 2 (x2, y2) [Endpoint one is stored in super constructor]
    public MyLine(int x1, int x2, int y1, int y2, MyColor color) {
        super(x1, y1, color);
        this.x2 = x2;
        this.y2 = y2;
    }
    public MyLine(int x1, int x2, int y1, int y2) {
        super(x1, y1);
        this.x2 = x2;
        this.y2 = y2;
    }
    public int getLength(){
        return (int) (Math.sqrt(Math.pow(super.getX()-x2, 2) + Math.pow(super.getY()-y2,2)));
    }
    public double get_xAngle(){
        return Math.toDegrees(Math.atan((double) (y2-super.getY()) / (double)(x2-super.getX())));
    }
    @Override
    public String toString(){ return "Line: [(" + super.getX() +", "
            + super.getY() + "), (" + x2 + ", " + y2 + ")], Length: "
            + getLength() + " and angle: " + get_xAngle();
    }
    @Override
    public void draw(GraphicsContext GC){
        GC.setStroke(super.getColor());
        GC.setLineWidth(2);
        GC.strokeLine(super.getX(), super.getY(),this.x2, this.y2);
    }
}

class MyPolygon extends MyShape {
    private int n; // number of the polygon’s equal side lengths and equal interior angles
    private int r; //  radius (r)
    //Constructors:
    MyPolygon(int x, int y, int n, int r, MyColor color) {
        super(x, y, color);
        this.n = n;
        this.r = r;
    }
    MyPolygon(int x, int y, int n, int r) {
        super(x, y);
        this.n = n;
        this.r = r;
    }
    //Methods:
    public double getSide() { return 2*r*Math.sin(Math.PI/n); }
    public double getPerimeter() { return getSide()*n; }
    public double getArea() {
        return ((n*Math.pow(getSide(),2))/(4*Math.tan(Math.PI/n)));
    }
    public double getAngle() { return 180.0*(n-2)/n; } //180(n-2) = sum of interior angles
    @Override
    public String toString(){
        return "Polygon with " + n + " sides of length " + getSide() + ", interior angles of " + getAngle() +
                " degrees, an area of " + getArea() + " and a perimeter of " + getPerimeter();
    }
    @Override
    public void draw(GraphicsContext GC) {
        double[] xVerts = new double[n];
        double[] yVerts = new double[n];
        double centralAngle = 0;
        double inc = (2*Math.PI)/n;
        for(int i = 0; i < n; i++){
            xVerts[i] = (int) r*Math.sin(centralAngle)+super.getX();
            yVerts[i] = (int) -r*Math.cos(centralAngle)+super.getY();
            centralAngle+=inc;
        }
        GC.setFill(super.getColor());
        GC.strokePolygon(xVerts, yVerts, n);
        GC.fillPolygon(xVerts, yVerts, n);
    }
}

class MyCircle extends MyShape {
    private int r;
    //Constructors:
    MyCircle(int x, int y, int r, MyColor color) {
        super(x, y, color);
        this.r = r;
    }
    MyCircle(int x, int y, int r) {
        super(x, y);
        this.r = r;
    }
    //Methods:
    public double getArea() { return Math.PI * Math.pow(r, 2); } // A = (PI)r^2
    public double getPerimeter(){ return Math.PI*(r*2); } //Circumference = 2(PI)r
    @Override
    public String toString(){
        return "Circle with Radius: " + r + ", Area: " + getArea() + ", and Perimeter: " + getPerimeter()
                + ", at X = " + super.getX() + " and Y = " + super.getY();
    }
    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(super.getColor());
        GC.strokeOval(super.getX(), super.getY(), r, r);
        GC.fillOval(super.getX(), super.getY(), r, r);
    }
}

//class MyRectangle extends MyShape{
//    int xR, yR; // x & y coordinates of top left corner
//    int width, height;
//    MyColor color;
//    MyRectangle(int x, int y, int w, int h, MyColor color) {
//        super(0,0, MyColor.BLACK);
//        this.xR = x;
//        this.yR = y;
//        this.width = w;
//        this.height = h;
//        this.color = color;
//    }
//
//    //Setters
//    public void setWidth(int w){ this.width=w; }
//    public void setHeight(int h){ this.height=h; }
//    //Getters
//    public int getWidth(){ return width; }
//    public int getHeight() { return height; }
//
//    public int area() { return width*height; }
//    public int perimeter() { return 2*(width+height); }
//    @Override
//    public void draw(GraphicsContext GC) {
//        //GC.setFill(MyColor.getColor());
//        GC.setFill(color.getColor());
//        GC.fillRect(xR, yR, width, height);
//    }
//    @Override
//    public String toString(){
//        return "Rectangle with Width: " + width + ", Height: " + height + ", Perimeter: " + perimeter() + ", Area: " + area();
//    }
//}

public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) /*throws Exception*/ {
        try {
            primaryStage.setTitle("CSc 221, Project 1");
            Pane P = new Pane();
            Canvas CV = addCanvas(800, 500);
            P.getChildren().add(CV);
            Scene SC = new Scene(P, 800, 500);
            primaryStage.setScene(SC);
            primaryStage.show();
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
    }
    private Canvas addCanvas(int cWidth, int cHeight) {
        Canvas CV = new Canvas(cWidth, cHeight);
        GraphicsContext GC = CV.getGraphicsContext2D();
        MyShape shape1 = new MyShape(800,500, MyColor.BLACK);
        shape1.draw(GC); // Paints the drawing canvas in the color specified
        MyCircle circle1 = new MyCircle (150, 0, 500, MyColor.WHITE);
        circle1.draw(GC);
        MyPolygon polygon1 = new MyPolygon(400,250, 6, 250, MyColor.BLUE);
        polygon1.draw(GC);
        MyCircle circle2 = new MyCircle(200, 50, 400, MyColor.MAGENTA);
        circle2.draw(GC);
        MyPolygon polygon2 = new MyPolygon(400,250, 6, 200, MyColor.LIME);
        polygon2.draw(GC);
        MyCircle circle3 = new MyCircle(250, 100, 300, MyColor.TEAL);
        circle3.draw(GC);
        MyPolygon polygon3 = new MyPolygon(400,250, 6, 150, MyColor.WHITE);
        polygon3.draw(GC);
        MyLine line1 = new MyLine(0, 800, 0, 500, MyColor.RED);
        line1.draw(GC);
        MyLine line2 = new MyLine(800, 0, 0, 500, MyColor.RED);
        line2.draw(GC);
        return CV;
    }
    public static void main(String[] args) {
        launch(args);
    }
}