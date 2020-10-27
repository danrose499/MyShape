package sample;

import javafx.scene.paint.Color;

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