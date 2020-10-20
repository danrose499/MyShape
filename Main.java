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
    DARK_RED(139, 0, 0, 255),
    GREY(128, 128, 128, 255),
    GREEN(0, 128, 0, 255),
    LIME(0, 255, 0, 255),
    MAGENTA(255, 0, 255, 255),
    MAROON(128, 0, 0, 255),
    NAVY_BLUE(0, 0, 128, 255),
    OLIVE(128, 128, 0, 255),
    PURPLE(128, 0, 128, 255),
    RED(255, 0 ,0, 255),
    SKY_BLUE(135, 206, 250, 255),
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

class MyPoint {
    int x, y; //(x,y) coordinates of MyPoint
    MyColor color = MyColor.WHITE; //Default Color
    //Constructors
    MyPoint(){ this(0, 0); }
    MyPoint(MyPoint P){ setPoint(P); }
    MyPoint(int x, int y){ setPoint(x, y); }
    MyPoint(int x, int y, MyColor color){
        setPoint(x, y);
        this.color = color;
    }
    //Setters
    public void setPoint(int x, int y) { this.x = x; this.y = y; }
    public void setPoint(MyPoint P) { this.x = P.getX(); this.y = P.getY(); }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    //Getters
    public int getX() { return x; }
    public int getY() { return y; }
    //Other Methods
    public void translate(int ShiftX, int ShiftY) { setPoint(x + ShiftX, y + ShiftY); }
    public double distance(MyPoint P) {
        int dx = x - P.getX();
        int dy = y - P.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }
    public double distanceFromOrigin() {
        MyPoint origin = new MyPoint();
        return distance(origin);
    }
    public void draw(GraphicsContext GC) {
        GC.setFill(color.getColor());
        GC.fillOval(x-1,y-1,3,3); //Prints an oval around point
    }
    public String toString() { return "Point at (" + x + ", " + y + ")"; }
}

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
    @Override
    public double getArea(){ return 0; }
    @Override
    public double getPerimeter(){ return getLength(); }
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

class MyPolygon extends MyShape {
    int n; // number of the polygon’s equal side lengths and equal interior angles
    int r; //  radius (r)
    MyPoint p;
    //Constructors:
    MyPolygon(MyPoint p, int n, int r, MyColor color) {
        super(new MyPoint(0,0), color);
        this.color = color;
        this.n = n;
        this.r = r;
        this.p = p;
    }
    MyPolygon(MyPoint p, int n, int r) {
        super(new MyPoint(0, 0));
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
            xVertices[i] = (int) r*Math.sin(centralAngle)+p.getX();
            yVertices[i] = (int) -r*Math.cos(centralAngle)+p.getY();
            centralAngle+=inc;
        }
        GC.setFill(super.getColor());
        GC.fillPolygon(xVertices, yVertices, n);
    }
    public MyRectangle getBoundingRectangle(){
        MyPoint q = new MyPoint(p.getX() - (int) r, p.getY() - (int) r);
        return new MyRectangle(q, (int) r*2, (int) r*2);
    }
    public MyPoint[][] getMyShapeArea(){
        MyCircle c = new MyCircle(p, (double) getApothem());
        return c.getMyShapeArea();
    }
}
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
        GC.fillOval(p.getX(), p.getY(), r, r);
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

class MyRectangle extends MyShape{
    MyPoint p;
    int width, height;
    //Constructors
    MyRectangle(MyPoint p, int w, int h, MyColor color) {
        super(new MyPoint(0, 0), color);
        this.p = p;
        this.width = w;
        this.height = h;
    }
    MyRectangle(MyPoint p, int w, int h) {
        super(new MyPoint(0, 0));
        this.p = p;
        this.width = w;
        this.height = h;
    }
    //Setters
    public void setPoint(MyPoint p){ this.p = p; }
    public void setWidth(int w){ this.width=w; }
    public void setHeight(int h){ this.height=h; }
    //Getters
    public MyPoint getPoint(){ return p; }
    public int getWidth(){ return width; }
    public int getHeight() { return height; }
    //Other Methods
    @Override
    public double getArea() { return width*height; }
    @Override
    public double getPerimeter() { return 2*(width+height); }
    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(color.getColor());
        GC.fillRect(p.getX(), p.getY(), width, height);
    }
    @Override
    public String toString(){
        return "Rectangle with Width: " + width + ", Height: " + height
                + ", Perimeter: " + getPerimeter() + ", Area: " + getArea();
    }
    public MyRectangle getBoundingRectangle(){ return new MyRectangle(p, width, height); }
    public MyPoint[][] getMyShapeArea(){
        MyPoint[][] pArea = new MyPoint[width+1][height+1];
        int i = 0;
        for(int x = p.getX(); x<=p.getX()+getWidth(); x++){
            int j = 0;
            for(int y = p.getY(); y<=p.getY()+getHeight(); y++){
                pArea[i][j] = new MyPoint(x, y);
                j++;
            }
            i++;
        }
        return pArea;
    }
}

public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) /*throws Exception*/ {
        try {
            primaryStage.setTitle("CSc 221, Project 2");
            Pane P = new Pane();
            Canvas CV = addCanvas(1000, 500);
            P.getChildren().add(CV);
            Scene SC = new Scene(P, 1000, 500);
            primaryStage.setScene(SC);
            primaryStage.show();
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
    }
    private Canvas addCanvas(int cWidth, int cHeight) {
        Canvas CV = new Canvas(cWidth, cHeight);
        GraphicsContext GC = CV.getGraphicsContext2D();

        MyRectangle r1 = new MyRectangle(new MyPoint(100, 50), 800, 400, MyColor.TEAL);
        r1.draw(GC);
        MyOval o1 = new MyOval(new MyPoint(100, 50), 800, 400, MyColor.VIOLET);
        o1.draw(GC);
        MyRectangle r2 = new MyRectangle(new MyPoint(217,109), 566, 283, MyColor.NAVY_BLUE);
        r2.draw(GC);
        MyOval o2 = new MyOval(new MyPoint(217,109), 566, 283, MyColor.GREY);
        o2.draw(GC);
        MyRectangle r3 = new MyRectangle(new MyPoint(300, 150), 400, 200, MyColor.SKY_BLUE);
        r3.draw(GC);
        MyOval o3 = new MyOval(new MyPoint(300, 150), 400, 200, MyColor.CYAN);
        o3.draw(GC);
        MyLine line1 = new MyLine(new MyPoint(0, 0), new MyPoint(1000, 500), MyColor.BLACK);
        line1.draw(GC);

        return CV;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
