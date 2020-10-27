package sample;

import javafx.scene.canvas.GraphicsContext;

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
