package sample;

import java.lang.Math;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

public class Main extends Application  {
    @Override
    public void start(Stage primaryStage) /*throws Exception*/ {
        try {
            primaryStage.setTitle("My PieChart");
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