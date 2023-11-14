import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;


public class TestBallsSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(500,500, Color.BLACK);

        gui.setSimulable(new BallsSimulator(gui));
    }
}

class Balls {

    public List<Point> balls;
    private List<Point> initPosBalls;

    public Balls(List<Point> inArr) {
        balls = new ArrayList<Point>(inArr);
        initPosBalls = new ArrayList<Point>(inArr);
    }

    public Balls() {
        balls = new ArrayList<Point>();
        initPosBalls = new ArrayList<Point>();
    }

    public void addBall(Point pt) {
        initPosBalls.add(pt);
        balls.add(pt);
    }

    public void addBall(int x, int y) {
        initPosBalls.add(new Point(x,y));
        balls.add(new Point(x,y));
    }

    public void translate(int dx, int dy) {
        for (Point ball : balls) {
            ball.translate(dx,dy);
        }
    }

    public void reInit() {
        balls.clear();
        for (Point p : initPosBalls) {
            balls.add(new Point(p));
        }
    }

    public void clear() {
        balls.clear();
    }

    public int getNbBalls() {
        return balls.size();
    }

    @Override
    public String toString() {
        StringBuilder strBd = new StringBuilder();

        strBd.append("Balls positions: \n");
        for(Point ball : balls)
            strBd.append(String.format("    (%d,%d)\n", ball.x, ball.y));

        return strBd.toString();
    }
}

class BallsSimulator implements Simulable {
    private GUISimulator gui;
    private Balls balls;

    public BallsSimulator(GUISimulator gui) {
        this.balls = new Balls();
        this.gui = gui;
    }

    @Override
    public void next() {
        balls.translate(12,4);

        if(balls.getNbBalls() < 10)
            balls.addBall(10,10);

        draw();
    }

    @Override
    public void restart() {
        balls.clear();
    }

    private void draw() {
        gui.reset();    // clear the window

        for(Point ball : balls.balls)
            gui.addGraphicalElement(new Rectangle(ball.x, ball.y, Color.WHITE, Color.WHITE, 10));
    }
}
