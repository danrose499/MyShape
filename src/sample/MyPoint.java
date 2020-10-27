package sample;

import javafx.scene.canvas.GraphicsContext;

class MyPoint {
    int x, y; //(x,y) coordinates of MyPoint
    MyColor color = MyColor.WHITE; //Default Color
    //Constructors
    MyPoint(){ this(0, 0); }
    MyPoint(MyPoint P){
        setPoint(P);
        this.color = P.getColor();
    }
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
    public MyColor getColor() { return color; }
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