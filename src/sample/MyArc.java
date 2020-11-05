package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

class MyArc extends MyShape {
    MyPoint p;
    double w, h, a, b, startAngle, arcExtent;
    ArcType closure = ArcType.ROUND;

    //Constructors
    MyArc(MyPoint p, double w, double h, double startAngle, double arcExtent, MyColor color){
        super(new MyPoint(0,0), color);
        this.p = p;
        this.w = w;
        this.h = h;
        a = w/2.00;
        b = h/2.00;
        this.startAngle = startAngle;
        this.arcExtent = arcExtent;
        this.color = color;
    }
    MyArc(MyPoint p, double w, double h, double startAngle, double arcExtent){
        super(new MyPoint(0,0));
        this.p = p;
        this.w = w;
        this.h = h;
        a = w/2.00;
        b = h/2.00;
        this.startAngle = startAngle;
        this.arcExtent = arcExtent;
        this.color = MyColor.BLACK;
    }
    //Getters
    public MyPoint getPoint() { return p; }
    //Setters
    public void setColor(MyColor color) { this.color = color; }
    public void setPoint(MyPoint p) { this.p = p; }
    public void setClosure(ArcType closure) { this.closure = closure; }
    public void setAxes(double w, double h) {
        this.w = w;
        this.h = h;
        this.a = w/2.00;
        this.b = h/2.00;
    }
    //Methods
    @Override
    public double getArea() {
        return 0;
    }
    @Override
    public MyRectangle getBoundingRectangle() {
        int xMin, yMin, xMax, yMax;
        int x1 = (p.getX() + (int) (a * Math.cos(Math.toDegrees(startAngle))));
        int y1 = (p.getY() + (int) (b * Math.sin(Math.toDegrees(startAngle))));
        int x2 = (p.getX() + (int) (a * Math.cos(Math.toDegrees(startAngle + arcExtent))));
        int y2 = (p.getY() + (int) (b * Math.sin(Math.toDegrees(startAngle + arcExtent))));

        xMin = Math.min(x1, x2);
        xMax = Math.max(x1, x2);
        yMin = Math.min(y1, y2);
        yMax = Math.max(y1, y2);

        for(int i = (int) startAngle; i < (int) (startAngle + arcExtent); i++){
            if(i%90==0){
                int tempX = (p.getX() + (int) (a * Math.cos(Math.toDegrees(i))));
                int tempY = (p.getY() + (int) (b * Math.sin(Math.toDegrees(i))));
                xMin = Math.min(xMin, tempX);
                xMax = Math.max(xMax, tempX);
                yMin = Math.min(yMin, tempY);
                yMax = Math.max(yMax, tempY);
            }
        }
        return new MyRectangle(new MyPoint(xMin, yMin), (xMax - xMin), (yMax - yMin));
    }
    @Override
    public MyPoint[][] getMyShapeArea() {
        MyPoint[][] pArea = this.getBoundingRectangle().getMyShapeArea();
        int i = 0;
        for(int x = p.getX() - (int) a; x < p.getX() + (int) a; x++){
            int j = 0;
            for(int y = p.getY() - (int) b; y < p.getY() + (int) b; y++) {
                double within = (Math.pow((x-p.getX()), 2)/Math.pow(a,2))
                        + (Math.pow((y-p.getY()),2)/Math.pow(b, 2));
                if (within != 1) {
                    pArea[i][j] = null;
                }
                j++;
            }
            i++;
        }
        return pArea;
    }
    @Override
    public String toString() {
        return "Arc of an ellipse centered at (" + p.getX() + ", " + p.getY() + "), a width of " + w +
                " and a height of " + h + ". The arc goes from " + startAngle + " to " + arcExtent + "degrees.";
    }
    @Override
    public void draw(GraphicsContext GC) {
        GC.setFill(color.getColor());
        GC.fillArc(p.getX(), p.getY(), w, h, startAngle, arcExtent, closure);
    }
}
